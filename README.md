Usage: java -jar Gmail.jar <Email> <password> <BTCGuild APIKey>
Example: java -jar Gmail.jar KingLinkTiger@gmail.com mysecretpass 75de7ce3003342cdad330a977627aa25

**NOTE**
This program does not parse the JSON from BTCGuild my webserver does. Also note that
BTCGuild has a 60 Second restriction when using their API keys so if you make a chrontab with
this make sure that the interval is more than one minute.
