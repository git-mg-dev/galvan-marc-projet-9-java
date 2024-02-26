# MediLabo Solutions
Repository pour le projet 9 de la formation développeur d'application Java
Fonctionnalités de l'application :
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
  - TODO