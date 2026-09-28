FROM gradle:8.14-jdk21

WORKDIR /app

COPY build.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon

COPY src ./src

CMD ["gradle", "test", "--no-daemon"]