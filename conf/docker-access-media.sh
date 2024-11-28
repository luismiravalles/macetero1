#
# Si tenemos Docker instalado con SNAP entonces tenemos restricciones de acceso al disco duro
# Para poder acceder a datos como una biblioteca de discos tengo que hacerlo a través de una 
# carpeta que esté baho /mnt o /media.
#
mkdir -p /media/discos
chown luis /media/discos
sudo snap connect docker:removable-media