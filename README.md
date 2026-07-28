# video-processing-api
A simple video processing api created in spring boot and FFmpeg

# Tech stack
Java 21, Spring boot, Docker, MySQL, FFmpeg

# Prerequites
Provide your own MySQL database and place it in the application.properties and in the compose.yaml

application.properties
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

compose.yaml
```
  'MYSQL_DATABASE='
  'MYSQL_PASSWORD='
  'MYSQL_ROOT_PASSWORD='
  'MYSQL_USER='
```
```
  SPRING_DATASOURCE_URL:
  SPRING_DATASOURCE_USERNAME:
  SPRING_DATASOURCE_PASSWORD:
```
# Installation

1. Clone the git repository
   ```
   git clone https://github.com/arcyck/video-processing-api.git
   ```
2. Go to the root directory of the project and run
   ```
   docker compose up --build
   ```
3. After it is done building it will be accessable at port 8080 and will be interactable within the swagger documentation at
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
