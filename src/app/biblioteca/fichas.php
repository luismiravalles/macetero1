<?php

header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");

$paramAutor=$_GET["autor"];

?>
<html>

<head>
	
	<?php include('head.php'); ?>


</head>

<body >
	
	<div class="container">
		<?php include('cabecera.php'); ?>
	
	
		<div class="form-group">
				<a class="btn btn-primary col-md-2" href="nuevo.php">Nuevo</a>
				<a class="btn btn-primary col-md-2" href="autores.php">Autores</a>
				<a class="btn btn-primary col-md-2" href="imprimir.php">Imprimir</a>
		</div>


		<div id="fichas" class="stripe">
		
			<div></div>

		<?php

			require ("comun.php");
			ini_set("display_errors", 1);
			conectar();

			$sql = "select id,autor,titulo,genero,valoracion from libro";
			if( $paramAutor != null) {
				$sql = $sql . " where autor ='$paramAutor'";
			}
			$sql = $sql . " order by autor, titulo";
			$result=mysql_query($sql);			
			while ($row = mysql_fetch_row($result)) { 
				$urlAutor=urlencode($row[1]);
				$urlTitulo=urlencode($row[2]);
				echo "<a href='editar.php?id=$row[0]'><div class='ficha ficha${row[4]}'>";
				echo "<div class='titulo'>$row[2]</div>";
				echo "<div class='autor'>$row[1]</div>";
				
				echo "<div class='genero$row[3]'>$row[3]</div>";
				echo "<div class='valor valor${row[4]}'>";
				for($x=0; $x<$row[4]; $x++) {
						echo "☆";
				}
				echo "</div>";

				echo "</div></a>";
			}
		?>
		</fichas>
	</div>


	<script src="js/bootstrap.min.js"></script>
</body>
</html>