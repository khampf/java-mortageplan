# This should work but does not. Always 404 on tomcat for some reason
FROM tomcat
ARG WAR_FILE=build/libs/mortageplan.war
#COPY ${WAR_FILE} /usr/local/tomcat/webapps/mortageplan.war

# To expose tomcat default webapps
# RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/webapps.old
# RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps

COPY ${WAR_FILE} /usr/local/tomcat/webapps/
EXPOSE 8080
# CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]