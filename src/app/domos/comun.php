<?php
function conectar() {

	date_default_timezone_set("Europe/Madrid");

	$link = mysql_connect("db:3306",
						  "root", getenv('BDPASS'));
	if( ! $link ) {
		echo "No se ha podido conectar a la base de datos.<br>";
	}
	if(!mysql_select_db("php", $link)) {
		echo "No se ha podido seleccionar la base de datos php<br>";
	}
	
	mysql_query("SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'", 
		$link);
	
	return $link;
}

function formatFecha($fecha) {
if(is_null($fecha)) {
	return null;
}
$l = strtotime($fecha);
if ($l >= strtotime("tomorrow")) {
        return date("\M\a\ñ\a\N\a H:i", $l);
}
if ($l >= strtotime("today"))
        return date("\H\o\y H:i", $l);
    else if ($l >= strtotime("yesterday"))
        return date("\A\y\\e\\r H:i", $l);
else
	return date("d/M H:i", $l);
}
?>
