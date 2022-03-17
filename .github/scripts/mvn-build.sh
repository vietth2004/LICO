#!bin/bash
mvn -T 1C clean install compile package -f ./core-engine/core/pom.xml
mvn -T 1C clean install compile package -f ./core-engine/jdt/pom.xml
mvn -T 1C clean install compile package -f ./core-engine/utils/pom.xml
mvn -T 1C clean install compile package -f ./discovery-server/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./cia-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./file-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./git-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./java-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./parser-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./project-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./spring-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./user-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./utility-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./version-compare-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./jsf-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./api-gateway/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ./strut-service/pom.xml -DskipTests
