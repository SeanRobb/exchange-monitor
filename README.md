# Exchange Monitor

## Description

This project was made to actively notify when a trade is executed on a crypto-exchange.

## Exchanges Supported

* Poloniex
* BitFinex

*Additional Exchanges can be added at request*

## Notification Types

### Slack Webhooks

Api Documentation: https://api.slack.com/incoming-webhooks

Slack Integration App: https://slack.com/apps/A0F7XDUAZ-incoming-webhooks

### Email

All you need is a SMTP server or relay.

## Requirements

Requires Java 7+ as well as differing environment variables depending on the notification type.

### General Environment Variables
Notification type will be configured based off of the environment variables set to the container. 
One notification type can be configured per instance. 
Only the additional variables for the notification type requested should be set.

1. exchange *(String)* - This is the exchange name that should be used to query
2. api.key *(String)* - This is the api key needed to view the exchange trade history
3. secret.key *(String)* - This is the secret needed to pair with the key
4. monitor.interval *(String)* - __OPTIONAL__ This is a cron that will be how often your account will be polled for new trades. If nothing is set the default will be every minute.
5. notification.test *(Boolean)* - __OPTIONAL__  This will send a test notification when the program is started.  If nothing is set the default will be false.


### Additional Slack Specific Environment Variables
1. slack.url *(String)* - This is the Slack Webhook Url needed for notification

### Additional Email Specific Environment Variables
1. email.address *(String)* - This is the email address where the notification should be sent
2. email.server.username *(String)* - This is the SMTP email server username for authentication
3. email.server.password *(String)* - This is the paired password for the SMTP email server username
4. email.server.host *(String)* - This is the host for where the SMTP email server is located
5. email.server.port *(Integer)* - __OPTIONAL__ This is the port to use for the SMTP email server.  If nothing is set the default will be 25



## Docker

[![](https://badge.imagelayers.io/seanprobb/exchange-monitor:latest.svg)](https://imagelayers.io/?images=seanprobb/exchange-monitor:latest 'Get your own badge on imagelayers.io')

Docker Hub link: https://hub.docker.com/r/seanprobb/exchange-monitor/

### Run with Docker

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
    
#### For Test Slack Notification on Startup
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e slack_url={slack url} \
    -e notification_test=true \
    -d seanprobb/exchange-monitor
    
## Donations

Bitcoin Wallet Address: 1NJhd7rNLZLjmNd2FouRwQB9Fqf5Zv3qzC

NEM Wallet Address: NA6BOK-GRSWFV-BVOX2P-YMSDIQ-MNZC6A-QT7OMG-KHWA
    
    