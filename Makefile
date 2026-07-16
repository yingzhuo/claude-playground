ifeq ($(OS), Windows_NT)
	MAKEFILE_PATH := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
	GRADLEW := $(MAKEFILE_PATH)/gradlew.bat
else
	MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
	GRADLEW := $(MAKEFILE_PATH)/gradlew
endif

.DEFAULT_GOAL := clean

.PHONY: \
clean purge rebuild-build-logic \
compile build rebuild \
test \
update-gradle-wrapper \
docker-build docker-push docker-compose-up docker-compose-down docker-remove-dangling

.SILENT:

clean:
	$(GRADLEW) 'clean' -q

purge:
	$(GRADLEW) 'clean' ':build-logic:clean' -q
ifeq ($(OS), Windows_NT)
	if exist $(MAKEFILE_PATH)\.gradle rmdir /s /q $(MAKEFILE_PATH)\.gradle
	if exist $(MAKEFILE_PATH)\build-logic\.gradle rmdir /s /q $(MAKEFILE_PATH)\build-logic\.gradle
else
	rm -rf $(MAKEFILE_PATH)/.gradle
	rm -rf $(MAKEFILE_PATH)/build-logic/.gradle
endif

rebuild-build-logic:
	$(GRADLEW) ':build-logic:clean' ':build-logic:jar' -q

compile:
	$(GRADLEW) 'classes'

build:
	$(GRADLEW) 'build' -x 'test' -x 'check'

rebuild: clean build

test:
	$(GRADLEW) 'test'

update-gradle-wrapper:
	$(GRADLEW) ':wrapper' -q

docker-build:
	$(GRADLEW) ':projects-app:core:jibDockerBuild' -q

docker-push:
	$(GRADLEW) ':projects-app:core:jib' -q

docker-compose-up:
	docker compose up -d

docker-compose-down:
	docker compose down

docker-remove-dangling:
	docker image prune -f
