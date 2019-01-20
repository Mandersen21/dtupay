FROM fabric8/java-alpine-openjdk8-jre
COPY . /target/bankservice-thorntail.jar /usr/src/
WORKDIR /usr/src/
CMD java -Djava.net.preferIPv4Stack=true\
        -Djava.net.preferIPv4Addresses=true\
        -jar bankservice-thorntail.jar
