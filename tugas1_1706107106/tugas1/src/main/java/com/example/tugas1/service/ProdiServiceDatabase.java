package com.example.tugas1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tugas1.model.ProdiModel;
import com.example.tugas1.dao.ProdiMapper;

@Service
public class ProdiServiceDatabase implements ProdiService {
	@Autowired
	private ProdiMapper prodiMapper;
	
	@Override
	public ProdiModel selectProdi(int id_prodi) {
		return prodiMapper.selectProdi(id_prodi);
	}
	
	@Override
	public List<ProdiModel>selectProdiByFakultasId(int id_fakultas) {
		return prodiMapper.selectProdiByFakultasId(id_fakultas);
	}

	@Override
	public List<ProdiModel> selectAllProdi() {
		return prodiMapper.selectAllProdi();
	}

}
