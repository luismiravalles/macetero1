# Ejecutar desde el directorio principal
user=root
password=pi--2018
echo "drop database php;" | \
    docker compose exec -T db mysql --user=$user --password=$password
