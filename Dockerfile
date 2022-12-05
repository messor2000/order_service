FROM openjdk:11
COPY target/PilotesOrderServiceAPI-0.0.1-SNAPSHOT.jar PilotesOrderServiceAPI.jar
EXPOSE 8082
CMD java $JAVA_OPTIONS -jar -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE PilotesOrderServiceAPI.jar $JAVA_ARGUMENTS