-- Script de création de la base de données MySQL pour le service review-service
-- Ce script est optionnel car l'application crée automatiquement la base de données et les tables

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS reviewdb;

-- Utiliser la base de données
USE reviewdb;

-- Créer la table reviews
CREATE TABLE IF NOT EXISTS reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    author VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    content TEXT,
    INDEX idx_product_id (product_id)
);

-- Insérer des données de test (optionnel)
INSERT INTO reviews (product_id, author, subject, content) VALUES
(1, 'Author 1', 'Subject 1', 'Content 1'),
(1, 'Author 2', 'Subject 2', 'Content 2'),
(2, 'Author 3', 'Subject 3', 'Content 3');

-- Afficher les données
SELECT * FROM reviews;

