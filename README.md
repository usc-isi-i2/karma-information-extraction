Karma-Information-Extraction
============================

Information extraction service for Karma that runs as a Jersey application in an embedded Grizzly container.
The service is used for named-entity extractions (People names, Dates, Places/Locations etc). 

## Installation and Setup ##
System Requirements: **Java 1.7, Maven 3.0** and above.

To run the service, execute the following command from `karma-information-extraction` top directory:
`mvn clean compile exec:java`. The server starts on `port 8090` by default at address
`http: //localhost:8090/myapp/myresource`.