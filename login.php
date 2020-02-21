<?php
header("Content-Type: text/html; charset=UTF-8");
  
$db_host="localhost";
$db_user="yijy001";
$db_pw="joon001";
$db_name="testdb";

$id=$_REQUEST["id"];
$pw=$_REQUEST["pw"];

$conn= mysqli_connect($db_host, $db_user, $db_pw, $db_name);
mysqli_query($conn, "set session character_set_connection=utf8;");
mysqli_query($conn, "set session character_set_results=utf8;");
mysqli_query($conn, "set session character_set_client=utf8;");

$sql = "SELECT PW, NAME FROM test WHERE ID='$id'";

mysqli_set_charset($conn, "utf8");
$result = mysqli_query($conn, $sql);
// result of sql query
if($result)
{
  $row = mysqli_fetch_array($result);
  if(is_null($row[0]))
  {
    echo "00"; //Can not find ID
  }
  else
  {
	if($pw==$row[0])
	{
		echo "10-$row[1]";
	}
	else
	{
		echo "01"; // Can not find PW
	}
  }
}
else
{
 echo "conERROR";
}
mysqli_close($conn);
?>