#!/bin/sh 

docker stop kafka-0
docker stop kafka-1
docker stop kafka-2
docker stop schema-registry
docker stop kafka-redpanda

docker rm kafka-0
docker rm kafka-1
docker rm kafka-2
docker rm schema-registry
docker rm kafka-redpanda 
