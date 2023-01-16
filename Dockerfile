FROM csabasulyok/gradle:7.2-jdk16-alpine as builder
WORKDIR /home/reactive-frim1910
COPY . ./
RUN gradle clean bootJar --no-daemon

FROM csabasulyok/gradle:7.2-jdk16-alpine as runner
WORKDIR /home/reactive-frim1910
COPY --from=builder /home/reactive-frim1910/build/libs/reactive-frim1910-*.jar reactive-frim1910.jar
EXPOSE 8080
CMD java -jar reactive-frim1910.jar
