mvn -T 1C clean install compile package -f ../core-engine/utils/pom.xml
mvn -T 1C clean install compile package -f ../core-engine/core/pom.xml
mvn -T 1C clean install compile package -f ../core-engine/jdt/pom.xml
mvn -T 1C clean install compile package -f ../management-service/discovery-server/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/cia-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../backend-service/file-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../backend-service/git-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/java-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../backend-service/parser-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../backend-service/project-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/spring-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../backend-service/user-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/utility-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/version-compare-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/jsf-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../management-service/api-gateway/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/struts-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/jsp-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/xml-service/pom.xml -DskipTests
mvn -T 1C clean install compile package -f ../processing-service/unitTesting/pom.xml -DskipTests