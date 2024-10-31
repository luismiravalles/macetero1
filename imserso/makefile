

down:
	sudo docker compose down

up:
	sudo docker compose  up -d --remove-orphans

compile:
	sudo docker run -it --rm --name maven \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo clean compile

test:
	sudo docker run -it --rm --name maven \
		--network imserso_default \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo test


bash:
	sudo docker run -it --rm --name maven \
		--network imserso_default \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		bash
