# Flix-Tube

Flix-Tube is a Spring Boot application that provides a platform for video uploading, metadata management and searching. The application relies on multiple containers for database storage, messaging, and analytics.

## Installation & Setup

### Prerequisites
Ensure you have the following installed:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- Java 17+
- Maven

### Steps to Run
1. **Start the necessary containers** by running the following command in the root directory of the project:
   ```sh
   docker-compose up -d
   ```
2. **Run the Spring Boot application** using Maven:
   ```sh
   mvn spring-boot:run
   ```
3. Access the application on **port 8090**:
   ```
   http://localhost:8090
   ```

## Required Containers
When running `docker-compose up`, the following seven containers should be running:

| Service         | Description                                  | Port  |
|---------------|----------------------------------|------|
| MySQL         | Database for application data             | 3308 |
| Kafka         | Messaging system for event streaming      | 9092 |
| Zookeeper     | Kafka coordination                        | 2181 |
| Adminer       | Web-based UI for managing MySQL          | 3309 |
| Elasticsearch | Search and indexing engine               | 9200 |
| Kibana        | Data visualization for Elasticsearch     | 5601 |
| Kafka UI     | Kafka management and message visualization | 8091 |

## Verifying the Setup
After starting the containers and the application:
- **Check MySQL**: Open Adminer at [http://localhost:3309](http://localhost:3309) and log in.
- **Check Kafka UI**: Visit [http://localhost:8091](http://localhost:8091) to visualize Kafka topics and messages.
- **Check Elasticsearch**: Visit [http://localhost:9200](http://localhost:9200) to see if Elasticsearch is running.
- **Check Kibana**: Open [http://localhost:5601](http://localhost:5601) for data visualization.

## Uploading a Video
To upload a video file along with its metadata, use the following cURL request:
```shell
curl --location 'http://localhost:8090/videos' \
--form 'metadata={"title":"5 Superstars who faced themselves: 5 Things","description":"Imitation is the sincerest form of flattery, but check out these five Superstars who squared off against their impostors.","channelId":12122,"creator":"WWE"}' \
--form 'file=@"/C:/Users/yashe/Downloads/Telegram Desktop/Finalising aws account setup.mp4"'

```

## Stopping the Services
To stop all running containers:
```sh
   docker-compose down
```
