#!/bin/bash
docker stop prometheus_lightmin
docker rm prometheus_lightmin
docker run \
  -d --name prometheus_lightmin \
  --network host \
  -v ./config/prometheus.yml:/etc/prometheus/prometheus.yml \
  -p 9090:9090 prom/prometheus
