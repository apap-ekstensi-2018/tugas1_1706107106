package com.example.tugas1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.tugas1.model.UniversitasModel;
import java.util.List;

@Mapper
public interface UniversitasMapper {
	@Select("select id, kode_univ, nama_univ from universitas where id = #{id_univ}")
	UniversitasModel selectUniversitas(@Param("id_univ") int id_univ);
	
	@Select("select id, kode_univ, nama_univ from universitas")
	List<UniversitasModel> selectAllUniversitas();
}
