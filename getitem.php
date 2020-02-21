<?php
header("Content-Type: text/html; charset=UTF-8");
  
$db_host="localhost";
$db_user="yijy001";
$db_pw="joon001";
$db_name="products";

$item_num = $_REQUEST["item_num"];

//php버전이 낮아서 따로 디코딩 스타일 지정못함 그래서 따로 함수로 지정해줌 (php업데이트하면 phpMyAdmin접속이 느려져 모든 속도가 느려져서 X)
function han ($s) { return reset(json_decode('{"s":"'.$s.'"}')); }
function to_han ($str) { return preg_replace('/(\\\u[a-f0-9]+)+/e','han("$0")',$str); }

$conn = mysqli_connect($db_host, $db_user, $db_pw, $db_name);
mysqli_query($conn, "set session character_set_connection=utf8;");
mysqli_query($conn, "set session character_set_results=utf8;");
mysqli_query($conn, "set session character_set_client=utf8;");

$sql = "SELECT * FROM items WHERE NUMBER='$item_num'";
//$sql = "SELECT NUMBER, ENGNAME, KORNAME, INFO, IMAGE, PRICE FROM items WHERE NUMBER='$item_num'";

mysqli_set_charset($conn, "utf8");
$result = mysqli_query($conn, $sql);

if($result)
{
	$res_row = array();
	
	while($row = mysqli_fetch_array($result))
	{
		array_push($res_row, array('number'=>$row[0], 'engname'=>$row[1], 
		'korname'=>$row[2], 'info'=>$row[3], 'image'=>$row[4], 'price'=>$row[5]));
	}
	
	//따로 디코딩이 안되기 때문에 to_han함수로 디코딩해줘야함
	echo to_han(json_encode(array("item"=>$res_row)));
}
else
{
	echo "conERROR";
}
mysqli_close($conn);
?>