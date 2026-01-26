.PHONY: shell up down clean start stop topic describe format

up:
	docker compose up -d

down:
	docker compose down

clean:
	docker compose down -v

build:
	docker compose build --no-cache kafka

shell:
	docker exec -it kafka bash

format:
	docker exec kafka ./kafka-storage.sh format --standalone -t random--uuid -c ../config/server.properties

start:
	docker exec -it kafka ./kafka-server-start.sh ../config/server.properties

stop:
	docker exec -it kafka ./kafka-server-stop.sh

topic:
	docker exec kafka bash -c './kafka-topics.sh --create --topic $$TOPIC_NAME --bootstrap-server $$BOOTSTRAP_SERVER --partitions $$PARTITIONS'

describe:
	docker exec kafka bash -c './kafka-topics.sh --describe --topic $$TOPIC_NAME --bootstrap-server $$BOOTSTRAP_SERVER'