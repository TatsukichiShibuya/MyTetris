#####
## if you want to specify a directory java installed explicitly,
## set the java binary directory here
#####
JAVABIN=

#####
## set javafx-sdk directory your installed 
#####
JAVAFXMODULE=lib

#####
##  set proxy server address and port number, if your machine connects
##  to the internet via proxy
JAVADOCPROXY=

#####
## csc room setting
#####
#JAVABIN=/opt/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home/bin/
#JAVAFXMODULE=/opt/Java/JavaFX/javafx-sdk-11.0.2/lib
##JAVADOCPROXY= -J-Dhttp.proxyHost=proxy.csc.titech.ac.jp -J-Dhttp.proxyPort=8080

# OS-dependent commands and separator
ifeq ($(OS),Windows_NT)
	SEP=;
	RM=rd /s /q
	FIND=echo
else
	SEP=:
	RM=rm -rf
	FIND=find
endif

JAVA=$(JAVABIN)java $(JAVAFLAGS)
JAVAC=$(JAVABIN)javac $(JAVACFLAGS)
JAVADOC0=$(JAVABIN)javadoc $(JAVADOCFLAGS)
MKDIR=mkdir
MAKE=make

MODULEPATH=$(JAVAFXMODULE)
CLASSPATH=lib/*$(SEP)
CLASSFLAGS=-classpath "bin$(SEP)$(CLASSPATH)resource"
JAVACCLASSFLAGS=-classpath "$(CLASSPATH)"
MODULEFLAGS=--module-path $(MODULEPATH) --add-modules javafx.controls,javafx.swing
JAVACFLAGS= -encoding utf8 -d bin -sourcepath src $(MODULEFLAGS) $(JAVACCLASSFLAGS) -Xlint:deprecation -Xdiags:verbose -Xlint:unchecked
JAVAFLAGS = $(MODULEFLAGS) $(CLASSFLAGS)
JAVADOCFLAGS= -html5 -encoding utf-8 -charset utf-8 -package -d javadoc -sourcepath src $(JAVADOCPROXY) -link https://docs.oracle.com/javase/jp/14/docs/api -link https://openjfx.io/javadoc/11 $(MODULEFLAGS) $(CLASSFLAGS)

SERVADDR=localhost
NUMBER=`echo \`whoami\`0 | md5 | sed -e "s/[^0-9]//g" |cut -c 1-6`

.PHONY: clean doc

.SUFFIXES: .class .java

.java.class:
	echo $@

.class:
	$(MAKE) bin
	$(eval CLS := $(subst /,.,$(@:src/%=%)))
	$(JAVAC) $@.java 
	$(JAVA) $(CLS)  $(PARAMETER)


Main:
	$(MAKE) src/tetris/window/StartWindow

bin:
	$(MKDIR) bin

clean:
	$(RM) bin
