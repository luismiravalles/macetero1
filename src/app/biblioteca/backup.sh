# Copia de Seguridad de la Base de datos de Biblioteca
# Se envia por correo la copia.
fecha=$(date +%y%m%d)
#mount /dev/sda1 /mnt/usb
dir=/mnt/w/raspberrypi
mkdir -p $dir
mysqldump --skip-comments -u root -ppi--2018 biblioteca >$dir/biblioteca_$fecha.sql
mysqldump --skip-comments -u root -ppi--2018 php >$dir/php_$fecha.sql
mysqldump --skip-comments -u root -ppi--2018 bote >$dir/bote_$fecha.sql
#umount /dev/sda1

