#
# Crear la base de datos PHP que es la que utiliza la estufa para funcionar.
# Importa los datos ademas de crear la base de datos.
#
# Ejecutar desde el directorio principal
. ./.env
user=root
password=$BDPASS
nombre="php"
echo "create database $nombre;" | docker compose exec -T db mysql --user=$user --password=$password
cat datos/php-1.sql | docker compose exec -T db mysql $nombre --user=$user --password=$password 