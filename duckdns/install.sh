tmp=/tmp/mycron
#write out current crontab
crontab -l > $tmp
#echo new cron into cron file
cat duckdns/crontab >> $tmp
#install new cron file
crontab $tmp
rm $tmp