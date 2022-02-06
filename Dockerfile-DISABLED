FROM tomcat
ARG WAR_FILE=build/libs/mortageplan-0.0.1-SNAPSHOT.war
#COPY ${WAR_FILE} /usr/local/tomcat/webapps/mortageplan.war

# RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps.old
# RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps

ADD ${WAR_FILE} /usr/local/tomcat/webapps/mortageplan.war
EXPOSE 8080
# CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]