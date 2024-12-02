# La carpeta de los discos.
DIR="/mnt/c/tmp"
OUT=/mnt/c/tmp/result.txt

#
# La ejecución por defecto. Puede cambiar segun lo que este desarrollando
#
default:
	@mvn -q -Dcmd=FindSinTag -Ddir=$(DIR) -Dout=$(OUT) exec:java


PonerTag:
	@mvn -q -Dcmd=PonerTag -Ddir=$(DIR)  -Dout=$(OUT) exec:java	

#
# Comando Contar. Muestra el numero  total de archivos .mp3.
#
Contar:
	@mvn -q -Dcmd=Contar -Ddir=$(DIR) -Dout=$(OUT) exec:java	