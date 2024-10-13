# Ejecutar desde el directorio principal
. ./.env
user=root
password=$BDPASS
cat datos/create-biblioteca.sql | docker compose exec -T db mysql --user=$user --password=$password
cat datos/biblioteca.sql | docker compose exec -T db mysql biblioteca --user=$user --password=$password 