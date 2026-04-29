# Mouseion API

## Inhoudsopgave
1. Inleiding
2. Functionaliteiten
3. Projectstructuur
4. Technieken en frameworks
5. Installatie en lokale setup
6. Configuratie
7. Tests uitvoeren
8. Gebruikers en autorisatie

---

## 1. Inleiding
Mouseion is een Spring Boot web-API voor het beheren van schilderijen en gerelateerde kunsthistorische data.  
De API ondersteunt het opslaan, ophalen en beheren van schilderijen, schilders, educatieve content en historische context.

---

## 2. Functionaliteiten
De belangrijkste functionaliteiten van de API zijn:

- CRUD-operaties voor Paintings
- Beheer van Painters
- Educatieve content koppelen aan schilderijen
- Historische periodes en karakteristieken beheren
- File upload en download (afbeeldingen)
- Authenticatie en autorisatie met JWT
- Integratie- en unit tests

---

## 3. Projectstructuur
Het project is opgebouwd volgens een standaard Spring Boot architectuur:

```text
src/main/java
├── controllers
├── services
├── models
├── repositories
├── security
├── exceptions
└── dtos

src/test/java
├── integrationtests
└── services
```

## 4. Technieken en frameworks
Dit project maakt gebruik van: 

**Backend framework**
- Java 21 – programmeertaal waarin de applicatie is ontwikkeld
- Spring Boot 3.5.9 – framework voor het bouwen van de REST-ful web-API
- Spring Web (spring-boot-starter-web) – voor het bouwen van REST-controllers en HTTP endpoints
- Spring Data JPA (spring-boot-starter-data-jpa) – voor database interactie via Hibernate ORM
- Hibernate – ORM-laag voor het mappen van Java entities naar relationele database tabellen
- Spring Validation (spring-boot-starter-validation) – voor input validatie van DTO’s

**Database**
- PostgreSQL - primaire database voor productie
- H2 Database - in-memory database voor unit- en integratietests

**Security**
- Spring Security (spring-boot-starter-security) – voor authenticatie en autorisatie 
- JWT (io.jsonwebtoken / jjwt) – voor token-based authentication

**Testing**
- Spring Boot Starter Test - voor unit- en integratietests
- JUnit 5 - testframework
- MockMvc - voor het testen van REST endpoints zonder de server volledig te starten

**Development tools**
- Spring Boot DevTools - voor het reload tijdens development
- Maven - dependency management en build automation tool

---

## 5. Installatie en lokale setup 

### Vereisten
- Java 17 of hoger
- Maven
- IDE (Intellij IDEA aanbevolen)

### Stappen

1. **Clone de repository** naar je lokale machine:
   ```bash
   git clone git@github.com:DJGrootendorst/mouseion.git

2. **Run de applicatie in de lokale machine** 

3. **De API draait standaard op:**
   ```bash
   http://localhost:8080

---
   
## 6. Configuratie

De applicatie gebruik configuratiebestanden in:

src/main/resources/application.properties

Belangrijke instellingen:
- Database configuratie
- JWT secret key 
- File upload directory

Voor tests wordt een aparte configuratie gebruikt:
src/test/resources/application-test.properties

---

## 7. Tests uitvoeren

De applicatie bevat zowel unit tests als integratietests:

- Unit tests (services)
- Integration tests (controllers)


### Gebruikte tools:
- JUnit 5
- Spring Boot Test
- MockMvc

---

## 8. Gebruikers en autorisatie

De applicatie gebruikt JWT-gebaseerde authenticatie en autorisatie via Spring Security. 

### Rollen:
- STUDENT: basis toegang tot de API
- EDUCATOR: basis toegang tot de API
- ADMIN: volledige toegang tot alle endpoints

### Authenticatie
Gebruikers loggen in en ontvangen een JWT-token, waarmee toegang wordt verkregen tot beveiligde endpoints.

## Opmerking
De exacte gebruikers en rollen kunnen worden aangemaakt via de database initialisatie (`data.sql`) of via de applicatie zelf.



  




