# Exchange Monitor

## Description

This project was made to notify slack when a trade is executed on a cryptocoin exchange.

## Exchanges Supported

* Poloniex
* BitFinex

*Additional Exchanges can be added at request*

## Requirements

Requires Java 7+ as well as 4 environment variables.

1. exchange - This is the exchange name that should be used to query
2. api.key - This is the api key needed to view the exchange trade history
3. secret.key - This is the secret needed to pair with the key
4. slack.url - This is the Slack Webhook Url needed for notification

## Slack Webhooks

Api Documentation: https://api.slack.com/incoming-webhooks

Slack Integration App: https://slack.com/apps/A0F7XDUAZ-incoming-webhooks

## Run With Docker

[![](https://badge.imagelayers.io/seanprobb/exchange-monitor:latest.svg)](https://imagelayers.io/?images=seanprobb/exchange-monitor:latest 'Get your own badge on imagelayers.io')

Docker Hub link: https://hub.docker.com/r/seanprobb/exchange-monitor/

### Starting with Docker
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e slack_url={slack url} \
    -d seanprobb/exchange-monitor
    
## Donations

Bitcoin Wallet Address: 1NJhd7rNLZLjmNd2FouRwQB9Fqf5Zv3qzC
    
    
    