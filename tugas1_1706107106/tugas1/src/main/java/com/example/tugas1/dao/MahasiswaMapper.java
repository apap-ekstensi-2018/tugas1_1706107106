package com.example.tugas1.dao;

import org.apache.ibatis.annotations.Update;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.tugas1.model.MahasiswaModel;

@Mapper
public interface MahasiswaMapper {
	@Select("select id, npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi from mahasiswa where npm = #{npm}")
	MahasiswaModel selectMahasiswa(@Param("npm") String npm);
	
	@Select("select id, npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi from mahasiswa where id_prodi = #{id_prodi}")
	List<MahasiswaModel> selectMahasiswaByProdi(@Param("id_prodi") int id_prodi);
	
	@Select("select max(npm) from mahasiswa where npm like #{parameter}")
	String selectNPMMax(@Param("parameter") String parameter);
	
	@Select("select count(nama) from mahasiswa where id_prodi = #{id_prodi} and tahun_masuk = #{tahun_masuk}")
	String totalMahasiswaPerProdiPerYear(@Param("id_prodi") int id_prodi,@Param("tahun_masuk") String tahun_masuk);
	
	@Select("select count(nama) from mahasiswa where id_prodi = #{id_prodi} and status = #{status} and tahun_masuk = #{tahun_masuk}")
	String totalMahasiswaPerStatusPerProdiPerYear(@Param("id_prodi") int id_prodi, @Param("status") String status, @Param("tahun_masuk") String tahun_masuk);
	
	@Update("UPDATE mahasiswa set npm = #{npm}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, agama = #{agama}, golongan_darah = #{golongan_darah}, status = #{status}, tahun_masuk = #{tahun_masuk}, jalur_masuk = #{jalur_masuk}, id_prodi = #{id_prodi} where id = #{id}")
	boolean updateMahasiswa(MahasiswaModel mahasiswa);
	
	@Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi) VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{agama}, #{golongan_darah}, #{status}, #{tahun_masuk}, #{jalur_masuk}, #{id_prodi})")
	boolean addMahasiswa(MahasiswaModel mahasiswa);
}
