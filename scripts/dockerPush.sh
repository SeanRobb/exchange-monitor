#!/usr/bin/env bash

docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"

echo "Pull Request: ${TRAVIS_PULL_REQUEST}"
echo "Branch: ${TRAVIS_BRANCH}"
echo "Tag: ${TRAVIS_TAG}"

if [ "$TRAVIS_PULL_REQUEST" = "false" ]; then
    if [ -n ${TRAVIS_BRANCH} ]; then
        mvn -f exchange-monitor-app/pom.xml docker:build -DdockerImageTags=${TRAVIS_BRANCH} -DpushImageTag;
    fi
    if [ -n ${TRAVIS_TAG} ]; then
        mvn -f exchange-monitor-app/pom.xml docker:build -DdockerImageTags=${TRAVIS_TAG} -DpushImageTag;
    fi
    mvn -f exchange-monitor-app/pom.xml docker:build -DpushImageTag
fi