. ./.env
cd /home/luis/macetero
DEST=/mnt/copia
mkdir -p $DEST/biblioteca
fecha=$(date +%y-%m-%d)
docker compose exec -T db mysqldump --user=root --password=$BDPASS biblioteca >$DEST/biblioteca/biblioteca-$fecha.sql