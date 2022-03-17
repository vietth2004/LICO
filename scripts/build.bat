call mvn -T 1C clean install compile package -f .\core-engine\core\pom.xml
call mvn -T 1C clean install compile package -f .\core-engine\jdt\pom.xml
call mvn -T 1C clean install compile package -f .\core-engine\utils\pom.xml
call mvn -T 1C clean install compile package -f .\management-service\discovery-server\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\cia-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\back-end-service\file-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\back-end-service\git-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\java-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\back-end-service\parser-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\back-end-service\project-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\spring-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\back-end-service\user-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\utility-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\version-compare-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\jsf-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\management-service\api-gateway\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\struts-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\jsp-service\pom.xml -DskipTests
call mvn -T 1C clean install compile package -f .\processing-service\xml-service\pom.xml -DskipTests
