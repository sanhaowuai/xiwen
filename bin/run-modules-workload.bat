@echo off
echo.
echo [��Ϣ] ʹ��Jar��������Modules-Workload���̡�
echo.

cd %~dp0
cd ../xiwen-modules/xiwen-workload/target

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -Dfile.encoding=utf-8 %JAVA_OPTS% -jar xiwen-modules-workload.jar

cd bin
pause