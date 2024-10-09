<?php
// ini_set('display_errors', 1); ini_set('display_startup_errors', 1); error_reporting(E_ALL);
require ("comun.php");
conectar();
$actuador=$_GET["actuador"];
$estado=$_GET["estado"];
$horas=$_GET["horas"];

$link = conectar();
if ( !is_null($horas)) {
	$horaSolicitado = date_create();
	date_timestamp_set($horaSolicitado,strtotime("+" . $horas . " hours"));
	$stHora = date_format($horaSolicitado, "Y-m-d H:i:s");
	$sql  = "UPDATE actuador SET estadoSolicitado = '$estado', hora = CURRENT_TIMESTAMP(), ";
	$sql .= " horaSolicitado = '$stHora' ";
	$sql .= " WHERE id=$actuador";
} else {
	$sql  = "UPDATE actuador SET estado = '$estado', hora = CURRENT_TIMESTAMP() ";
	$sql .= "WHERE id=$actuador";
}

$result=mysql_query($sql);
if(!$result) {
	$error=mysql_error();
	echo $sql ;
	echo $error;
} else {
	header("Location: movil.php");
	exit;
}
?>
