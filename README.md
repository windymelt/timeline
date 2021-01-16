# Timeline #

# Prepare DB

Starts up MySQL and keep window open:

```sh
$ docker-compose up db
```

Then in another terminal execute setup script:

```sh
$ ./script/setup_db_docker.sh
```

# Run

```sh
$ docker-compose up
```

Open [http://localhost:8000/](http://localhost:8000/) in your browser.
