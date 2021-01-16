#!/bin/bash
set -ex

MYSQL_HOST=db
MYSQL_USER=root
# cf. docker-compose.yml
MYSQL_PASS=my-secret-pw
# cf. docker-compose.yml
MYSQL_SCHEMA_PATH=/db/timeline.sql
docker-compose run --rm db bash -c "mysql -h $MYSQL_HOST -u $MYSQL_USER --password=$MYSQL_PASS < $MYSQL_SCHEMA_PATH"