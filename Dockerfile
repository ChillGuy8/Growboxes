#
#   EXPLANATION WHY I use only dockerfile
#   free hosting doesn't run docker compose files so i can't create db image and connect to it
#   so i creating one image where build server and db, so server connect to db by localhost
#

# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /build

# Copy the Maven wrapper with execute permissions
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml .

# Set permissions for the Maven wrapper
RUN chmod +x mvnw

# Download dependencies
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

# Copy source code and build the application
COPY src/ ./src
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

# Stage 2: Extract layers for caching
FROM build AS extract
WORKDIR /build

# Extract the application into layers using Spring Boot Layertools
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

# Debugging step: check if 'JarLauncher' exists after extraction
RUN ls -l target/extracted/spring-boot-loader

# Stage 3: Create a minimal runtime image with PostgreSQL and Spring Boot
FROM eclipse-temurin:17-jre-jammy AS final

# Install PostgreSQL and required packages
RUN apt-get update && apt-get install -y postgresql postgresql-contrib

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASS=admin
ENV POSTGRES_DB=userdb

RUN mkdir -p /var/run/postgresql && chown -R postgres:postgres /var/run/postgresql

# Set up PostgreSQL initialization
USER postgres
RUN echo "local all ${POSTGRES_USER} trust" | cat > ./etc/postgresql/14/main/pg_hba.conf
RUN echo "host all all 127.0.0.1/32 md5" | cat >> ./etc/postgresql/14/main/pg_hba.conf
RUN service postgresql start && \
    psql --command "ALTER USER ${POSTGRES_USER} with password '${POSTGRES_PASS}';" && \
    createdb -O ${POSTGRES_USER} ${POSTGRES_DB}

USER root

# Create a non-privileged user for Spring Boot
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

# Copy the application layers from the "extract" stage
WORKDIR /app
COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./

# Copy custom entrypoint script to manage PostgreSQL and Spring Boot startup
COPY entrypoint.sh /usr/local/bin/entrypoint.sh

USER root
RUN chmod +x /usr/local/bin/entrypoint.sh

# Expose port 80 for Spring Boot and port 5432 for PostgreSQL
EXPOSE 80

# Define the entrypoint
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
