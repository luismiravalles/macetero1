<?php

header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");
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
			<a class="btn btn-primary col-md-2" href="libros.php">Libros</a>
		</div>


		<table id="tabla" class="stripe">
			<thead>
				<tr role='row'>		
					<th class="sorting_asc">Autor</th>
					<th class="sorting">Leídos</th>
					<th class="sorting">Valoración</th>
				</tr>
			</thead>

		<?php

			require ("comun.php");
			ini_set("display_errors", 1);
			conectar();

			$sql = "select autor,count(valoracion), avg(valoracion) from libro group by autor";
			$result=mysql_query($sql);			
			while ($row = mysql_fetch_row($result)) { 
				$urlAutor=urlencode($row[0]);
				echo "<tr>";
				echo "<td><a href='libros.php?autor=$urlAutor'>$row[0]</a></td>";
				echo "<td>$row[1]</td>";
				echo "<td class='valor valor${row[2]}'>";
					for($x=0; $x<$row[2]; $x++) {
							echo "☆";
					}
					echo "</td>";
				echo "</tr>";

			}
		?>
		</table>
	</div>


	<script src="js/bootstrap.min.js"></script>
</body>
</html>