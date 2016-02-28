FROM java:7
COPY . /usr/src/polo
WORKDIR /usr/src/polo/target

ENTRYPOINT ["java", "-jar", "exchange-monitor-1.0-SNAPSHOT.jar"]
CMD [""]