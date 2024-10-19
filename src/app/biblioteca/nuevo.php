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
		<h2>Nuevo Libro</h2>
		
		<?php
			$autor=null;
			$titulo=null;
			$valoracion=null;
			$genero=null;
			$id=null;
			$comentarios=null;
			$isbn=null;
			$url=null;
		
			if($_POST) {
				$autor=$_POST["autor"];
				$titulo=$_POST["titulo"];
				$valoracion=$_POST["valoracion"];
				$genero=$_POST["genero"];
				$comentarios=$_POST["comentarios"];
				$isbn=$_POST["isbn"];
				$url=$_POST["url"];
				$sql="insert into libro (autor,titulo,valoracion,genero,comentarios, isbn, url) values 
					  ('$autor','$titulo','$valoracion','$genero','$comentarios', '$isbn', '$url')
					" ;
					 
				mysql_query($sql);
				if(mysql_affected_rows() < 1) {
					echo "<h3 style='color:red;'>Error guardando</h3>";
				} else {
					echo "<h3>Se ha insertado un nuevo libro</h3>";
				}
				$autor=null;
				$titulo=null;
				$valoracion=null;
				$genero=null;
				$comentarios=null;
				$id=null;
				$isbn=null;
				$url=null;
			}
			
		?>
	

			<form action="nuevo.php" method="POST" class="form-horizontal">
				<div class="form-group">
					<a class="btn btn-primary btn-danger" href="libros.php">Volver a la lista</a>

					<button type="submit" name="button" class="btn btn-primary btn-success" >Guardar</button>
				</div>

				<?php include('form.php'); ?>
				

				
			</form>
	</div>
	
	
</body>
