package com.example.tugas1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.tugas1.model.ProdiModel;
import java.util.List;

@Mapper
public interface ProdiMapper {
	@Select("select id, kode_prodi, nama_prodi, id_fakultas from program_studi where id = #{id_prodi}")
	ProdiModel selectProdi(@Param("id_prodi") int id_prodi);
	
	@Select("select id, kode_prodi, nama_prodi, id_fakultas from program_studi where id_fakultas = #{id_fakultas}")
	List<ProdiModel> selectProdiByFakultasId(@Param("id_fakultas") int id_fakultas);
	
	@Select("select id, kode_prodi, nama_prodi, id_fakultas from program_studi")
	List<ProdiModel> selectAllProdi();
}
