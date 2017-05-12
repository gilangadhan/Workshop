<?php
require_once('koneksi.php');
if(isset($_POST['id_w'])){
	   $id_w = $_POST['id_w'];
	 
	try{
	  $sql = "DELETE FROM `workshop` WHERE `workshop`.`id_workshop` = '".$id_w."'";
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
