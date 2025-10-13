#
# Shell para ejecutar cada x tiempo desde crontab.
#
# Ejemplo de uso en crontab:
#  */15 * * * * bash /home/luis/macetero/imserso/run.sh
#
cd /home/luis/macetero/imserso
logs=crontab-logs.txt
rm -f $logs
date >>$logs
make run2025 >>$logs 2>&1
make islas >>$logs 2>&1
make capitales >>$logs 2>&1
date >>$logs