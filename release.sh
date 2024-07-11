#!/bin/sh
./mvnw release:clean -Pwithout-pitest
./mvnw release:prepare -Pwithout-pitest
./mvnw release:perform

