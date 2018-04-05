#!/bin/sh

case $1 in
	LV1|LV2|DR1|DR2)
		;;
	*)
		echo "LV1, DR1?"
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

MAIN_PREFIX=org.afc.gateway.zuul.InternalZuulGatewayLocal


BASE=../../..
CLASSES=target/classes
TEST_CLASSES=target/test-classes
CONFIG=target/config

cd $BASE
mkdir -p target/lib
if [ -z "`ls target/lib`" ]; then
	echo "copying dependencies..."
	mvn dependency:copy-dependencies -DoutputDirectory=target/lib > /dev/null 
fi


CLASSPATH=`find target/BOOT-INF/lib`
CLASSPATH=`echo $CLASSPATH | sed "s/ /${S}/g"` 

java -Xmx256m -cp "${TEST_CLASSES}${S}${CLASSES}${S}${CLASSPATH}" "${MAIN_PREFIX}$1"