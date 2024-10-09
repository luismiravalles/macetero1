<html>

<head>
<style>
	  body {
			font-family: Arial;
			background-color:lightgray;
			}
	  table {
			background-color: white;
			margin: 20px;
			border-radius: 16px;
			box-shadow: 3px 3px 3px;
	  }
	  table,td {
			font-size: 28px;
			padding: 5px;
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


<table border="0" style="border: solid 1px;">
  <tr class='cabeceraTabla'>
	<td>Hora</td>
	<td>Valor</td>
  </tr>

<?php

$id=$_GET["id"];


$sql = <<<EOT
	SELECT  lectura.hora , lectura.valor, sensor.medida
	FROM lectura
	LEFT JOIN sensor ON sensor.id = lectura.idsensor
	WHERE lectura.idsensor = $id
	ORDER BY lectura.hora DESC 
	LIMIT 0 , 30
EOT;

$result=mysql_query($sql);
if(!$result) {
	$error=mysql_error();
	echo "<tr><td>Result es $result , $error </td></tr>";
}
while ($row = mysql_fetch_row($result)) { 
	echo <<<EOT
	<tr>
		<td>${row[0]}</td>
		<td class="valor">${row[1]}${row[2]}</td>
	</tr>
EOT;
}


?>
</table>

</body>
</html>