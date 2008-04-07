export SWI_HOME_DIR=/home/nacho/programas/pl-5.6.35/
export PATH=$PATH:$SWI_HOME_DIR/bin:$SWI_HOME_DIR/lib/i386-linux:$SWI_HOME_DIR/lib/:$SWI_HOME_DIR/lib/jpl.jar
export CLASSPATH=.:$SWI_HOME_DIR/lib/jpl.jar

#java -jar simulador.jar logger.config -Djava.library.path=$SWI_HOME_DIR/lib/i386-linu/:$PATH:$SWI_HOME_DIR
java -Djava.library.path=/home/nacho/programas/pl-5.6.35/lib/i386-linux -jar simulador.jar
