#!bin/bash
mvn -T 1C clean install compile package -f ./discovery-server/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./cia-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./file-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./github-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./java-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./parser-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./project-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./spring-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./user-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./utility-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./version-compare-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./xml-service/pom.xml -DskipTests
