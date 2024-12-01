# La carpeta de los discos.
DIR=/mnt/c/discos

#
# La ejecución por defecto. Puede cambiar segun lo que este desarrollando
#
default:
	@mvn -q -Dcmd=FindSinTag -Ddir=$(DIR) exec:java

#
# Comando Contar. Muestra el numero  total de archivos .mp3.
#
Contar:
	@mvn -q -Dcmd=Contar -Ddir=$(DIR) exec:java	