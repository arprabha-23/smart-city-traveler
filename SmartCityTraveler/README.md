# Smart City Traveler

A simple beginner-friendly JavaFX + Maven college project. It uses in-memory data only: no Spring Boot, database, external API, or payment gateway.

## Demo login
- Username: `demo`
- Password: `demo123`

## Requirements
- JDK 17 or newer (JDK 17 is recommended)
- Eclipse IDE with Maven support
- Maven 3.8+

## Command-line run
```text
mvn clean javafx:run
```

## Eclipse
1. File > Import > Maven > Existing Maven Projects.
2. Select the `SmartCityTraveler` folder.
3. Finish and wait for Maven dependencies to download.
4. Right-click project > Maven > Update Project.
5. Right-click project > Run As > Maven build...
6. In Goals enter: `clean javafx:run`
7. Run.

The application contains Login, Sign Up, Home, Profile, Search, Search Results, Booking, Payment and Confirmation screens.
