FROM openjdk:latest
COPY build/libs/agl-task-tracker.jar agl-task-tracker.jar
EXPOSE 8082
CMD java $JAVA_OPTIONS -jar -Dspring.profiles.active=$SPRING_PROFILE agl-task-tracker.jar