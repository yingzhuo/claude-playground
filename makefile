ifeq ($(OS), Windows_NT)
	MAKEFILE_PATH := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
	GRADLEW := $(MAKEFILE_PATH)/gradlew.bat
else
	MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
	GRADLEW := $(MAKEFILE_PATH)/gradlew
endif

.DEFAULT_GOAL := clean

.PHONY: clean purge rebuild-build-logic compile build rebuild check test update-gradle-wrapper

.SILENT:

clean:
	$(GRADLEW) 'clean' -q

purge:
	$(GRADLEW) 'clean' ':buildSrc:clean' -q
	rm -rf $(MAKEFILE_PATH)/.gradle
	rm -rf $(MAKEFILE_PATH)/buildSrc/.gradle

rebuild-build-logic:
	$(GRADLEW) ':buildSrc:clean' -q
	$(GRADLEW) ':buildSrc:jar' -q

compile:
	$(GRADLEW) 'classes'

build:
	$(GRADLEW) 'build' -x 'test' -x 'check'

rebuild: clean build

check:
	$(GRADLEW) 'check'

test:
	$(GRADLEW) 'test'

update-gradle-wrapper:
	$(GRADLEW) ':wrapper' -q

stop-gradle-daemon:
	$(GRADLEW) --stop -q > /dev/null
