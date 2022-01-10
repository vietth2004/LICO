#!bin/bash
mvn verify -f ./discovery-server/pom.xml -DskipTests
mvn verify -f ./cia-service/pom.xml -DskipTests
mvn verify -f ./file-service/pom.xml -DskipTests
mvn verify -f ./github-service/pom.xml -DskipTests
mvn verify -f ./java-service/pom.xml -DskipTests
mvn verify -f ./parser-service/pom.xml -DskipTests
mvn verify -f ./project-service/pom.xml -DskipTests
mvn verify -f ./spring-service/pom.xml -DskipTests
mvn verify -f ./user-service/pom.xml -DskipTests
mvn verify -f ./utility-service/pom.xml -DskipTests
mvn verify -f ./version-compare-service/pom.xml -DskipTests
mvn verify -f ./api-gateway/pom.xml -DskipTests

