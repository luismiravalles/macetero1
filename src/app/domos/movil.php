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
		background-color: #448;
		color:white;
		
	}
	a {
		padding-right:10px;
		padding-left: 10px;
		text-decoration: none;
		color: #448;
	}
	.cabItem {
		padding-top: 0.2em;
		padding-left: 0.2em;
		background-color: #92bbca;
		
	}
	.cuandoTemperatura {
		text-align: center;
    		margin-bottom: 0.5em;
		font-size: 120%;
		border-top: solid 1px #AAAAAA;
	}
	.sitioTemperatura {
		font-size: 120%;
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
	  
		var options = [
			{
			  // Tipo 0 para Temperatura
			  animation: {
					startup:true,
					duration: 1000
			  },
			  width: 440,
			  height: 380,
			  legend: { position: 'none' },
			  lineWidth: 10,
			  curveType: 'function',
			  chartArea: {left:20,top:20,width:'90%',height:'80%'},	
			  colors:['#448'],	  
			  axes: {
				x: {
				  0: { side: 'bottom'} // Top x-axis.
				}
			  },
			  vAxis: {
				ticks: [10,15,20,25]					
			 }
			},
			{
			  // Tipo 1 para Humedad
			  animation: {
					startup:true,
					duration: 1000
			  },			  
			  width: 380,
			  height: 380,
			  legend: { position: 'none' },
			  lineWidth: 10,
			  curveType: 'function',
			  chartArea: {left:20,top:20,width:'90%',height:'80%'},
			  colors:['#800'],			  
			  axes: {
				x: {
				  0: { side: 'bottom'} // Top x-axis.
				}
			  },
			  vAxis: {
				ticks: [40,60,80,100]					
			 }
			}			
		];
	
	<?php
	$sql = "SELECT sensor.id, sensor.tipo FROM sensor WHERE sensor.activo=1";
	$result=mysql_query($sql);
	if(!$result) {
		$error=mysql_error();
		echo "<tr><td>Result es $result , $error </td></tr>";
	}
	$ids=array();
	$tipos=array();
	while ($row = mysql_fetch_row($result)) { 
		$ids[]=$row[0];
		$tipos[]=$row[1];
	}
	
	echo "\tvar ids=[\n";
	foreach($ids as $id) {
		echo "$id,\n";
	}
	echo "\t-1];\n";
	
	echo "\tvar tipos=[\n";
	foreach($tipos as $tipo) {
		echo "$tipo,\n";
	}
	echo "\t-1];\n";	
	
	echo "\tvar data = [\n";
	$coma0="";
	foreach($ids as $id) {
		echo "\n\t${coma0}new google.visualization.arrayToDataTable([";
		echo "\n\t\t['Hora', 'Temperatura'],";

		$sql = <<<EOT
		  select date_format(l.hora, '%m-%d %H') as shora, avg(l.valor + s.suma) 
		  from lectura l
		  left join sensor s on s.id=l.idsensor
		  where l.idsensor=$id
			 and l.valor > 0
			 and current_timestamp() < addtime(l.hora, '0 ${horas}:0:0.0')
			 group by date_format(l.hora, '%m-%d %H') 
EOT;

		$result=mysql_query($sql);

		if (!$result) {
			echo 'Could not run query: ' . mysql_error();
		}

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
			chart.draw(data[i],options[tipos[i]]);
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
	SELECT nombre,  valor+suma, medida, hora, sensor.id, foto.foto
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
	<div class="item">
		<div class="cabItem">
			<span ><a class="sitioTemperatura" href="querysensor.php?id=$row[4]">$row[0]</a></span>
		</div>
		<div class="valor">
		  <span id="grafico$row[4]" style="width: 380px; height: 380px; float:left;"></span>
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
SELECT id, nombre,  estado, hora, consultado, estadoSolicitado, horaSolicitado from actuador where activo = 1
EOT;

$result=mysql_query($sql);
if(!$result) {
	$error=mysql_error();
	echo "<tr><td>Result es $result , $error </td></tr>";
}


while ($row = mysql_fetch_row($result)) { 
	$hora=formatFecha($row[3]);
	$consultado=formatFecha($row[4]);
	$estadoSolicitado=$row[5];
	$horaSolicitado=formatFecha($row[6]);
	echo <<<EOT
	<div class="item">
		<div class="cabItem">
		$row[1]
		</div>
		<div class="$row[2]">$row[2]</div>
		<div>
				<span class="sub">desde</span>
				<span>$hora</span>
				<span class="sub">(consultado $consultado)</span>
		<div>
EOT;
	if(!is_null($estadoSolicitado) && strcmp($row[2], $estadoSolicitado)) {
		if($estadoSolicitado = "ON") {
			$verbo=" encenderá ";
		} else {
			$verbo=" apagará ";
		}
		echo "<span style='font-size:140%;'> Se ${verbo} $horaSolicitado</span>";
	}
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
	echo <<<EOT
		<a href="set.php?actuador=$row[0]&estado=${accion}&horas=2">
			<div class="accion">
				${texto} en 2 horas
				
			</div>	
		</a>
EOT;
	echo <<<EOT
		<a href="set.php?actuador=$row[0]&estado=${accion}&horas=4">
			<div class="accion">
				${texto} en 4 horas
				
			</div>	
		</a>
EOT;
	echo <<<EOT
		<a href="set.php?actuador=$row[0]&estado=${accion}&horas=6">
			<div class="accion">
				${texto} en 6 horas
				
			</div>	
		</a>
EOT;
	echo <<<EOT
		<a href="set.php?actuador=$row[0]&estado=${accion}&horas=8">
			<div class="accion">
				${texto} en 8 horas
				
			</div>	
		</a>
EOT;
	echo <<<EOT
		<a href="set.php?actuador=$row[0]&estado=${accion}&horas=10">
			<div class="accion">
				${texto} en 10 horas
				
			</div>	
		</a>
EOT;
	echo <<<EOT
		<a href="set.php?actuador=$row[0]&estado=${accion}&horas=12">
			<div class="accion">
				${texto} en 12 horas
				
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
