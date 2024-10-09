<html>


<body>


<h1>SALUDOS</h1>

<?

if( ! ($_GET["saludo"] === null) ) {
	echo "Has establecido el saludo";
	$GLOBALS["saludo"]=$_GET["saludo"];
} else {
	echo "Hola mundo!!!";
	echo "Saludos " . $GLOBALS["saludo"];
}

?>

</body>

</html>