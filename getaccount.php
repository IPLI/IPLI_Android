<?php
header("Content-Type: text/html; charset=EUC-KR");

$db_host="localhost";
$db_user="yijy001";
$db_pw="joon001";
$db_name="testdb";

$conn = mysqli_connect($db_host, $db_user, $db_pw, $db_name);
mysqli_query($conn, "set session character_set_connection=euckr;");
mysqli_query($conn, "set session character_set_results=euckr;");
mysqli_query($conn, "set session character_set_client=euckr;");

$query = "SELECT * FROM test";
$result = mysqli_query($conn, $query);
while($row = mysqli_fetch_array($result))
{
	$data = "$row[0],$row[1],$row[2]";
	echo $data;
	echo "<br>";
}
mysqli_close($conn);
?>