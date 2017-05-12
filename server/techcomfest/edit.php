<?php
require_once('koneksi.php');
if(isset($_POST['nama_w']) &&     
   isset($_POST['id_w']) &&     
   isset($_POST['ket_w']) && 
   isset($_POST['tempat_w'])){
	   $id_w = $_POST['id_w'];
	   $nama_w = $_POST['nama_w'];
	   $tempat_w = $_POST['tempat_w'];
	   $tanggal_w = date('Y-m-d');
	   $ket_w = $_POST['ket_w'];
	try{
	  $sql = "UPDATE `workshop` SET 
	  `nama_workshop` = '".$nama_w."', 
	  `tempat_workshop` = '".$tempat_w."', 
	  `ket_workshop` = '".$ket_w."', 
	  `tanggal_workshop` = '".$tanggal_w."' 
	  WHERE `workshop`.`id_workshop` = '".$id_w."'";
	  $ss = $dbh->prepare($sql);
	  $ss->execute();
	  $size=$ss->rowCount();
	  if($ss){
			$json['message']='Data sukses';
			$json['success']=true;
			echo json_encode($json);
	  }else{
			$json['message']='Data gagal';
			$json['success']= false;
			echo json_encode($json);
	  }
	}catch(PDOExeption $e){
		$json['message']=$e->getMessage();
		$json['success']= false;		
		echo json_encode($json);
	}
}else{
     	$json['message']='Input Salah';
		$json['success']= false;
		
		echo json_encode($json);
}
?>
