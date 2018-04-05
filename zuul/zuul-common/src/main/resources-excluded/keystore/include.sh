
function importkeystore() {
	keytool -importkeystore -srckeystore $1 -srcstoretype PKCS12 -srcstorepass $2 -deststoretype JKS -destkeystore $KEYSTORE -deststorepass $STOREPASS
	echo "===== key of $1 imported ====="
}

function changealias() {
	keytool -changealias -alias "$1" -destalias $2 -keypass $3 $STORE
	echo "===== alias of $1 changed to $2 ====="
}

function keypasswd() {
	keytool -keypasswd -alias "$1" -keypass $2 -new $STOREPASS $STORE
	echo "===== align the key password of $1 with storepass ====="
}

function mvn_rename() {
	# the key has an ugly alias which cannot be administrated by keytool, we need use custom program to rename it
	mvn -f $BASE/src/main/resources-excluded/keystore/rename-alias.pom exec:java "-Dexec.mainClass=org.afc.jsse.KeyStoreTools" "-Dexec.args=rename $KEYSTORE $STOREPASS $1 $2 $3"
	echo "===== rename the non-displayable alias of $1 to $2 ====="
}
