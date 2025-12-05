# Architecture de Persistance - Review Service

## 📐 Architecture en couches

```
┌─────────────────────────────────────────┐
│         ReviewController.java           │  ← Couche API REST
│  (Endpoints HTTP, validation, réponses) │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│          ReviewService.java             │  ← Couche Service (Logique métier)
│  (Transactions, logique métier)         │
└──────────────┬──────────────────────────┘
               │
               ├──────────────┐
               ▼              ▼
┌────────────────────┐  ┌──────────────────┐
│ ReviewRepository   │  │  ReviewMapper    │  ← Couche Persistance
│ (Spring Data JPA)  │  │  (Domain ↔ JPA)  │
└─────────┬──────────┘  └──────────────────┘
          │
          ▼
┌─────────────────────────────────────────┐
│       ReviewJpaEntity.java              │  ← Entité JPA
│    (Mapping table reviews)              │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│      MySQL Database (reviewdb)          │  ← Base de données
│         Table: reviews                  │
└─────────────────────────────────────────┘
```

## 🔄 Flux de données

### Lecture (GET)
```
1. Client → ReviewController.getReviews(productId)
2. ReviewController → ReviewService.getReviewsByProductId(productId)
3. ReviewService → ReviewRepository.findByProductId(productId)
4. ReviewRepository → MySQL (SELECT * FROM reviews WHERE product_id = ?)
5. MySQL → ReviewRepository (List<ReviewJpaEntity>)
6. ReviewService → ReviewMapper.mapToDomainEntity()
7. ReviewService → ReviewController (List<Review>)
8. ReviewController → Client (JSON)
```

### Écriture (POST)
```
1. Client → ReviewController.createReview(Review)
2. ReviewController → ReviewService.createReview(Review)
3. ReviewService → ReviewMapper.mapToJpaEntity(Review)
4. ReviewService → ReviewRepository.save(ReviewJpaEntity)
5. ReviewRepository → MySQL (INSERT INTO reviews ...)
6. MySQL → ReviewRepository (ReviewJpaEntity avec ID)
7. ReviewService → ReviewMapper.mapToDomainEntity()
8. ReviewService → ReviewController (Review)
9. ReviewController → Client (JSON)
```

## 📦 Composants

### 1. **Review** (Model Domain)
- **Rôle**: Modèle de données pour l'API
- **Emplacement**: `model/Review.java`
- **Caractéristiques**: 
  - POJO simple
  - Utilisé dans les échanges API
  - Indépendant de la couche persistance

### 2. **ReviewJpaEntity** (Entité JPA)
- **Rôle**: Représentation de la table MySQL
- **Emplacement**: `persistence/entity/ReviewJpaEntity.java`
- **Annotations**:
  - `@Entity` - Marque comme entité JPA
  - `@Table(name = "reviews")` - Nom de la table
  - `@Id` - Clé primaire
  - `@GeneratedValue` - Auto-incrémentation
  - `@Data` (Lombok) - Génère getters/setters

### 3. **ReviewRepository** (Repository)
- **Rôle**: Interface d'accès aux données
- **Emplacement**: `persistence/ReviewRepository.java`
- **Type**: Interface Spring Data JPA
- **Méthodes**:
  - Hérite de `JpaRepository` (CRUD complet)
  - `findByProductId()` - Méthode custom

### 4. **ReviewMapper** (Mapper)
- **Rôle**: Conversion entre modèles
- **Emplacement**: `persistence/mapper/ReviewMapper.java`
- **Méthodes**:
  - `mapToDomainEntity()` - JPA → Domain
  - `mapToJpaEntity()` - Domain → JPA

### 5. **ReviewService** (Service)
- **Rôle**: Logique métier et transactions
- **Emplacement**: `service/ReviewService.java`
- **Annotations**: `@Service`, `@Transactional`
- **Responsabilités**:
  - Orchestrer les opérations
  - Gérer les transactions
  - Conversion via mapper
  - Gestion des erreurs

### 6. **ReviewController** (Contrôleur)
- **Rôle**: API REST
- **Emplacement**: `controller/ReviewController.java`
- **Annotations**: `@RestController`, `@RequestMapping`
- **Endpoints**: 6 endpoints CRUD

### 7. **DataInitializer** (Configuration)
- **Rôle**: Initialisation des données de test
- **Emplacement**: `config/DataInitializer.java`
- **Type**: `CommandLineRunner`
- **Exécution**: Au démarrage de l'application

## 🔧 Configuration

### application.yaml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/reviewdb
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update  # Crée/met à jour les tables automatiquement
    show-sql: true      # Affiche les requêtes SQL dans les logs
```

### build.gradle
```groovy
dependencies:
  - spring-boot-starter-data-jpa    # JPA/Hibernate
  - spring-boot-starter-webflux     # API REST réactive
  - mysql-connector-j               # Driver MySQL
  - lombok                          # Réduction boilerplate
```

## 🗄️ Schéma de base de données

### Table: reviews
```sql
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| review_id  | int          | NO   | PRI | NULL    | auto_increment |
| product_id | int          | YES  | MUL | NULL    |                |
| author     | varchar(255) | YES  |     | NULL    |                |
| subject    | varchar(255) | YES  |     | NULL    |                |
| content    | text         | YES  |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
```

### Index
- **PRIMARY KEY**: review_id
- **INDEX**: idx_product_id (pour optimiser les recherches par produit)

## 🎯 Bonnes pratiques appliquées

1. **Séparation des responsabilités**
   - Chaque couche a un rôle distinct
   - Pas de logique métier dans le contrôleur
   - Pas de code SQL dans le service

2. **Utilisation de patterns**
   - **Repository Pattern** (Spring Data JPA)
   - **Mapper Pattern** (conversion entre modèles)
   - **Service Pattern** (logique métier)
   - **DTO Pattern** (Review comme DTO)

3. **Gestion des transactions**
   - `@Transactional` sur les méthodes du service
   - `readOnly = true` pour les lectures (optimisation)

4. **Configuration par profils**
   - Profil `native` pour développement local
   - Profil `docker` pour déploiement conteneurisé

5. **Injection de dépendances**
   - Injection par constructeur (recommandé par Spring)
   - Pas de `@Autowired` sur les champs

## 🚀 Évolutions possibles

1. **Validation**
   ```java
   @NotNull @Min(1) private Integer productId;
   @NotBlank private String author;
   ```

2. **Pagination**
   ```java
   Page<ReviewJpaEntity> findByProductId(Integer productId, Pageable pageable);
   ```

3. **Recherche avancée**
   ```java
   List<ReviewJpaEntity> findByAuthorContainingIgnoreCase(String author);
   ```

4. **Audit**
   ```java
   @CreatedDate private LocalDateTime createdAt;
   @LastModifiedDate private LocalDateTime updatedAt;
   ```

5. **Soft Delete**
   ```java
   @SQLDelete(sql = "UPDATE reviews SET deleted = true WHERE review_id = ?")
   ```

6. **Caching**
   ```java
   @Cacheable("reviews")
   public List<Review> getReviewsByProductId(Integer productId)
   ```

## 📚 Technologies utilisées

- **Spring Boot 4.0.0** - Framework principal
- **Spring Data JPA** - Abstraction de persistance
- **Hibernate** - ORM (implémentation JPA)
- **MySQL 8.0** - Base de données relationnelle
- **Lombok** - Réduction du code boilerplate
- **Docker** - Conteneurisation
- **Gradle** - Build et gestion des dépendances

