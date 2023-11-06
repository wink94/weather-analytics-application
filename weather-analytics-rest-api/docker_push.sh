docker build -t registry.digitalocean.com/weather-analytics-rest-api:latest .

docker tag registry.digitalocean.com/weather-analytics-rest-api registry.digitalocean.com/weather-analytics-rest-api/weather-api:latest

docker push registry.digitalocean.com/weather-analytics-rest-api/weather-api