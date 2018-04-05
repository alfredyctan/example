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

SIGN="localhost"
SIGN_ALIAS=`head -1 $BASE/$STDIN/${SIGN}.genkey.stdin`
VALIDITY=730


mkdir -p $BASE/${SIGN_ALIAS}

clean $BASE/${SIGN}/${SIGN}.csr
clean $BASE/${SIGN}/${SIGN}.crt
clean $BASE/${ROOT_CA}/${ROOT_CA}.srl

echo "generate key private key of the alias ${SIGN_ALIAS}"
keytool -genkey -alias "${SIGN_ALIAS}" -keyalg RSA -keysize 2048 -validity ${VALIDITY} -keystore $BASE/$KEYSTORE -storepass $PASSCODE < $BASE/${STDIN}/$SIGN.genkey.stdin

echo "create certifcate request for the alias ${SIGN_ALIAS}"
keytool -certreq -alias "${SIGN_ALIAS}" -file $BASE/$SIGN/${SIGN}.csr -keypass $PASSCODE -keystore $BASE/$KEYSTORE -storepass $PASSCODE

echo "exporting the keystore from jks to pkcs12 format"
keytool -importkeystore -srckeystore $KEYSTORE -srcstorepass $PASSCODE -srcalias "${SIGN_ALIAS}" -destkeystore $BASE/${SIGN}/${SIGN}.p12 -deststoretype PKCS12 -deststorepass ${PASSCODE} -destkeypass ${PASSCODE}

echo "exporting the key from pkcs12 to pem"
openssl pkcs12 -in $BASE/${SIGN}/${SIGN}.p12 -out $BASE/${SIGN}/${SIGN}.pem -nodes -nocerts -passin "pass:${PASSCODE}"

echo "sign certifcate request for the alias ${SIGN_ALIAS} by own root ca"
openssl x509 -req -CA $BASE/${ROOT_CA}/${ROOT_CA}.crt -CAkey $BASE/${ROOT_CA}/${ROOT_CA}.pem -in $BASE/$SIGN/${SIGN}.csr -out $BASE/$SIGN/${SIGN}.crt -days ${VALIDITY} -CAcreateserial -CAserial $BASE/${ROOT_CA}/${ROOT_CA}.srl

keytool -list -v -keystore $BASE/$KEYSTORE -storepass $PASSCODE

echo "certificate chain..."
echo "`find . -name *.crt`"
