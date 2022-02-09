FROM openjdk:11-slim
ARG WAR_FILE=./build/libs/mortageplan.war
COPY ${WAR_FILE} /
ENTRYPOINT ["java","-jar","/mortageplan.war"]