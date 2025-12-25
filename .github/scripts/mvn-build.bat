@echo off
setlocal

call mvn -B -ntp -T 1C -f .\core-engine\core\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\core-engine\jdt\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\core-engine\utils\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1

call mvn -B -ntp -T 1C -f .\processing-service\cia-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\back-end-service\file-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\back-end-service\git-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\java-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\back-end-service\parser-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\back-end-service\project-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\spring-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\back-end-service\user-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\utility-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\version-compare-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\jsf-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\struts-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\jsp-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1
call mvn -B -ntp -T 1C -f .\processing-service\xml-service\pom.xml clean install -DskipTests
if errorlevel 1 exit /b 1

endlocal
