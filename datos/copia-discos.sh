#
# Copiar la carpeta de discos al disco montado con el nombre /mnt/copia
#
# Montar previamente con
#
# mount /dev/sdb1 /mnt/copia
#
#
rsync -av /media/discos /mnt/copia