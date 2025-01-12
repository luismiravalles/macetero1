# Cómo instalar un Wordpress para un blog particular
En el fichero dockerfile.yml declaramos un nuevo servicio con el nombre del blog y del context path
que vayamos a querer utilizar.

De momento no le metemos el proxy inverso nginx porque durante la instalación inicial no lo vamos a
usar.

```
    guajiros:
        image: wordpress
        restart: ${RESTART}
        links:
        - db
        ports:
        - 8001:80
        environment:
        WORDPRESS_DB_HOST: db
        WORDPRESS_DB_USER: root
        WORDPRESS_DB_PASSWORD: ${BDPASS}
        WORDPRESS_DB_NAME: guajiros
        WORDPRESS_CONFIG_EXTRA: |
            define('FORCE_SSL_ADMIN', false);
    #         define('WP_HOME', 'https://macetero.duckdns.org/guajiros');
    #         define('WP_SITEURL', 'https://macetero.duckdns.org/guajiros');
        volumes:
        - guajiros:/var/www/html
```

Como se puede comprobar, NO nos interesa declarar las constantes WP_HOME y WP_SITEURL.

Luego creamos en MySql una base de datos con el nombre del blog (Ejemplo guajiros) vacía.

Arrancamos el servicio con el docker compose up guajiros -d.

Navegamos la primera vez a la URL pero **FUNDAMENTAL** desde la red local, sin usar proxy
inverso. POr tanto tenemos que tener acceso al equipo directamente y utilizaremos el puerto
al que hemos mapeado por ejemplo en este caso http://192.168.1.139:8001 . Esto nos llevará
directamente a las páginas de instalación que une vez cubiertas harán que se creen todas
las tablas de la base de datos.

A continuacion vamos a la base de datos y cambiamos los valores "siteurl" y "home" de la
tabla wp_options por el valor real que va a tener el blog. Por ejemplo "https://macetero.duckdns.org/guajiros.

Ahora tenemos que crear el Location proxy en el nginx como sigue:

```
  location  ~ \/guajiros {        
        rewrite ^/guajiros(/.*)$ $1 break;
        proxy_set_header Host $http_host;
        proxy_set_header X-Forwarded-Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass http://guajiros;
    }    
```

Ahora ya podemos navegar al blog tranquilamente. 

**Atención** Todavía no funciona del todo bien.