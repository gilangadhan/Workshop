<?php
include 'koneksi.php';
if(isset($_POST['jurusan_mahasiswa'])){ 
		$jurusan_mahasiswa = $_POST['jurusan_mahasiswa'];
		$sql = mysql_query("select * from master_mahasiswa where jurusan_mahasiswa = '".$jurusan_mahasiswa."'");
		$cek = mysql_num_rows($sql);
		$response = array();
		if ($cek > 0){
			$response["mahasiswa"] = array();
			while ($row = mysql_fetch_array($sql)){
				$data = array();
				$data["nim"] = $row["nim_mahasiswa"];
				$data["nama"] = $row["nama_mahasiswa"];
				$data["jurusan"] = $row["jurusan_mahasiswa"];
				$data["prodi"] = $row["prodi_mahasiswa"];
				$data["kelas"] = $row["kelas_mahasiswa"];
				$data["alamat"] = $row["alamat_mahasiswa"];
				array_push($response["mahasiswa"], $data);
				}
			$response["success"] = true;
			$response["message"] = "Data berhasil diambil";
			echo json_encode($response);
		}else{
			$response["success"] = false;
			$response["message"] = "Data tidak berhasil diambil";
			echo json_encode($response);
		}
	}else{
			$response["success"] = false;
			$response["message"] = "data tidak ada";
			echo json_encode($response);
}