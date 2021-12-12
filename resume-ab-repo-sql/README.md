## How to connect to PostgreSQL

```shell
docker run \
  --name some-postgres \
  -e POSTGRES_PASSWORD=postgrespass \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=resumedevdb \
  -p 5432:5432 \
  -d postgres
```
