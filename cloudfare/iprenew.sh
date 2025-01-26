#
# Renovar la IP
#
. /home/luis/macetero/.env
echo $CLOUDFLARE_TOKEN
echo $CLOUDFLARE_ZONE_ID
CLOUDFLARE_EMAIL="luismiravalles@gmail.com"
MIIP=$(curl ifconfig.me)
echo "Mi IP pública es $MIIP"

echo "Consultando las zonas..."
curl https://api.cloudflare.com/client/v4/zones \
    -H "X-Auth-Email: $CLOUDFLARE_EMAIL" \
    -H "X-Auth-Key: $CLOUDFLARE_GLOBAL_API_KEY"
echo
echo "==========================================================================================="

echo "Consultando los Registros DNS..."
curl -X GET "https://api.cloudflare.com/client/v4/zones/$CLOUDFLARE_ZONE_ID/dns_records" \
     -H "X-Auth-Email: $CLOUDFLARE_EMAIL" \
     -H "X-Auth-Key:  $CLOUDFLARE_GLOBAL_API_KEY" \
     -H "Content-Type:application/json"
echo
echo "==========================================================================================="



echo "Actualizando IP para el macetero.org"
curl https://api.cloudflare.com/client/v4/zones/$CLOUDFLARE_ZONE_ID/dns_records/$CLOUDFLARE_RECORD_ID \
    -X PATCH \
    -H 'Content-Type: application/json' \
    -H "X-Auth-Email: $CLOUDFLARE_EMAIL" \
    -H "X-Auth-Key: $CLOUDFLARE_GLOBAL_API_KEY" \
    -d "{\"content\": \"$MIIP\"}"
echo     
echo "==========================================================================================="
