<?php

header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");
?>
<html>

<head>
	<meta charset="UTF-8">
<style>
	  body {
			font-family: Arial;
			background-color: rgb(123, 103, 85);
			}
	  table {
			background-color: white;
			margin: 20px;
			border-radius: 16px;
			box-shadow: 3px 3px 3px;
	  }
	  .tablaTemperaturas {
		font-size: 40px;
	  }
	  .tablaActuadores {
		font-size: 28px;
	  }
	  table,td {
			padding: 20px;
			border-radius: 8px;
			}
	  .valor {
			font-weight: bold;
			text-align: center;
			font-size: 200%;
			color: darkred;
	  }
	
	.cabeceraTabla {
		background-color: darkblue;
		color:white;
	}
	a {
		padding-right:10px;
		padding-left: 10px;
	}
</style>
</head>

<body style='font-family=Arial; font-size: 150%;'>


<?php

require ("comun.php");

conectar();

?>


<table border="0" style="border: solid 1px;" class="tablaTemperaturas">

<?php

$usuario=$_GET["usuario"];
$password=$_GET["password"];

mysql_query("SET lc_time_names = 'es_ES'");

$sqlUsuario = <<<EOT
SELECT 1 
FROM usuario
WHERE usuario='$usuario'
  AND password='$password'
EOT;

$permiso=True;
$result=mysql_query($sqlUsuario);
if(!$result || mysql_num_rows($result)==0) {
	$permiso=False;
} 

$sql = <<<EOT
	SELECT nombre,  valor, medida, hora, sensor.id, foto.foto
	FROM sensor
	LEFT JOIN foto ON foto.id = sensor.id
	WHERE sensor.activo=1
EOT;

$result=mysql_query($sql);
if(!$result) {
	$error=mysql_error();
	echo "<tr><td>Result es $result , $error </td></tr>";
}
while ($row = mysql_fetch_row($result)) { 
	$hora=formatFecha($row[3]);
	$foto=base64_encode($row[5]);
	echo <<<EOT
	<tr>
		<td><a href="graficosensor.php?id=$row[4]&horas=48"><img src="data:image/jpeg;base64,$foto" alt="Foto"></a><br></td>
		<td><a href="graficosensor.php?id=$row[4]"><img src="chart.png"/></a> <a href="querysensor.php?id=$row[4]">$row[0]</a></td>
		<td class="valor">${row[1]}${row[2]}</td>
		<td>$hora</td>
	</tr>
EOT;
}


?>
</table>


<table border="0" style="border: solid 1px;" class="tablaActuadores">
  <tr class='cabeceraTabla'>
	<td>Aparato</td>
	<td>Estado</td>
	<td>Hora</td>
	<td>Consultado</td>
	<td>Acciones</td>
  </tr>

<?php

$sql = <<<EOT
SELECT id, nombre,  estado, hora, consultado from actuador where activo = 1
EOT;

$result=mysql_query($sql);
if(!$result) {
	$error=mysql_error();
	echo "<tr><td>Result es $result , $error </td></tr>";
}


while ($row = mysql_fetch_row($result)) { 
	$hora=formatFecha($row[3]);
	$consultado=formatFecha($row[4]);
	echo <<<EOT
	<tr>
		<td>$row[1]</td>
		<td>$row[2]</td>
		<td>$hora</td>
		<td>$consultado</td>
EOT;
if($permiso) {
	if($row[2]=="ON") {
		$texto="Apagar";
		$accion="OFF";
	} else {
		$texto="Encender";
		$accion="ON";
	}
	echo <<<EOT
		<td>
				<a href="set.php?actuador=$row[0]&estado=${accion}"> ${texto} </a>
				
		</td>	
EOT;
}
	echo <<<EOT
	</tr>
EOT;
}


?>
</table>

</body>
</html>