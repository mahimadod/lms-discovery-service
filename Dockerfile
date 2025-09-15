FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-XX:StartFlightRecording=filename=/tmp/recording.jfr,dumponexit=true,settings=profile", "-XX:+UnlockDiagnosticVMOptions", "-XX:+DebugNonSafepoints", "-jar", "/app.jar"]
