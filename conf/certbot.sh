#
# INstalación y preparación de certificado SSL
# para permitir conexiones port https usando certificados
# de Lets encrypt.
#
# Ver http://www.bitsmi.com/2021/06/15/usando-lets-encrypt-y-certbot-para-generar-certificados-tls-para-nginx/ 
#

# Asegurar que tenemos ultima version de snap
sudo snap install core; sudo snap refresh core

# Instalar certbot
sudo snap install --classic certbot

# Crear enlace simbolico
sudo ln -s /snap/bin/certbot /usr/bin/certbot

#
# Instalar Plugins de DuckDns, CREO QUE NO ES NECESARIO.
#
#sudo snap install certbot-dns-duckdns
#sudo snap set certbot trust-plugin-with-root=ok
#sudo snap connect certbot:plugin certbot-dns-duckdns
#sudo certbot plugins # Para ver los plugins instalados y verificar que esta duck-dns




