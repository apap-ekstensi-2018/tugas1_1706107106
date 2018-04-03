package com.example.tugas1.service;

import java.util.List;
import com.example.tugas1.model.UniversitasModel;

public interface UniversitasService {
	UniversitasModel selectUniversitas(int id_univ);
	List<UniversitasModel> selectAllUniversitas();
}
