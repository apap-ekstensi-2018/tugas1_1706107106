package com.example.tugas1.service;

import java.util.List;
import com.example.tugas1.model.MahasiswaModel;

public interface MahasiswaService {
	MahasiswaModel selectMahasiswa(String npm);
	List<MahasiswaModel> selectMahasiswaByProdi(int id_prodi);
	String selectNPMMax(String parameter);
	String totalMahasiswaPerProdi(int id_prodi, String tahun_masuk);
	String totalMahasiswaPerStatusPerProdi(int id_prodi, String status, String tahun_masuk);
	boolean updateMahasiswa(MahasiswaModel mahasiswa);
	boolean addMahasiswa(MahasiswaModel mahasiswa);
}
