# MediLabo Solutions
Microservice d'évaluation du risque de diabète du patient

### Règles d'évaluation
- Aucun risque (None) : Le dossier du patient ne contient aucune note du médecin contenant les déclencheurs (terminologie)
- Risque limité (Borderline) : Le dossier du patient contient entre deux et cinq déclencheurs et le patient est âgé de plus de 30 ans ;
- Danger (In Danger) : Dépend de l'âge et du sexe du patient. Si le patient est un homme de moins de 30 ans, alors trois termes déclencheurs doivent être présents. Si le patient est une femme et a moins de 30 ans, il faudra quatre termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra six ou sept ;
- Apparition précoce (Early onset) : Encore une fois, cela dépend de l'âge et du sexe. Si le patient est un homme de moins de 30 ans, alors au moins cinq termes déclencheurs sont nécessaires. Si le patient est une femme et a moins de 30 ans, il faudra au moins sept termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra huit ou plus.