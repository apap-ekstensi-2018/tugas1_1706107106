package com.example.tugas1.service;

import java.util.List;
import com.example.tugas1.model.FakultasModel;

public interface FakultasService {
	FakultasModel selectFakultas(int id_fakultas);
	List<FakultasModel> selectFakultasByUnivId(int id_univ);
	List<FakultasModel> selectAllFakultas();
}
