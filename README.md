# OC_P7_Poseidon

## 🛠️ Technologies

- **Langage :** Java 17
- **Framework :** Spring Boot
- **Build Tool :** Maven
- **Base de données :** MySQL
- **Frontend :** Thymeleaf
- **Tests unitaires :** JUnit 5 et Mockito
- **Couverture de code :** JaCoCo

## ⚙️ Installation et lancement

1. **Cloner le dépôt :**

    ```bash
    git clone git@github.com:MarionLz/OC_P7_Poseidon.git
    cd OC_P7_Poseidon

2. **Créer un fichier `.env` à la racine du projet** avec les variables suivantes :  
   👉 Remplacez les valeurs par celles de **votre propre base de données locale** MySQL.

    ```env
    DB_URL=jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    DB_USER=**votre_nom_utilisateur_mysql**
    DB_PASSWORD=**votre_mot_de_passe_mysql**
   ```

    ```markdown
    > 🔐 Ne partagez jamais ce fichier `.env` ou vos identifiants dans un dépôt public.

3. **Lancer l’application :**

    ```bash
    mvn spring-boot:run

4. **Accéder à l’application via http://localhost:8081**