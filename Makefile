ORG := $(shell cat build.gradle | grep "^group" | cut -f 2 -d ' ' | sed "s/'//g")
REPO := $(shell cat settings.gradle | grep "rootProject.name" | cut -f 3 -d ' ' | sed "s/'//g")
TAG := $(shell cat build.gradle | grep "^version" | cut -f 2 -d ' ' | sed "s/'//g")
IMG := ${ORG}/${REPO}:${TAG}
DEVDB := ga4gh-starter-kit.dev.db
JAR := ga4gh-starter-kit-passport-admin.jar

Nothing:
	@echo "No target provided. Stop"

.PHONY: clean-sqlite
clean-sqlite:
	@rm -f ${DEVDB}

.PHONY: clean-jar
clean-jar:
	@rm -f ${JAR}

.PHONY: clean-all
clean-all: clean-sqlite clean-jar

.PHONY: sqlite-db-build
sqlite-db-build: clean-sqlite
	@sqlite3 ${DEVDB} < database/sqlite/create-schema.migrations.sql

.PHONY: sqlite-db-populate-dev-dataset
sqlite-db-populate-dev-dataset:
	@sqlite3 ${DEVDB} < database/sqlite/populate-dev-dataset.migrations.sql

.PHONY: sqlite-db-refresh
sqlite-db-refresh: clean-sqlite sqlite-db-build sqlite-db-populate-dev-dataset
