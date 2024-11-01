#
# Permitirme ejecutar docker sin ser root.
#
sudo setfacl -m user:$USER:rw /var/run/docker.sock

