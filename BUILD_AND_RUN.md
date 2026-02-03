# ThingsBoard - Build and Run Instructions

## Prerequisites

- **Java 17+**
- **Maven 3.6+**
- **Docker** (for PostgreSQL)

Verify installation:
```bash
java -version
mvn -version
docker --version
```

## Step 1: Build from Source

From the project root directory (`/home/eleet/poly/LOG8371/thingsboard`):

```bash
MAVEN_OPTS="-Xmx1024m" NODE_OPTIONS="--max_old_space_size=4096" mvn -T2 license:format clean install -DskipTests
```

This will take a while (20-30 minutes on first build). The main JAR will be created at:
```
application/target/thingsboard-4.4.0-SNAPSHOT-boot.jar
```

## Step 2: Start PostgreSQL

ThingsBoard requires PostgreSQL. Start it with Docker:

```bash
docker run -d --name tb-postgres \
  -e POSTGRES_DB=thingsboard \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15
```

Wait a few seconds for PostgreSQL to be ready:
```bash
docker logs tb-postgres 2>&1 | tail -3
# Should show: "database system is ready to accept connections"
```

## Step 3: Initialize Database Schema

Run the install command to create tables and load demo data:

```bash
java -cp application/target/thingsboard-4.4.0-SNAPSHOT-boot.jar \
  -Dloader.main=org.thingsboard.server.ThingsboardInstallApplication \
  -Dinstall.data_dir=application/target/data \
  -Dinstall.load_demo=true \
  -Dspring.jpa.hibernate.ddl-auto=none \
  -Dinstall.upgrade=false \
  -Dspring.datasource.url=jdbc:postgresql://localhost:5432/thingsboard \
  -Dspring.datasource.username=postgres \
  -Dspring.datasource.password=postgres \
  org.springframework.boot.loader.launch.PropertiesLauncher
```

Wait for: `Installation finished successfully!`

## Step 4: Run ThingsBoard

```bash
java -jar application/target/thingsboard-4.4.0-SNAPSHOT-boot.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/thingsboard \
  --spring.datasource.username=postgres \
  --spring.datasource.password=postgres
```

Or run in background:
```bash
java -jar application/target/thingsboard-4.4.0-SNAPSHOT-boot.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/thingsboard \
  --spring.datasource.username=postgres \
  --spring.datasource.password=postgres &
```

Wait for startup (30-60 seconds), then access: **http://localhost:8080**

## Default Login Credentials

| Role | Email | Password |
|------|-------|----------|
| System Admin | sysadmin@thingsboard.org | sysadmin |
| Tenant Admin | tenant@thingsboard.org | tenant |
| Customer User | customer@thingsboard.org | customer |

## Stopping ThingsBoard

```bash
# Stop the application
pkill -f thingsboard-4.4.0-SNAPSHOT-boot.jar

# Stop PostgreSQL
docker stop tb-postgres
```

## Restarting (After Initial Setup)

If you've already built and initialized the database, you only need:

```bash
# 1. Start PostgreSQL
docker start tb-postgres

# 2. Wait a few seconds, then start ThingsBoard
java -jar application/target/thingsboard-4.4.0-SNAPSHOT-boot.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/thingsboard \
  --spring.datasource.username=postgres \
  --spring.datasource.password=postgres
```

## Troubleshooting

### "relation does not exist" error
You forgot to run Step 3 (database initialization).

### Docker authentication error
```bash
docker logout
# Then retry the docker run command
```

### Out of memory during build
Increase Maven memory:
```bash
MAVEN_OPTS="-Xmx2048m" mvn ...
```

### Port 5432 already in use
```bash
docker stop tb-postgres && docker rm tb-postgres
# Then retry starting PostgreSQL
```
