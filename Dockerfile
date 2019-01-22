FROM openjdk:maven-jdk-8-alpine-headless-browser:1

WORKDIR /root
COPY src ./src
COPY pom.xml ./

# Start Xvfb virtual display server and run mvn command on container startup.
COPY start.sh ./
CMD ./start.sh
