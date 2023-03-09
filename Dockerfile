# Spring boot 프로젝트에 설정한 JDK 17 버전 기반 이미지
FROM openjdk:17-jdk

# Spring boot 프로젝트 build 후 .jar파일 경로 설정
ARG JAR_FILE=./build/libs/spring-tutorial-0.0.1.jar

# JAR 파일 메인 디렉토리에 복사
COPY ${JAR_FILE} app.jar

EXPOSE 4000

# 시스템 진입점 정의
ENTRYPOINT ["java", "-jar", "/app.jar"]