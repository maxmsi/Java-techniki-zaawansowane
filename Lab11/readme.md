## Kompilacja java:

javac -h . Chi2.java

## Kompilacja cpp:

g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux statistics_Chi2.cpp -o statistics_Chi2.o


## Udostępnienie shared library:
	"we have to include it in a new shared library"

g++ -shared -fPIC -o libnative.so statistics_Chi2.o -lc

## Uruchomienie kodu javy z natywna biblioteka z konsoli :
-NATIVE_SHARED_LIB_FOLDER=/home/max/IdeaProjects/Lab11/src/statistics
-java -cp . -Djava.library.path=/NATIVE_SHARED_LIB_FOLDER statistics.Chi2.

->> java -cp . -Djava.library.path=/home/max/IdeaProjects/Lab11/src/statistics Chi2.java


## Do kompilacji z poziomu IDE: 
cp libnative.so /usr/lib 
