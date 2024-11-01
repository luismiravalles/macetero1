
help:
	echo "Este es el Make"

web:
	sudo docker compose down web
	sudo docker compose up web -d


reup:
	sudo docker compose down
	sudo docker compose up -d	