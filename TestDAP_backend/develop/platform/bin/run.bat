cd ..
cd ..
SET CLASSPATH=./*;./lib/*;./conf;./lang
SET PATH=%PATH%;D:\java\jdk1\openjdk-8u345\bin
java -cp %CLASSPATH% com.digiwin.simplified.app.test.DWSimplifiedModeMyApp

REM java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=127.0.0.1:18085 TestDAP_backend-5.0.1.2000.jar
REM java -jar TestDAP_backend-5.0.1.2000.jar