# MediLabo Solutions
Repository pour le projet 9 de la formation développeur d'application Java

### Fonctionnalités de l'application :
- Ajout / modification de patients
- Consultation / ajout de notes dans l'historique du patient
- Evaluation du risque de diabète du patient

### Architecture du projet
- Le projet est construit en plusieurs microservices :
  - Discovery server Eureka
  - Service Gateway
  - Service d'authentification
  - Service patient pour gérer les données des patients
  - Service notes pour gérer l'historique des patients
  - Service risque pour évaluer le risque de diabète
  - Service UI pour l'interface utilisateur
- Diagramme d'architecture
![Diagramme-architecture-2s](https://github.com/git-mg-dev/galvan-marc-projet-9-java/assets/144458198/d2224c44-20fe-4987-86ab-1c2a9a5a6632)

### Utilisation de l'application sur Docker
- Contruire les images de tous les microservices à partir des dockerfiles
- Lancer le docker compose à la racine du projet
- Vérifier que tous les microservices sont lancés sur http://localhost:9102/ (Eureka)
- Aller sur l'application http://localhost:8080/
- Pour s'authentifier, les identifiants sont prérenseignés

### Utilisation de l'application hors Docker
- Créer les bases de données à partir des fichiers du répertoire scripts
- Lancer le discovery server Eureka en 1er puis lancer les autres microservices
- Vérifier que tous les microservices sont lancés sur http://localhost:9101/ (Eureka)
- Aller sur l'application http://localhost:8080/
- Pour s'authentifier, les identifiants sont prérenseignés

## Recommandations Green Code
Faire du Green coding c'est mettre en œuvre une pratique de développement qui permet de produire un code plus vertueux afin de minimiser l’utilisation de ressources par l’application. Par ressources, on entend :
- La mémoire -> éviter les fuites mémoire
- L’utilisation CPU -> optimisation des algorithmes
- L’utilisation du réseaux -> réduire le nombre de requêtes, minimiser la taille des données véhiculées, etc.

Quelques pistes d'amélioration de l'application :
- Paginer la liste des patients pour limiter la taille des données retournées
- Refactorer la boucle forEach qui affiche la liste des patients pour diminuer le nombre de requêtes à la BDD
- Rendre le champ riskLevel statique pour ne pas le calculer à la volée à chaque fois qu'on affiche les données d'un patient
