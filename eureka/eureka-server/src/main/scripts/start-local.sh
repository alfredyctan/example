#!/bin/sh

case $1 in
	LV1|LV2|DR1|DR2)
		;;
	*)
		echo "LV1, LV2, DR1, DR2?"
		exit 1
		;;
esac

case `uname -a` in
	Linux*|Darwin*)
		S=':'
		;;
	MINGW64_NT*)
		S=';'
		;;
esac

MAIN_PREFIX=org.afc.discovery.eureka.EurekaServerLocal
SERVER=eureka-server

CLASSES=$SERVER/target/classes
TEST_CLASSES=$SERVER/target/test-classes
CONFIG=$SERVER/target/config

mkdir -p $SERVER/target/lib
if [ -z "`ls $SERVER/target/lib`" ]; then
	echo "copying dependencies..."
	cd $SERVER
	mvn dependency:copy-dependencies -DoutputDirectory=target/lib > /dev/null
	cd .. 
fi

CLASSPATH=`find $SERVER/target/lib`
CLASSPATH=`echo $CLASSPATH | sed "s/ /${S}/g"` 

java -Xmx256m -cp "${TEST_CLASSES}${S}${CLASSES}${S}${CLASSPATH}" "${MAIN_PREFIX}$1"