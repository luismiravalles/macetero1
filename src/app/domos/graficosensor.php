<html>
<head>
	<style>
		  body {
				font-family: Arial;
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

<?php
	/* error_reporting(E_ALL);
	ini_set("display_errors", 1); */
	require ("comun.php");
	conectar();
	$id=$_GET["id"];
	$horas=$_GET["horas"];
	$dias=$_GET["dias"];
	if(!$horas && !$dias) {
		$horas=24;
	}
?>



    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1.1", {packages:["bar"]});
      google.setOnLoadCallback(drawStuff);

      function drawStuff() {
        var data = new google.visualization.arrayToDataTable([
          ['Hora', 'Temperatura'],
		  <?php
			if(!$horas) {
				$horas=24*$dias;
				$sql = <<<EOT
				  select date_format(hora, '%m-%d') as shora, avg(valor) 
				  from lectura 
				  where idsensor=$id
					 and current_timestamp() < addtime(hora, '0 ${horas}:0:0.0')
					 group by date_format(hora, '%m-%d') 
EOT;
			
			} else {
				$sql = <<<EOT
				  select date_format(hora, '%H') as shora, avg(valor) 
				  from lectura 
				  where idsensor=$id
					 and current_timestamp() < addtime(hora, '0 ${horas}:0:0.0')
					 group by date_format(hora, '%m-%d %H') 
EOT;

			}
			$result=mysql_query($sql);		  
			  while ($row = mysql_fetch_row($result)) { 
				$hora=$row[0];
				$valor=$row[1];
				echo "[\"${hora}\", ${valor}],";
			  }
		  ?>
        ]);

        var options = {
          title: 'Temperatura',
          width: 900,
          legend: { position: 'none' },
          chart: { subtitle: 'Temperatura' },
          axes: {
            x: {
              0: { side: 'top', label: 'Horas'} // Top x-axis.
            }
          },
		  vAxis: { 
					ticks: [0,1,2,3,4,5,10,15,20,25,30,35],
					maxValue: 35
					},
	
          bar: { groupWidth: "90%" }
        };

        var chart = new google.charts.Bar(document.getElementById('top_x_div'));
        // Convert the Classic options to Material options.
        chart.draw(data, google.charts.Bar.convertOptions(options));
      };
    </script>

</head>

<body style='font-family=Arial; font-size: 150%;'>
	<div id="top_x_div" style="width: 900px; height: 500px;"></div>
	
	<ul>
	<li><a href="graficosensor.php?id=<?= $id ?>&horas=148">Ultima semana</a></li>
	<li><a href="graficosensor.php?id=<?= $id ?>&dias=30">Ultimos 30 días</a></li>
	<li><a href="graficosensor.php?id=<?= $id ?>&dias=90">Ultimos 90 días</a></li>
	</ul>

</body>
</html>