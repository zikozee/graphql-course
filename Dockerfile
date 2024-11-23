# First stage: JDK with GraalVM
FROM ghcr.io/graalvm/jdk-community:22 AS build

RUN microdnf update -y && \
microdnf install -y gcc glibc-devel zlib-devel libstdc++-devel gcc-c++ && \
microdnf clean all

WORKDIR /opt/base
COPY target/graphql-course app

# Second stage
FROM debian:bookworm-slim

WORKDIR /opt/graphql

LABEL DEVELOPER='Ezekiel Eromosei<ezekiel.eromosei@test.com>'

EXPOSE 8080

COPY --from=build /opt/base/app /opt/graphql/app

ENTRYPOINT ["./app"]