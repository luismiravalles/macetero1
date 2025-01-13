#
# Shell de copia de seguridad del sitio Wordpress de los Guajiros.
#
cd /home/luis/macetero
nombre=guajiros
. ./.env
DEST=/mnt/copia
mkdir -p $DEST/$nombre
fecha=$(date +%y-%m-%d)
docker compose exec -T db mysqldump --user=root --password=$BDPASS guajiros >$DEST/$nombre/$nombre-$fecha.sql

#
# Ver el punto de montaje,
#
contenido=$(docker inspect macetero_$nombre | grep Mountpoint | cut -d\" -f4)

mkdir -p $DEST/$nombre/content
rsync -uav $contenido $DEST/$nombre/content