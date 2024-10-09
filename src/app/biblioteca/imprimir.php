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
						paging: false,
						searching: false,
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
				});
	});			
	</script>	

</head>

<body >
	
	<div class="container">
		<?php include('cabecera.php'); ?>
	

		<table id="tabla" class="stripe">
			<thead>
				<tr role='row'>		
					<th class="sorting_asc">Autor</th>
					<th class="sorting">Título</th>
					<th class="sorting">Género</th>
					<th class="sorting">Valor</th>
				</tr>
			</thead>

		<?php

			require ("comun.php");
			ini_set("display_errors", 1);
			conectar();

			$sql = "select id,autor,titulo,genero,valoracion from libro";
			if( $paramAutor != null) {
				$sql = $sql . " where autor ='$paramAutor'";
			}
			$result=mysql_query($sql);			
			while ($row = mysql_fetch_row($result)) { 
				$urlAutor=urlencode($row[1]);
				$urlTitulo=urlencode($row[2]);
				echo "<tr>";
				echo "<td>$row[1]</td>";
				echo "<td>$row[2]</td>";
				echo "<td class='genero$row[3]'>$row[3]</td>";
				echo "<td class='valor valor${row[4]}'>";
				for($x=0; $x<$row[4]; $x++) {
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