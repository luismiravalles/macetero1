#
# Shell para ejecutar cada x tiempo desde crontab.
#
# Ejemplo de uso en crontab:
#  */15 * * * * bash /home/luis/macetero/imserso/run.sh
#


cd /home/luis/macetero/imserso
#DESTINATARIOS="luismiravalles@gmail.com,vizcarrmen@gmail.com"
DESTINATARIOS="luismiravalles@gmail.com"
logs=crontab-logs.txt
rm -f $logs
date >>$logs
make costas DESTINATARIOS=$DESTINATARIOS>>$logs 2>&1
make islas DESTINATARIOS=$DESTINATARIOS>>$logs 2>&1
make capitales DESTINATARIOS=$DESTINATARIOS>>$logs 2>&1
date >>$logs