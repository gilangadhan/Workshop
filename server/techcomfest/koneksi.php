<?php
$hostname = 'localhost';
$username ='root';
$password = '';
$dbh = new PDO("mysql:host=$hostname;
dbname=techcomfest", $username, $password);

$dbh->setAttribute(PDO::ATTR_ERRMODE,
PDO::ERRMODE_EXCEPTION); 
//echo 'connected';
?>