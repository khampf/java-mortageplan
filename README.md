# Mortage Plans
_by Kåre Hampf_

## Job interview code task

### Requirements and dependencies
 * Java 11


### How to build
 * `gradlew clean build`

### How to run

#### As a console application with assigned input
 * `gradlew runJavaExec`

#### As a SpringBoot Web App using built in Tomcat
 * `java -jar mortageplan.war`
 * Listens on [port 8080](http://localhost:8080) and has [REST endpoints](http://localhost:8080/rest/prospects)

#### As a local Docker image
 * Deploys as _mortageplan_tomcat_ and forwards [port 8080](http://localhost:8080) to it.
 * Listens on [port 8080](http://localhost:8080) and has [REST endpoints](http://localhost:8080/rest/prospects)
