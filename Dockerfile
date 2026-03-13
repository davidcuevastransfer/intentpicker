FROM --platform=${BUILDPLATFORM} tw-base-java.arti.tw.ee/tw-base-java:21 as builder
COPY build/libs/intentpicker.jar ./service.jar
RUN java -Djarmode=tools -jar service.jar extract --layers --launcher

FROM tw-base-java.arti.tw.ee/tw-base-java:21
COPY --from=builder /app/artifacts/service/dependencies/ ./
COPY --from=builder /app/artifacts/service/spring-boot-loader/ ./
COPY --from=builder /app/artifacts/service/application/ ./
ENTRYPOINT ["/app/entrypoint.sh", "-cp" , "/app/artifacts", "org.springframework.boot.loader.launch.JarLauncher"]
