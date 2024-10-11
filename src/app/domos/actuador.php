<?php
require ("comun.php");

conectar();

$actuador=$_GET["id"];
$ahora=time();

$sql  = "UPDATE actuador SET consultado = CURRENT_TIMESTAMP() ";
$sql .= "WHERE id=$actuador";
$result=mysql_query($sql);

$sql = <<<EOT
SELECT actuador.estado,actuador.horaSolicitado,actuador.estadoSolicitado FROM actuador
 WHERE id=$actuador
EOT;

$result=mysql_query($sql);
if(!$result) {
	$error=mysql_error();
	echo "???";
}
while ($row = mysql_fetch_row($result)) { 
	$estado=$row[0];
	$horaSolicitado=strtotime($row[1]);
	$estadoSolicitado=$row[2];
	if(!is_null($estadoSolicitado) &&
		strcmp($estadoSolicitado,$estado) &&
		$ahora > $horaSolicitado) {
		$estado=$estadoSolicitado;
		$estadoSolicitado=null;
		$horaSolicitado=null;
		$sql  ="UPDATE actuador SET estado='$estado', ";
		$sql .=" estadoSolicitado=null, ";
		$sql .=" horaSolicitado=null ";
		$sql .=" WHERE id=$actuador";
		$result=mysql_query($sql);
		if(!$result) {
			$error=mysql_error();
			echo $error . "???\n";
		}
	}
	echo $estado . "\n";
}
?>
