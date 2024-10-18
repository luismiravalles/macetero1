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
		<h2>Editar Libro</h2>
		
		<?php
		
			$autor="";
			$titulo="";
			$valoracion="";
			$genero="";
			$id=0;
			$comentarios="";
			$isbn="";
			$url="";
		
			if(array_key_exists('id', $_GET)) {
				$id=$_GET["id"];
				$sql="select id, autor, titulo, valoracion, genero, comentarios, isbn, url, fecha from libro where id=${id}";
				$result=mysql_query($sql);
				$row = mysql_fetch_row($result);
				$autor=$row[1];
				$titulo=$row[2];
				$valoracion=$row[3];				
				$genero=$row[4];
				$comentarios=$row[5];
				$isbn=$row[6];
				$url=$row[7];
				$fecha=$row[8];
			}
		
			if($_POST) {
				$id=$_POST["id"];
				$autor=$_POST["autor"];
				$titulo=$_POST["titulo"];
				$valoracion=$_POST["valoracion"];
				$genero=$_POST["genero"];
				$comentarios=$_POST["comentarios"];
				$isbn=$_POST["isbn"];
				$url=$_POST["url"];
				$sql="update libro set autor='$autor', titulo='$titulo', valoracion=$valoracion
								,genero='$genero'
								,comentarios='$comentarios'
								,isbn='$isbn'
								,url='$url'
						where id=$id";				 
				mysql_query($sql);
				echo "<h3>Se ha actualizado el libro</h3>";
			}
			
		?>
	

			<div class="form-group">
				<a class="btn btn-primary" href="libros.php">Volver a la lista</a>
			</div>
	
	
			<form action="editar.php" method="POST" class="form-horizontal">
			
				<?php include('form.php'); ?>
			
				<div class="form-group">
					<div class="col-md-2">
						<button type="submit" name="button" class="btn btn-primary" >Guardar</button>
					</div>
					<div class="col-md-2">
						
						<input type="button"  class="btn btn-primary" onclick="location.href='borrar.php?id=<?php echo $id; ?>'"
							value="Borrar"/>
					</div>
				</div>


				
			</form>
	</div>
	
	
</body>