#!/bin/bash

#
# Esta shell es invocada desde certbot para crear el certificado.
#
#

# Establecer la variable DUCKDNS_TOKEN en el .env
. ./.env

if [[ ! $DUCKDNS_TOKEN ]]; then
    echo "Token no proporcionado. Hay que establecer la variable DUCKDNS_TOKEN"
    exit 1
fi

# Se verifica que Certbot proporcione el parámetro con el valor del dominio que se está validando
if [[ ! $CERTBOT_DOMAIN ]]; then
    echo "Dominio no proporcionado"
    exit 1
fi

# DuckDNS usa un único registro TXT para todos los sub dominios pertenecientes a una misma cuenta
# Para dominios con la forma *.foo-bar.duckdns.org se obtiene el sub dominio principal eliminando las partes "duckdns.org" y el *wildcard*
# En este caso el valor que se pasa a la Rest API de DuckDNS es "foo-bar"
CERTBOT_DOMAIN=${CERTBOT_DOMAIN%%.duckdns.org}
CERTBOT_DOMAIN=${CERTBOT_DOMAIN##*.}

if [[ $1 == "clear" ]]; then
    # En caso que se ejecute el reset de los registros TXT, se envia una petición con "crear=true" a DuckDNS
    echo "Reset del dominio ${CERTBOT_DOMAIN}"
    curl -s "https://www.duckdns.org/update?domains=${CERTBOT_DOMAIN}&token=${DUCKDNS_TOKEN}&txt=whatever&clear=true" 
elif [[ $CERTBOT_VALIDATION ]]; then
    # Con el código de validación que proporciona Certbot, se actualiza el registro TXT de DuckDNS para el sub dominio indicado
    echo "Actualizando registros del dominio ${CERTBOT_DOMAIN}"
    echo "--- Vamos a actualizar el registro TXT de duckdns"
    cmd="https://www.duckdns.org/update?domains=${CERTBOT_DOMAIN}&token=${DUCKDNS_TOKEN}&txt=${CERTBOT_VALIDATION}"
    echo "--- Comando = ${cmd}"
    curl -s "$cmd"
    echo ""
else
    echo "Error: No se ha proporcionado código de validación"
    exit 1
fi

# Se espera un tiempo para que se propague el cambio en el DNS
tiempo=60s
echo "---- Esperando $tiempo a que se propague el cambio.----"
sleep $tiempo
echo "---- Fin de la espera ---"