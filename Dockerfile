FROM tomcat:9-jdk8
COPY dist/SE190722_WS2.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]