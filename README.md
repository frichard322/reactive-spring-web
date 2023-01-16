# Reactive IDDE

- Start application with a specific **profile**:
    - **dev**: local mongodb
    - **prod**: cloud based mongodb

```shell
docker compose --profile <dev/prod> up
```

- Stop application:

```shell
docker compose --profile <dev/prod> down
```
