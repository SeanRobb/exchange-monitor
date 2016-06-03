# Exchange Monitor

## Description

This project was made to actively notify when a trade is executed on a 
crypto-exchange. Also this will save balance snapshots if a database is 
connected.  This is a very modular project so depending on the 
environment settings set the program will preform different operations. 
Hybrid configurations are supported as well.

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

1. exchange (String) - This is the exchange name that should be used to query
2. api_key (String) - This is the api key needed to view the exchange trade history
3. secret_key (String) - This is the secret needed to pair with the key 
4. notification_test (Boolean) - __OPTIONAL__  This will send a test notification when the program is started.  _(Default: false)_
5. monitor_interval (Cron String) - __OPTIONAL__ This is a cron that will be how often your account will be polled for new trades. _(Default: 0 1/1 * * * *)_
6. summary_interval (Cron String) - __OPTIONAL__ The interval at which a wallet summary will be compiled and sent as a notification.  _(Default: 0 30 7 * * MON)_
7. snapshot_interval (Cron String) - __OPTIONAL__ The interval at which a snapshot will be taken of your current crypto currency balances.  This functionality requires a database connection. _(Default: 0 0 0 1/1 * *)_


### Additional Slack Specific Environment Variables
1. slack_url (String) - The Slack Webhook Url needed for notification

### Additional Email Specific Environment Variables
1. email_address (String) - The email address where the notification should be sent
2. email_server_username (String) - The SMTP email server username for authentication
3. email_server_password (String) - The paired password for the SMTP email server username
4. email_server_host (String) - The host for where the SMTP email server is located
5. email_server_port (Integer) - __OPTIONAL__ This is the port to use for the SMTP email server.   _(Default: 25)_

### Additional Database Environment Variables
A database is not required.  If a database is connected then a snapshot of balances will be saved in the snapshot collection. Otherwise no snapshot data will be saved off.

1. instance_id (String) - __OPTIONAL__ This is the instance id for this container.  This is used to allow for multiple container to connect to the same mongo database. _(Default: default)_

#### Mongo Specific
1. mongo_database_host (String) - __OPTIONAL__ The host for where a mongo database is connected. _(Default: mongo)_
2. mongo_database_port (Integer) - __OPTIONAL__ The port on which to communicate with mongo on. _(Default: 27017)_



## Docker

[![](https://badge.imagelayers.io/seanprobb/exchange-monitor:latest.svg)](https://imagelayers.io/?images=seanprobb/exchange-monitor:latest 'Get your own badge on imagelayers.io')

Docker Hub link: https://hub.docker.com/r/seanprobb/exchange-monitor/

### Run with Docker

#### For Just Slack Notifications
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e slack_url={slack url} \
    -d seanprobb/exchange-monitor
    
    
#### For Just Email Notifications
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e api_key={api key} \
    -e secret_key={api key secret} \
    -e email_server_host={SMTP Server Hostname} \
    -e email_server_username={SMTP Server Username} \
    -e email_server_password={SMTP Server Password} \
    -e email_address={Notification Email Address} \
    -d seanprobb/exchange-monitor
    
#### For Just Balance Snapshots  

##### Non Docker Mongo
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    -e mongo_database_host={mongo database host} \
    -e mongo_database_port={mongo database port} \
    -d seanprobb/exchange-monitor
    
    
##### Docker Mongo
    
###### Start Exchange Monitor
    docker run \
    --name={container name} \
    -e exchange={exchange name} \
    --link {mongo container name}:{mongo database host} \
    -d seanprobb/exchange-monitor

## Donations

Bitcoin Wallet Address: 1NJhd7rNLZLjmNd2FouRwQB9Fqf5Zv3qzC

NEM Wallet Address: NA6BOK-GRSWFV-BVOX2P-YMSDIQ-MNZC6A-QT7OMG-KHWA
    
    