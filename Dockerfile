FROM nexus.shd.ci.nonprod.dvsa.aws:5000/maven-jdk-8-alpine-headless-browser:1

WORKDIR /root
COPY src ./src
COPY pom.xml ./
COPY allure ./allure

# Start Xvfb virtual display server and run mvn command on container startup.
COPY start.sh ./
CMD ./start.sh
