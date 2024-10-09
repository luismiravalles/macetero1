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
			background-color: rgb(0, 44, 55);
			}
	  table,.tabla {
			background-color: white;
			margin: 20px;
			box-shadow: 3px 3px 3px;
			border-top-left-radius: 20px;
   	 border-top-right-radius: 20px;
	  }
	  .tabla {
		font-size: 40px;
	  }
	  table,td {
			padding: 20px;
			border-radius: 8px;
			}
	  .valor {
			font-weight: bold;
			text-align: center;
			font-size: 600%;
			color: darkred;
	  }
  	  .sub {
		font-size: 50%;
		color: #888888;
	  }
	  .ON {
		font-weight: bold;
		text-align: center;
		font-size: 600%;
		color: darkred;
	  }
	  .OFF {
		font-weight: bold;
		text-align: center;
		font-size: 600%;
		color: darkgreen;
	  }
	
	.cabeceraTabla {
		background-color: darkblue;
		color:white;
		
	}
	a {
		padding-right:10px;
		padding-left: 10px;
		text-decoration: none;
		color: darkblue;
	}
	.cabItem {
		padding-top: 0.5em;
		padding-left: 1em;
		background-color: #92bbca;
		border-top-left-radius: 10px;
   	border-top-right-radius: 10px;
		
	}
	.cuandoTemperatura {
		text-align: center;
    		margin-bottom: 0.5em;
		font-size: 200%;
		border-top: solid 1px #AAAAAA;
	}
	.sitioTemperatura {
		font-size: 200%;
	}
	.accion {
		text-align: center;
		font-size: 200%;
		background-color: #EEEEEE;
		padding: 0.5em;
	}
</style>


<?php
	/* error_reporting(E_ALL);
	ini_set("display_errors", 1); */
	require ("comun.php");
	conectar();
	$horas=$_GET["horas"];
	$dias=$_GET["dias"];
	if(!$horas && !$dias) {
		$horas=24;
	}
?>



    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      
	  
	  google.charts.load('current', {'packages':['corechart']});
	  
      google.charts.setOnLoadCallback(drawStuff);

      function drawStuff() {
	  
	  
        var options = {
          width: 380,
          legend: { position: 'none' },
          lineWidth: 10,
		  curveType: 'function',
          axes: {
            x: {
              0: { side: 'bottom'} // Top x-axis.
            }
          },
		  vAxis: {
			ticks: [0,20,40,60,80,100]					
		 }
	
        };
	
	<?php
	$ids=array();
	$ids[]='1235';
	
	echo "\tvar ids=[\n";
	foreach($ids as $id) {
		echo "$id,\n";
	}
	echo "\t-1];\n";
	
	echo "\tvar data = [\n";
	$coma0="";
	foreach($ids as $id) {
		echo "\n\t${coma0}new google.visualization.arrayToDataTable([";
		echo "\n\t\t['Hora', 'Temperatura'],";

		$sql = <<<EOT
		  select date_format(hora, '%H') as shora, avg(valor) 
		  from lectura 
		  where idsensor=$id
			 and valor > 0
			 and current_timestamp() < addtime(hora, '0 ${horas}:0:0.0')
			 group by date_format(hora, '%m-%d %H') 
EOT;

		$result=mysql_query($sql);
		$coma="";
		$alguno=false;
		while ($row = mysql_fetch_row($result)) { 
			$hora=$row[0];
			$valor=$row[1];
			echo "\n\t\t${coma}[\"${hora}\", ${valor}]";
			$coma=",";
			$alguno=true;
		}
		if($alguno==false) {
			echo "\n\t\t${coma}[\"0\", 20]";
		}		
		echo "])\n";
		$coma0=",";

	}
	echo "];\n";	
	?>
	
		for(i=0; ids[i]!=-1; i++) {
			var chart = new google.visualization.LineChart(document.getElementById("grafico" + ids[i]));
			chart.draw(data[i],options);
		 }
	 
	 }
    </script>

</head>

<body style='font-family=Arial; font-size: 150%;'>





<div border="0" style="border: solid 1px;" class="tabla">

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
	WHERE sensor.id='1235'
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
	<div class="item">
		<div class="cabItem">
			<span ><a class="sitioTemperatura" href="querysensor.php?id=$row[4]">$row[0]</a></span>
		</div>
		<div class="valor">
		  <span id="grafico$row[4]" style="width: 360px; height: 500px; float:left;"></span>
		  <span>
				<a href="graficosensor.php?id=$row[4]">
					${row[1]}${row[2]}
				</a>
			</span>
		</div>
		<div class="cuandoTemperatura" >$hora</div>
	</div>
EOT;
}


?>
</div>


<div border="0" style="border: solid 1px;" class="tabla">

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
	<div class="item">
		<div class="cabItem">
		$row[1]
		</div>
		<div class="$row[2]">$row[2]</div>
		<div>
			<div><span class="sub">desde</span> $hora</div>
			<div class="sub">(consultado $consultado)</div>
		<div>
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
		<a href="set.php?actuador=$row[0]&estado=${accion}">
			<div class="accion">
				${texto}
				
			</div>	
		</a>
EOT;
}
	echo <<<EOT
	</div>
EOT;
}


?>
</table>

</body>
</html>
