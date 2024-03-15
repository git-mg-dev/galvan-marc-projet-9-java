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
![diagramme-architecture](https://github.com/git-mg-dev/galvan-marc-projet-9-java/assets/144458198/6e62e9e5-b90f-4a57-98ca-93c102952c16)

### Utilisation de l'application sur Docker
- Lancer le docker compose à la racine du projet (il crée les images de tous les microservices)
- Vérifier que tous les microservices sont lancés sur http://localhost:9102/ (Eureka)
- Aller sur l'application http://localhost:8080/
- Pour s'authentifier, les identifiants sont prérenseignés

### Utilisation de l'application hors Docker
- Créer les bases de données à partir des fichiers du répertoire scripts
- Lancer le discovery server Eureka en 1er puis lancer les autres microservices
- Vérifier que tous les microservices sont lancés sur http://localhost:9101/ (Eureka)
- Aller sur l'application http://localhost:8080/
- Pour s'authentifier, les identifiants sont prérenseignés
