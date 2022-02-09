# Mortage Plans

_by Kåre Hampf_

## Job interview code task

### Requirements and dependencies

* Java 11

### Build and test

* `gradlew clean build`

### How to run

#### As console application with assigned input

* `gradlew runExececutableWar`
* or
    * Unix: `java -jar build/libs/mortageplan.war prospects.txt`
    * Windows: `java -jar .\build\libs\mortageplan.war .\prospects.txt`

#### As SpringBoot Web App using built in Tomcat

* `gradlew runExececutableWarTomcat`
* or (same as previous but without arguments)
  * Unix: `java -jar build/libs/mortageplan.war`
  * Windows: `java -jar .\build\libs\mortageplan.war`
* Listens on [port 8080](http://localhost:8080) and has [REST endpoints](http://localhost:8080/rest/prospects)

#### As Docker container

##### Deploy local Docker image

* `gradlew docker`
* or `docker image build . -t mortageplan`
* Deploys image _mortageplan_

##### Run local Docker container

* `gradlew dockerRun`
* or `docker run -d -p 8080:8080 --name mortageplan -i mortageplan`
* Runs container _mortageplan_
* Listens on [port 8080](http://localhost:8080) and has [REST endpoints](http://localhost:8080/rest/prospects)

#### Stop local Docker container

* `gradlew dockerStop`
* or `docker stop mortageplan`

#### Remove local Docker container and image
* `gradlew dockerRemoveContainer`