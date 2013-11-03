# grabbed from http://stackoverflow.com/questions/2214575/passing-arguments-to-make-run
ifeq (run, $(firstword $(MAKECMDGOALS)))
  # use the rest as arguments for "run"
  RUN_ARGS := $(wordlist 2, $(words $(MAKECMDGOALS)), $(MAKECMDGOALS))
  # ... and turn them into do-nothing targets
  $(eval $(RUN_ARGS):;@:)
endif

default: bin java

.PHONY: clean run bin java

run:
	cd bin && java Main $(RUN_ARGS)

bin:
	mkdir -p bin

java:
	javac -d bin src/Main.java


clean:
	rm -rf bin
