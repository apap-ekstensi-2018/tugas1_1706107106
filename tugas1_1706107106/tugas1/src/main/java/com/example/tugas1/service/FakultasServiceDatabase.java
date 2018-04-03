package com.example.tugas1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.dao.FakultasMapper;

@Service
public class FakultasServiceDatabase implements FakultasService {
	@Autowired
	private FakultasMapper fakultasMapper;
	
	public FakultasServiceDatabase() {}
	
	public FakultasServiceDatabase(FakultasMapper fakultasMapper) {
		this.fakultasMapper = fakultasMapper;
	}
	
	@Override
	public FakultasModel selectFakultas(int id_fakultas) {
		return fakultasMapper.selectFakultas(id_fakultas);
	}
	
	@Override
	public List<FakultasModel> selectFakultasByUnivId(int id_univ) {
		return fakultasMapper.selectFakultasByUnivId(id_univ);
	}

	@Override
	public List<FakultasModel> selectAllFakultas() {
		return fakultasMapper.selectAllFakultas();
	}

}
