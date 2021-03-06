FROM gradle:jdk17 AS gradle
WORKDIR /app
COPY . .
RUN gradle bootJar

FROM openjdk:17 as runtime
WORKDIR /app
ENV PORT 8080
ENV SPRING_PROFILE production
ENV DATABASE_URL ""
ENV ISSUER_URL "https://keycloak-tidsbanken-case.herokuapp.com/auth/realms/tidsbankencase"
ENV JWKS_URI "https://keycloak-tidsbanken-case.herokuapp.com/auth/realms/tidsbankencase/protocol/openid-connect/certs"
ENV CLIENT_ID "tidsbanken-id"
ENV CLIENT_SECRET "client-secret"
ENV DDL_AUTO "create"
ENV APP_ORIGIN "http://localhost:4200"
COPY --from=gradle /app/build/libs/*.jar /app/app.jar
RUN chown -R 1000:1000 /app
USER 1000:1000
ENTRYPOINT ["java","-jar","app.jar"]

