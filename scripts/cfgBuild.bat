call mvn -T 1C clean install compile package -f .\core-engine\cfg\pom.xml
call mvn -T 1C clean install compile package -f .\processing-service\unitTesting\pom.xml -DskipTests