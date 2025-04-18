orbd -ORBInitialPort 1050 &
sleep 2
javac CMS/*.java impl/*.java server/*.java
java -classpath . server.Server -ORBInitialPort 1050 -ORBInitialHost localhost