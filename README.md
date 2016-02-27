# Exchange Monitor

## Description

This project was made to notify slack when a trade is executed on a cryptocoin exchange.

## Exchanges Supported

* Poloniex
* Coinbase
* BitFinex

*Additional Exchanges can be added at request*

## Run With Docker

### Starting
    docker run \
    --name={image name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e slack_url={slack url} \
    -d seanprobb/trade-monitor
    
    
    