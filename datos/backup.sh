. ./.env
docker compose exec -T db mysqldump --user=root --password=$BDPASS biblioteca >/tmp/biblioteca.sql