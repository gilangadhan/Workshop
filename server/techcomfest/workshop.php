<?php
require_once('koneksi.php');
try{
  $sql = "select * from workshop";
  $ss = $dbh->prepare($sql);
  $ss->execute();
  
  $data = $ss->fetchAll(PDO::FETCH_OBJ);
  $size=$ss->rowCount();
  if($size > 0){
		$json['message']='Data sukses';
		$json['success']=true;
		$json['workshop']=$data;
		
		echo json_encode($json);
  }else{
     	$json['message']='Data gagal';
		$json['success']= false;
		
		echo json_encode($json);
  }
}catch(PDOExeption $e){
	$json['message']=$e->getMessage();
	$json['success']= false;		echo json_encode($json);
}
?>
