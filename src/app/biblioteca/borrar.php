<?php

header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");

require ("comun.php");
ini_set("display_errors", 1);
conectar();
?>
<html>

<head>
	
	<?php include('head.php'); ?>	

</head>

<body >
	<div class="container">
		<?php include('cabecera.php'); ?>
		<h2>Borrar Libro</h2>
		
		<?php
			if(array_key_exists('id', $_GET)) {
				$id=$_GET["id"];
				$sql="delete from libro where id=$id";
				mysql_query($sql);
				echo "<h3>Se ha borrado el libro con id $id</h3";				
			}
		?>
		
		<div class="container">
				<div class="row">
					<a href="libros.php">Volver a la lista</a>
				</div>
		
		</div>
	</div>
</body>