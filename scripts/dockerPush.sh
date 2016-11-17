#!/usr/bin/env bash

docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"

echo "Branch: ${TRAVIS_BRANCH}"
echo "Tag: ${TRAVIS_TAG}"

if [ "$TRAVIS_PULL_REQUEST" = "false" ]; then
    if [ -n ${TRAVIS_BRANCH} ]; then
        mvn -f exchange-monitor-app/pom.xml docker:build -DpushImageTag -DdockerImageTag=${TRAVIS_BRANCH};
    fi
    if [ -n ${TRAVIS_TAG} ]; then
        mvn -f exchange-monitor-app/pom.xml docker:build -DpushImageTag -DdockerImageTag=${TRAVIS_TAG};
    fi
    mvn -f exchange-monitor-app/pom.xml docker:build -DpushImageTag
fi