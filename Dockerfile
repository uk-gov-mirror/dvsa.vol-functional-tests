FROM nexus.shd.ci.nonprod.dvsa.aws:5000/maven-jdk-8-alpine-headless-browser:latest

WORKDIR /root
COPY ./.m2 /root/.m2
COPY src /root/src
COPY pom.xml /root/pom.xml
#COPY target ./

# Start Xvfb virtual display server and run mvn command on container startup.
COPY start.sh /root/start.sh
CMD ./start.sh
