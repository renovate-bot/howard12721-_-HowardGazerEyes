FROM eclipse-temurin:25.0.2_10-jdk AS builder

WORKDIR /workspace

COPY gradlew gradlew.bat build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

RUN chmod +x ./gradlew

COPY src ./src

RUN set -eux; \
    app_name="$(sed -n 's/^projectArtifactId=//p' gradle.properties)"; \
    test -n "$app_name"; \
    ./gradlew --no-daemon installDist; \
    mv "build/install/$app_name" /opt/app; \
    ln -s "/app/bin/$app_name" /opt/app/bin/start

FROM eclipse-temurin:25.0.2_10-jre

RUN useradd --system --create-home --home-dir /home/appuser appuser

WORKDIR /app

COPY --from=builder --chown=appuser:appuser /opt/app ./

ENV JAVA_OPTS=""

USER appuser

ENTRYPOINT ["/app/bin/start"]
