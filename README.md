# SuperPrinter

## Setup

To get the project running, execute the following commands in order:

1. Start the containers:
   ```bash
   make up
   ```

2. Format the Kafka storage:
   ```bash
   make format
   ```

3. Start the Kafka server:
   ```bash
   make start
   ```
   **Note:** This command will lock the terminal. You will need to open a new terminal for the next steps.

4. Create the topic (in a new terminal):
   ```bash
   make topic
   ```

## Other Commands

*   `make down`: Stops the Docker containers.
*   `make clean`: Stops the Docker containers and removes volumes.
*   `make shell`: Opens a bash shell inside the Kafka container.
*   `make stop`: Stops the Kafka server.
*   `make describe`: Describes the `printer-queue` topic.
*   `make init`: A shortcut command defined in the Makefile (combines format and topic).
