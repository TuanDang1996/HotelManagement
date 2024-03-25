# HotelManagement

#### Start application:
  - Prepare environment: Java 17 + gradle 8.4
  - Build project: gradle build
  - Start application: docker-compose up -d --build

#### Documentation:

- Swagger url: http://localhost:8080/swagger-ui/index.html#
- Tracing + Monitoring: using grafana + prometheus + otel-collector + loki + tempo + zipkin + jaeger
    ![img_2.png](img_2.png)
  -  Grafana url: http://localhost:3000/login (admin/admin)
  ![img.png](img.png)
  - Zipkin url: http://localhost:9411/zipkin
  ![img_1.png](img_1.png)