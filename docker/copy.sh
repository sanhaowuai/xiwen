#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20210908.sql ./mysql/db
cp ../sql/ry_config_20220114.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../xiwen-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy xiwen-gateway "
cp ../xiwen-gateway/target/xiwen-gateway.jar ./xiwen/gateway/jar

echo "begin copy xiwen-auth "
cp ../xiwen-auth/target/xiwen-auth.jar ./xiwen/auth/jar

echo "begin copy xiwen-visual "
cp ../xiwen-visual/xiwen-monitor/target/xiwen-visual-monitor.jar  ./xiwen/visual/monitor/jar

echo "begin copy xiwen-modules-system "
cp ../xiwen-modules/xiwen-system/target/xiwen-modules-system.jar ./xiwen/modules/system/jar

echo "begin copy xiwen-modules-file "
cp ../xiwen-modules/xiwen-file/target/xiwen-modules-file.jar ./xiwen/modules/file/jar

echo "begin copy xiwen-modules-job "
cp ../xiwen-modules/xiwen-job/target/xiwen-modules-job.jar ./xiwen/modules/job/jar

echo "begin copy xiwen-modules-gen "
cp ../xiwen-modules/xiwen-gen/target/xiwen-modules-gen.jar ./xiwen/modules/gen/jar

