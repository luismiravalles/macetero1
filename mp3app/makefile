# La carpeta de los discos.
DIR="/media/discos/2024-12"
OUT=/media/discos/result-clasic.txt

#
# La ejecución por defecto. Puede cambiar segun lo que este desarrollando
#
default:
	@mvn -q -Dcmd=FindSinTag -Ddir=$(DIR) -Dout=$(OUT) exec:java


PonerTag:
	@mvn -q -Dcmd=PonerTag -Ddir=$(DIR)  -Dout=$(OUT) exec:java	

VerGenero:
	@mvn -q -Dcmd=VerGenero -Ddir=$(DIR)  -Dout=$(OUT) exec:java		

#
# Convierte de 320 a 192 el bitrate de los archivos de la carpeta DIR
#
BitRate:
	@mvn -q -Dcmd=BitRate -Ddir=$(DIR)  -Dout=$(OUT) exec:java	

Repetidos:
	@mvn -q -Dcmd=Repetidos -Ddir=$(DIR)  -Dout=$(OUT) exec:java				

Arreglar:
	@mvn -q -Dcmd=Arreglar -Ddir=$(DIR)  -Dout=$(OUT) exec:java		

#
# Comando Contar. Muestra el numero  total de archivos .mp3.
#
Contar:
	@mvn -q -Dcmd=Contar -Ddir=$(DIR) -Dout=$(OUT) exec:java	

