<?php

header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");

$paramAutor=$_GET["autor"];

?>
<html>

<head>
	
	<?php include('head.php'); ?>

	<script>
	$(document).ready(function(){
					$('#tabla').DataTable({
						paging: true,
						pageLength: 50,
						language: {
							search: "Filtrar",
							info:   "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
							lengthMenu:     "Mostrar _MENU_ registros",
							paginate: {
								first:    "Primero",
								last:     "Último",
								next:     "Siguiente",
								previous: "Anterior"
							}
						},
						buttons: [ 'excel','pdf' ]
						
	
	
				});
	});			
	</script>	

</head>

<body >
	
	<div class="container">
		<?php include('cabecera.php'); ?>
	
	
		<div class="form-group">
				<a class="btn btn-primary col-md-2" href="nuevo.php">Nuevo</a>
				<a class="btn btn-primary col-md-2" href="autores.php">Autores</a>
				<a class="btn btn-primary col-md-2" href="imprimir.php">Imprimir</a>
		</div>


		<table id="tabla" class="stripe">
			<thead>
				<tr role='row'>
					<th class="sorting_asc">Autor</th>
					<th class="sorting">Título</th>
					<th class="sorting">Género</th>
					<th class="sorting">Valor</th>
					<th class="sorting">Fecha</th>
					<th class=""></th>
				</tr>
			</thead>

		<?php

			require ("comun.php");
			ini_set("display_errors", 1);
			conectar();

			$sql = "select id,autor,titulo,genero,valoracion,isbn,url,fecha from libro";
			if( $paramAutor != null) {
				$sql = $sql . " where autor ='$paramAutor'";
			}
			$result=mysql_query($sql);			
			while ($row = mysql_fetch_row($result)) { 
				$urlAutor=urlencode($row[1]);
				$urlTitulo=urlencode($row[2]);
				$isbn=$row[5];
				$img=null;
				$url=$row[6];
				$f=new DateTime($row[7]);
				$fecha=$f->format("Y-m-d");
				$enlace="https://www.entrelectores.com/busqueda?term=$urlTitulo";
				if($url!=null && $url!="") {
					$enlace=$url;
				}
				echo "<tr>";
				if($url!=null && $url!="") {
					$img = "
						<a href='$url'>
							<span class='glyphicon glyphicon-cloud'></span>
						</a>";
				}
				echo "<td><a href='https://www.entrelectores.com/busqueda?term=$urlAutor'>$row[1]</a></td>";
				echo "<td>
						$img
						<a href='$enlace'>$row[2]
						</a>
					</td>";
				echo "<td class='genero$row[3]'>$row[3]</td>";
				echo "<td class='valor valor{$row[4]}'>";
				echo "$row[4]"."☆";
				echo "</td>";
				echo "<td>$fecha</td>";
				echo "<td>
						<a href='editar.php?id=$row[0]'>Editar</a>
					 </td>";
				echo "</tr>";
			}
		?>
		</table>
	</div>


	<script src="js/bootstrap.min.js"></script>
</body>
</html>