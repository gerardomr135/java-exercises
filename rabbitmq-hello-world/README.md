# rabbitmq-hello-world

"Hello World" exercise for RabbitMQ in Java: a producer sends a message to a queue and a consumer receives it.

- `Send` declares a durable quorum queue named `hello` and publishes one message.
- `Recv` declares the same queue and prints every message it receives.

## Run

Start RabbitMQ (management UI at http://localhost:15672, user `guest` / password `guest`):

```
docker compose -f compose.yml up -d
```

Build, then start the consumer in one terminal and the producer in another:

```
mvn clean compile
mvn exec:java -Dexec.mainClass=com.example.Recv
mvn exec:java -Dexec.mainClass=com.example.Send -Dexec.args="my message"
```
