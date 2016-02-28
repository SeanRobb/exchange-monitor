# Exchange Monitor

## Description

This project was made to notify slack when a trade is executed on a cryptocoin exchange.

## Exchanges Supported

* Poloniex

*Additional Exchanges can be added at request*

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
    
    
    
