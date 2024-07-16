# 该镜像需要依赖的基础镜像
FROM openjdk:8

ARG JAR_FILE=deploy-service.jar

COPY ${JAR_FILE} app.jar

EXPOSE CONTAINER_PORT

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms512m -Xmx1024m"

CMD java $JAVA_OPTS -jar app.jar
