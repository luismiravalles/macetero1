# Ejecutar desde el directorio principal
user=root
password=pi--2018
cat datos/create-biblioteca.sql | docker compose exec -T db mysql --user=$user --password=$password
cat datos/biblioteca.sql | docker compose exec -T db mysql biblioteca --user=$user --password=$password 