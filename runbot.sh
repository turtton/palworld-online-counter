#!/usr/bin/env bash

while read line; do
    export "$line"
done < .env

java -jar palworld-online-counter.jar