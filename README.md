# Exchange Monitor

## Description

This project was made to notify slack when a trade is executed on a cryptocoin exchange.

## Exchanges Supported

* Poloniex
* BitFinex

*Additional Exchanges can be added at request*

## Requirements

Requires Java 7+ as well as differing environment variables depending on the notification type.

### General Environment Variables
Notification type will be configured based off of the environment variables set to the container. 
One notification type can be configured per instance. 
Only the additional variables for the notification type requested should be set.

1. exchange - This is the exchange name that should be used to query
2. api.key - This is the api key needed to view the exchange trade history
3. secret.key - This is the secret needed to pair with the key

### Additional Slack Specific Environment Variables
1. slack.url - This is the Slack Webhook Url needed for notification

### Additional Email Specific Environment Variables
1. email.address - This is the email address where the notification should be sent
2. email.server.username - This is the SMTP email server username for authentication
3. email.server.password - This is the paired password for the SMTP email server username
4. email.server.host - This is the host for where the SMTP email server is located
5. email.server.port - __OPTIONAL__ This is the port to use for the SMTP email server.  If nothing is set the default will be 25

## Slack Webhooks

Api Documentation: https://api.slack.com/incoming-webhooks

Slack Integration App: https://slack.com/apps/A0F7XDUAZ-incoming-webhooks

## Run With Docker

[![](https://badge.imagelayers.io/seanprobb/exchange-monitor:latest.svg)](https://imagelayers.io/?images=seanprobb/exchange-monitor:latest 'Get your own badge on imagelayers.io')

Docker Hub link: https://hub.docker.com/r/seanprobb/exchange-monitor/

### Starting with Docker

#### For Slack Notifications
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e slack_url={slack url} \
    -d seanprobb/exchange-monitor
    
    
#### For Email Notifications
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e email_server_host={SMTP Server Hostname} \
    -e email_server_port={SMTP Server Port (optional: default is 25)} \
    -e email_server_username={SMTP Server Username} \
    -e email_server_password={SMTP Server Password} \
    -e email_address={Notification Email Address} \
    -d seanprobb/exchange-monitor
    
## Donations

Bitcoin Wallet Address: 1NJhd7rNLZLjmNd2FouRwQB9Fqf5Zv3qzC
    
    
    