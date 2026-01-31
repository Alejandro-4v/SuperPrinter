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

init:
	docker exec kafka chmod +x /opt/kafka/bin/scripts/initializeServer.sh
	docker exec kafka bash -c './scripts/initializeServer.sh'

check:
	docker exec kafka chmod +x /opt/kafka/bin/scripts/checkServer.sh
	docker exec kafka bash -c './scripts/checkServer.sh'