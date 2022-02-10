# Mortage Plans

_by Kåre Hampf_

## Job interview code task

### Requirements and dependencies

* JDK 11 (tested with OpenJDK 11)

### Build and test

* `gradlew clean build`

### How to run

#### As console application with assigned input

* `gradlew runExecutableWar`
* or
    * Unix: `java -jar build/libs/mortageplan.war prospects.txt`
    * Windows: `java -jar .\build\libs\mortageplan.war .\prospects.txt`
* Note: I could only manage to get euro-sign (€) output in cmd.exe using UTF-8 encoding like so:
  * `chcp 65001`

Example output:
```
> Task :runExecutableWar
01:24:53.575 [main] INFO com.example.mortageplan.MortagePlanApplication - Loading prospects.txt in console session.
01:24:53.584 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.584 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.584 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.584 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.584 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.584 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.585 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.585 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.585 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.585 [main] WARN com.example.mortageplan.util.CSV - Malformed CSV (empty line)
01:24:53.618 [main] WARN com.example.mortageplan.MortagePlanApplication - Invalid CSV input: Incorrect number of columns (must be 4) in [.]

****************************************************************************************************

Prospect 1: Juha wants to borrow 1000.0 € for a period of 2 years and pay 43.87 € each month
Prospect 2: Karvinen wants to borrow 4356.0 € for a period of 6 years and pay 62.87 € each month
Prospect 3: Claes Månsson wants to borrow 1300.55 € for a period of 2 years and pay 59.22 € each month
Prospect 4: Clarencé Andersson wants to borrow 2000.0 € for a period of 4 years and pay 46.97 € each month

****************************************************************************************************

BUILD SUCCESSFUL in 1s
5 actionable tasks: 1 executed, 4 up-to-date
```
#### As SpringBoot Web App using built in Tomcat

* `gradlew runExecutableWarTomcat`
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

### Access deployed ECS Docker container on Amazon ECR
* [http://ec2-13-51-13-216.eu-north-1.compute.amazonaws.com:8080](http://ec2-13-51-13-216.eu-north-1.compute.amazonaws.com:8080)