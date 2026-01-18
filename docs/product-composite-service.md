# Product Composite Service — Documentation technique

## Nom et description
- Nom: Product Composite Service
- Description: Agrège les données des micro-services Product, Review et Recommendation pour exposer une vue consolidée d'un produit via une API réactive.

## Installation et exécution
- Dépendances: Spring Boot, Spring WebFlux, découverte de services (EnableDiscoveryClient)
- Démarrage: via Docker Compose ou Gradle (voir `product-composite-service/Dockerfile` et `docker-compose.yaml` du projet racine)

## Architecture (Hexagonale)
- API (Controller): `ProductCompositeController`
- Domaine/DTO: `ProductComposite`, `Product`, `Review`, `Recommendation`
- Adapter sortant (HTTP clients): `ProductCompositeIntegration` (WebClient)
- Infrastructure: Configuration Spring Boot, découverte de services

```mermaid
flowchart LR
    FE[Frontend] -->|GET /POST /DELETE| PC[Product Composite Service]
    PC -->|GET /product/{id}\nPOST /product\nDELETE /product/{id}| P[Product Service]
    PC -->|GET/POST/DELETE /recommendation| R[Recommendation Service]
    PC -->|GET/POST/DELETE /review| RV[Review Service]
```

## Modèle de données (DTO)
- `ProductComposite` record: `{ product: Product, reviews: List<Review>, recommendations: List<Recommendation> }`
- `Product`: `{ productId: int, name: String, weight: int }`
- `Review`: `{ productId: int, reviewId: int, author: String, subject: String, content: String }`
- `Recommendation`: `{ productId: int, recommendationId: int, author: String, rate: int, content: String }`

## API Reference
Base path: `/product-composite`

- GET `/{productId}`
  - Réponse: `ProductComposite`
  - Agrège: `GET product-service/product/{productId}`, `GET recommendation-service/recommendation?productId={productId}`, `GET review-service/review/{productId}`

- POST `/`
  - Corps: `ProductComposite`
  - Effets:
    - `POST product-service/product` avec `product`
    - `POST recommendation-service/recommendation` pour chaque `recommendation`
    - `POST review-service/review` pour chaque `review`

- DELETE `/{productId}`
  - Effets:
    - `DELETE product-service/product/{productId}`
    - `DELETE recommendation-service/recommendation?productId={productId}`
    - `DELETE review-service/review/{productId}`

## Intégration et flux
`ProductCompositeIntegration`:
- Constantes d'URL:
  - Product: `http://product-service/product`
  - Review: `http://review-service/review`
  - Recommendation: `http://recommendation-service/recommendation`
- `getProductComposite(int productId)`:
  - Mono.zip de 3 appels HTTP, mapping vers `new ProductComposite(t1, t3, t2)`
- `createCompositeProduct(ProductComposite body)`:
  - Crée la ressource produit puis itère sur recommandations et reviews
- `deleteCompositeProduct(int productId)`:
  - Supprime produit, recommandations et reviews (par productId)

## Sécurité
- Ce service ne fait pas d'authentification directe. Dans une architecture complète, il est typiquement protégé par une gateway et/ou des filtres qui propagent le JWT vers les services ciblés. Les IDs utilisateurs (si utilisés dans Review/Recommendation) seraient transmis dans les payloads ou headers.

## Tests
- `ProductCompositeServiceApplicationTests` (SpringBootTest): test de contexte chargé. Tests unitaires spécifiques aux intégrations HTTP ne sont pas présents dans le projet au moment de la rédaction.

## Points d'attention
- Résilience: pas de circuit breaker/timeout visibles. À considérer pour la prod.
- Consistance: les opérations POST/DELETE ne sont pas transactionnelles distribuées; prévoir des compensations si une étape échoue.
