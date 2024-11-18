
help:
	echo "Este es el Make"

web:
	sudo docker compose down web
	sudo docker compose up web -d
	sudo docker compose logs web

svn:
	sudo docker compose down svn
	sudo docker compose up svn -d	
	sudo docker compose logs svn


reup:
	sudo docker compose down
	sudo docker compose up -d	