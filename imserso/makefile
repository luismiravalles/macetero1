ARGS=

down:
	docker compose down

# Arrancar el Selenium
up:
	docker compose  up -d --remove-orphans

compile:
	 docker run --rm --name maven \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo clean compile

# Ejecutar
run:
	docker run --rm --name maven \
		--network imserso_default \
		--env TZ=Europe/Madrid \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo \
		-D test="TestTurismoSocial#run" \
		test
	cp destinos-imserso.html index.html

santander:
	docker run --rm --name santander \
		--network imserso_default \
		--env TZ=Europe/Madrid \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo \
		-D test="TestTurismoSocial#runSantander" \
		test
	
bilbao:
	docker run --rm --name bilbao \
		--network imserso_default \
		--env TZ=Europe/Madrid \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo \
		-D test="TestTurismoSocial#runBilbao" \
		test	

# Ejecutar un test diferente
test:
	docker run --rm --name maven \
		--network imserso_default \
		--env TZ=Europe/Madrid \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		mvn -Dmaven.repo.local=/maven-repo \
		-D test="TestTurismoSocial#testEnviarResultados" \
		test



# Abrir una shell dentro del contenedor de Maven para hacer pruebas.
bash:
	docker run -it --rm --name maven \
		--network imserso_default \
		--env TZ=Europe/Madrid \
		-v /maven-repo:/maven-repo -v ./:/project \
		-w /project maven:3.3-jdk-8 \
		bash
