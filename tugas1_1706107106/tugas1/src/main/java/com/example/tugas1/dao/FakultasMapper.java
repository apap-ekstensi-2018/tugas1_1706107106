package com.example.tugas1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.tugas1.model.FakultasModel;
import java.util.List;

@Mapper
public interface FakultasMapper {
	@Select("select id, kode_fakultas, nama_fakultas, id_univ from fakultas where id = #{id_fakultas}")
	FakultasModel selectFakultas(@Param("id_fakultas") int id_fakultas);
	
	@Select("select id, kode_fakultas, nama_fakultas, id_univ from fakultas where id_univ = #{id_univ}")
	List<FakultasModel> selectFakultasByUnivId(@Param("id_univ") int id_univ);
	
	@Select("select id, kode_fakultas, nama_fakultas, id_univ from fakultas")
	List<FakultasModel> selectAllFakultas();
}
