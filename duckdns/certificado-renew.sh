#
# Tenemos que hacer esto como maximo cada 90 dias.
# Se recomienda hacerlo 30 días antes de que caduque el certificado.
#
# Ejecutaremos 
#     sudo certbot renew situados en /home/macetero.
# 
# Esto  nos deja un fichero /etc/letsencrypt/live/macetero.duckdns.org/fullchain.pem actualizado
# que podemos copiar a su destino
#
# Primero hacemos una copia por si hay que restaurarla.
fecha=$(stat --format "%y" ./local/certs/fullchain.pem | cut -d' ' -f1)
mkdir -p ./local/certs/$fecha
cp ./local/certs/*.pem ./local/certs/$fecha
cp /etc/letsencrypt/live/macetero.duckdns.org/* ./local/certs

