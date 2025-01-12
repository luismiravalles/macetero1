
help:
	echo "Este es el Make"

web:
	docker compose down web
	docker compose up web -d
	docker compose logs web

web-restart:
	docker compose restart web
	docker compose logs -n 10 web	

svn:
	sudo docker compose down svn
	sudo docker compose up svn -d	
	sudo docker compose logs svn

navidrome:
	sudo docker compose down navidrome
	sudo docker compose up navidrome -d
	sudo docker compose logs navidrome

guajiros:
	docker compose down guajiros
	docker compose up guajiros -d
	docker compose logs guajiros

guajiros-remove:
	docker compose stop guajiros
	docker compose rm -f guajiros
	docker volume rm macetero_guajiros	

reup:
	sudo docker compose down
	sudo docker compose up -d	

