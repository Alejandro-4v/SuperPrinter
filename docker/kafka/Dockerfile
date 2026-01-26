FROM openjdk:26-ea-29-jdk-oracle

RUN microdnf install -y curl tar gzip

ENV KAFKA_HOME=/opt/kafka
ENV PATH=$PATH:$KAFKA_HOME/bin

RUN mkdir -p $KAFKA_HOME && \
    curl -o /tmp/kafka.tgz https://dlcdn.apache.org/kafka/4.1.1/kafka_2.13-4.1.1.tgz && \
    tar -xzf /tmp/kafka.tgz -C $KAFKA_HOME --strip-components=1 && \
    rm /tmp/kafka.tgz

WORKDIR $KAFKA_HOME/bin

CMD ["tail", "-f", "/dev/null"]