package com.example.tugas1.service;

import com.example.tugas1.model.MahasiswaModel;
import com.example.tugas1.dao.MahasiswaMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MahasiswaServiceDatabase implements MahasiswaService {
	@Autowired
	private MahasiswaMapper mahasiswaMapper;
	
	public MahasiswaServiceDatabase() { }
	
	public MahasiswaServiceDatabase(MahasiswaMapper mahasiswaMapper) {
		this.mahasiswaMapper = mahasiswaMapper;
	}
	
	@Override
	public MahasiswaModel selectMahasiswa(String npm) {
		log.info ("select mahasiswa with npm {}", npm);
		return mahasiswaMapper.selectMahasiswa(npm);
	}
	
	@Override
	public List<MahasiswaModel> selectMahasiswaByProdi(int id_prodi) {
		log.info ("select mahasiswa with id_prodi {}", id_prodi);
		return mahasiswaMapper.selectMahasiswaByProdi(id_prodi);
	}
	
	@Override
	public String selectNPMMax(String parameter) {
		log.info ("select mahasiswa with npm {}", parameter);
		return mahasiswaMapper.selectNPMMax(parameter);
	}
	
	@Override
	public String totalMahasiswaPerProdi(int id_prodi, String tahun_masuk) {
		log.info ("counting total mahasiswa with id_prodi {} and tahun_masuk {}", id_prodi, tahun_masuk);
		return mahasiswaMapper.totalMahasiswaPerProdiPerYear(id_prodi, tahun_masuk);
	}
	
	@Override
	public String totalMahasiswaPerStatusPerProdi(int id_prodi, String status, String tahun_masuk) {
		log.info ("counting total mahasiswa with id_prodi {} and status {} and tahun_masuk {}", id_prodi, status, tahun_masuk);
		return mahasiswaMapper.totalMahasiswaPerStatusPerProdiPerYear(id_prodi, status, tahun_masuk);
	}

	@Override
	public boolean updateMahasiswa(MahasiswaModel mahasiswa) {
		log.info ("updating mahasiswa with npm {}, nama {}", mahasiswa.getNpm(), mahasiswa.getNama());
		return mahasiswaMapper.updateMahasiswa(mahasiswa);
	}

	@Override
	public boolean addMahasiswa(MahasiswaModel mahasiswa) {
		log.info ("add mahasiswa with npm {}, nama {}", mahasiswa.getNpm(), mahasiswa.getNama());
		return mahasiswaMapper.addMahasiswa(mahasiswa);
	}

}
