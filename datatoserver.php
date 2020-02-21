<?php
header("Content-Type: text/html; charset=UTF-8");

$db_host="localhost";
$db_user="yijy001";
$db_pw="joon001";
$db_name="testdb";

$id=$_REQUEST["id"];
$pw=$_REQUEST["pw"];
$name=$_REQUEST["name"];

echo $id;
echo $pw;
echo $name;

$conn = mysqli_connect($db_host, $db_user, $db_pw, $db_name);
mysqli_query($conn, "set session character_set_connection=utf8;");
mysqli_query($conn, "set session character_set_results=utf8;");
mysqli_query($conn, "set session character_set_client=utf8;");
$query = "INSERT INTO test (id, pw, name) VALUES ('$id', '$pw', '$name');";
$result = mysqli_query($conn, $query);

if($result)
	echo " |Result: OK";
else
	echo " |Result: Error";

mysqli_close($conn);
?>