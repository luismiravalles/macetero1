<html>
<body>

<?php

	require ("comun.php");

	$sensor=$_GET["sensor"];
$valor=$_GET["valor"];
$insertar=$_GET["insertar"];
$hora=time();

$link = conectar();

$sql  = "UPDATE sensor SET hora=CURRENT_TIMESTAMP(), valor=$valor";
$sql .= " WHERE id=$sensor ";

$result=mysql_query($sql);

if($insertar == "1") {
	$sql  = "INSERT INTO lectura (idsensor, hora, valor) ";
	$sql .= "VALUES ( $sensor, CURRENT_TIMESTAMP(), $valor )";

	$result=mysql_query($sql);
}
		
echo "Resultado $result <br>";
?>

</body>


</html>