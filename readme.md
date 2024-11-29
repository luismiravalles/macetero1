# --- Instalar nextcloud ---
- Inicialmente es necesario ejecutar sin my.cnf en el docker-compose.yml
- Exponer el puerto para configurar en red local , 
- Naver a http://192.168.1.xxx:9000


# --- Aumentar el filesystem
- sudo vgdisplay  

- sudo lvextend -L +50G /dev/mapper/ubuntu--vg-ubuntu--lv

- sudo resize2fs /dev/mapper/ubuntu--vg-ubuntu--lv
