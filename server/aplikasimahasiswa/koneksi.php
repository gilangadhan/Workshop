<?php 
$server = "localhost";
$user = "root";
$password = "";
$database = "mahasiswa";

mysql_connect($server, $user, $password) or die ("Koneksi Putus");
mysql_select_db($database) or die ("Database tidak bisa dibuka");

?>