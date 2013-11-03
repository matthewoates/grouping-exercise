default: bin java

run:
	cd bin && java Main

bin:
	mkdir -p bin

java:
	javac -d bin src/Main.java

clean:
	rm -rf bin
