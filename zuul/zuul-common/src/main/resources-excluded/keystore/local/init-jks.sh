#!/bin/sh

#
# This script is used to initialize the keystore for the environment and put under /src/test/resources/keystore/env folder
#

KEYSTORE=zuul.jks 
STOREPASS=p@55w0rd
STORE="-keystore $KEYSTORE -storepass $STOREPASS"
BASE="../../../../.."

. $BASE/src/main/resources-excluded/keystore/include.sh

if [ -f $KEYSTORE ]; then
	rm -rf $KEYSTORE
	echo "===== remove existing $KEYSTORE ====="
fi

# generate a empty keystore 
keytool -genkey -alias default -keyalg RSA -keysize 2048 $STORE < $BASE/src/main/resources-excluded/keystore/genkey.stdin
keytool -delete -alias default $STORE
echo "===== empty keystore $KEYSTORE created ====="

importkeystore $BASE/src/main/resources-excluded/cert/root-ca/root-ca.p12 localhost
importkeystore $BASE/src/main/resources-excluded/cert/localhost/localhost.p12 localhost 

keytool -list $STORE
