This Cobra project demonstrate two GraphQL Schema services and a Gateway written in Kotlin built with Gradle using Netflix libraries which supports Apollo Federation connected to a DataStax Graph database server (localhost)

The two GraphQL Schema services are vehicle-dgs and labor-dgs that gets data from the DataStax graph database server, which run on port 8080 and 8081 respectively.

You can pull this Cobra project and run the two Kotlin services on Intellij IDE.
Run the Service project using Java 11
Gradle - clean, build, run as a Spring Boot application with the main at VehicleApplication and LaborApplication classes


To run the Federation Gateway where all services need to be running,
   1) Changes to the directory,
	cmdr> cd apollo-gateway

   2) Run the Federation Gateway, type the command
	cmdr> nodemon index.js


