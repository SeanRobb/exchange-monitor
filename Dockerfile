FROM java:7
COPY target/exchange-monitor*.jar /usr/src/exchange-monitor.jar
WORKDIR /usr/src/

ENTRYPOINT ["java", "-jar", "exchange-monitor.jar"]
CMD [""]