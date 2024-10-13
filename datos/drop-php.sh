# Ejecutar desde el directorio principal
. ./.env
user=root
password=$BDPASS
echo "drop database php;" | \
    docker compose exec -T db mysql --user=$user --password=$password
