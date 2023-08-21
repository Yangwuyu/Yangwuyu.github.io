cd ..
cd ..
SET CLASSPATH=./;./platform/lib/*;./platform/conf;./platform/lang
SET PATH=%PATH%;D:\java\jdk1\openjdk-8u345\bin
java -cp %CLASSPATH% com.digiwin.app.common.launcher.DWXClassLauncher --xclass.mainclass=com.digiwin.gateway.DWApiGatewayApplication --xclass.password=@classPassword@