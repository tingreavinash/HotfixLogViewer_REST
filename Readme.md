<!-- PROJECT LOGO -->

<p align="center">

<h3 align="center">Backend Service for - Hotfix Log Viewer</h3>

  <p align="center">
    Data provider service for frontend application (Hotfix Log Viewer). [Internal Tool]
    <br />

  </p>
</p>





<!-- ABOUT THE PROJECT -->

## :bulb: About The Project

#### Backend Application:
* This application parses the MS Excel file daily and stores the JSON data in MongoDB.
* The frontend application makes a request to this application to get the requested data from Database.

#### Related Project:
* [Link to the project](https://github.com/tingreavinash/HotfixLogViewer_UI)


## :warning: Techstack/Framework Used

* Java
* Spring Boot
* Spring Data MongoDB
* Apache POI

<!-- GETTING STARTED -->
## :syringe: Getting Started

Follow below steps, If you want to setup this application locally on your machine.

### Prerequisites

* [Maven](https://maven.apache.org/download.cgi)
* JDK 8+
* [MongoDB Database Server](https://www.mongodb.com/try/download/community)
* MS Excel file that you want to process

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/tingreavinash/HotfixService.git
   ```
2. Go to the project folder and Build the project
   ```java
   mvn clean install
   ```
3. Go to the target folder and Start the service
   ```java
   java -jar HotfixService-0.0.1-SNAPSHOT.jar
   ```

## :page_with_curl: Application Configuration
The application configuration can be found here - [Click Here](https://github.com/tingreavinash/HotfixService/blob/master/src/main/resources/application.yml)

<!-- USAGE EXAMPLES -->
## :bomb: Usage

The REST endpoints will be exposed on port ``7777``. These endpoints are configured in frontend application. When you make any search request from frontend, the request will reach this application.

<!-- LICENSE -->
## :blue_book: License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## :heart: Contributor

Avinash Tingre - [Connect with me on LinkedIn](https://www.linkedin.com/in/abtingre/)
