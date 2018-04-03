package com.example.tugas1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tugas1.model.UniversitasModel;
import com.example.tugas1.dao.UniversitasMapper;

@Service
public class UniversitasServiceDatabase implements UniversitasService {
	@Autowired
	private UniversitasMapper universitasMapper;
	
	public UniversitasServiceDatabase() {}
	
	public UniversitasServiceDatabase(UniversitasMapper universitasMapper) {
		this.universitasMapper = universitasMapper;
	}
	
	@Override
	public UniversitasModel selectUniversitas(int id_univ) {
		return universitasMapper.selectUniversitas(id_univ);
	}

	@Override
	public List<UniversitasModel> selectAllUniversitas() {
		return universitasMapper.selectAllUniversitas();
	}

}
