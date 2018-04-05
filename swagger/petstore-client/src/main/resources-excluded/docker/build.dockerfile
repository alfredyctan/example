FROM ubuntu:16.04 as scripts
WORKDIR /working
ADD scripts/service/start.sh /working
ADD scripts/service/spring-boot/start-service.sh /working
RUN find . -name '*.sh' -exec chmod 750 {} \;

FROM scratch
WORKDIR /opt/sys/app/${project.artifactId}
COPY --from=scripts /working ./
ADD ${project.artifactId}-${project.version}.jar /opt/sys/app/${project.artifactId}/lib/
ADD config /opt/sys/app/${project.artifactId}/config

VOLUME /tmp

CMD ["./start.sh"]
