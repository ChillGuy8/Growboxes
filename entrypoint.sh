#!/bin/bash

# Start PostgreSQL
echo "Starting PostgreSQL..."
service postgresql start

POSTGRES_USER=postgres
POSTGRES_PASSWORD=admin

# Wait for PostgreSQL to be fully started
#until psql -U "${POSTGRES_USER}"; do
#  echo "PostgreSQL is starting up..."
#  sleep 2
#done

#psql --command "CREATE USER ${POSTGRES_USER} WITH SUPERUSER PASSWORD '${POSTGRES_PASSWORD}';"
#createdb -O ${POSTGRES_USER} ${POSTGRES_DB}

echo "PostgreSQL started!"

# Start the Spring Boot application
echo "Starting Spring Boot application..."
java org.springframework.boot.loader.launch.JarLauncher