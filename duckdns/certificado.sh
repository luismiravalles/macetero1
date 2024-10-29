#
# Obtener el certificado para el dominio macetero.duckdns.org con certbot.
# Ver como instalarlo previamente en conf/certbot.sh
#
#certbot certonly -n --agree-tos --email foo@bar.com \
#    --manual -d "macetero.duckdns.org"  \
#    --preferred-challenges dns \
#    --manual-auth-hook duckdns/certbot-hook.sh \
#    --manual-cleanup-hook "duckdns/certbot-hook.sh clear"


# Docker instalado con Snap parece ser queno permite montar volumenes que apunten a
# carpetas fuera de mi home,... cosa que no comprendo así que tengo que copiarlo.
mkdir -p ./local/certs
cp /etc/letsencrypt/live/macetero.duckdns.org/* ./local/certs

#
# ACORDARSE DE hacer Portforwarding del puerto 443 en el Router.
#


