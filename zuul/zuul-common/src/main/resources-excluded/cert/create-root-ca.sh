#!/bin/sh

clean() { 
	if [ -f $1 ]; then
		rm -rf $1
		echo "===== remove existing $1 ====="
	fi
}


BASE="."
STDIN="stdin"
KEYSTORE=keystore.jks 
PASSCODE=p@55w0rd

ROOT_CA="root-ca"
ROOT_CA_ALIAS=`head -1 $BASE/$STDIN/${ROOT_CA}.genkey.stdin`
VALIDITY=7300

mkdir -p $BASE/${ROOT_CA}

clean $BASE/$KEYSTORE
clean $BASE/${ROOT_CA}/${ROOT_CA}.p12
clean $BASE/${ROOT_CA}/${ROOT_CA}.crt
clean $BASE/${ROOT_CA}/${ROOT_CA}.pem
clean $BASE/${ROOT_CA}/${ROOT_CA}.srl


echo "generate key private key of the root CA"
keytool -genkey -alias "${ROOT_CA_ALIAS}" -keyalg RSA -keysize 2048 -validity ${VALIDITY} -keystore $BASE/$KEYSTORE -storepass $PASSCODE < $BASE/$STDIN/${ROOT_CA}.genkey.stdin

echo "exporting the keystore from jks to pkcs12 format"
keytool -importkeystore -srckeystore $KEYSTORE -srcstorepass $PASSCODE -srcalias "${ROOT_CA_ALIAS}" -destkeystore $BASE/${ROOT_CA}/${ROOT_CA}.p12 -deststoretype PKCS12 -deststorepass ${PASSCODE} -destkeypass ${PASSCODE}

echo "exporting the certifcate from pkcs12 to cer"
openssl pkcs12 -in $BASE/${ROOT_CA}/${ROOT_CA}.p12 -out $BASE/${ROOT_CA}/${ROOT_CA}.crt -nokeys -passin "pass:${PASSCODE}"

echo "exporting the key from pkcs12 to pem"
openssl pkcs12 -in $BASE/${ROOT_CA}/${ROOT_CA}.p12 -out $BASE/${ROOT_CA}/${ROOT_CA}.pem -nodes -nocerts -passin "pass:${PASSCODE}"

keytool -list -v -keystore $BASE/$KEYSTORE -storepass $PASSCODE
