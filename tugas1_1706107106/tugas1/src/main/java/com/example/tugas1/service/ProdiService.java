package com.example.tugas1.service;

import java.util.List;
import com.example.tugas1.model.ProdiModel;

public interface ProdiService {
	ProdiModel selectProdi(int id_prodi);
	List<ProdiModel>selectProdiByFakultasId(int id_fakultas);
	List<ProdiModel> selectAllProdi();
}
