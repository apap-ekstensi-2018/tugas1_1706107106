package com.example.tugas1.controller;

import static org.assertj.core.api.Assertions.entry;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.tugas1.model.*;
import com.example.tugas1.service.*;

@Controller
public class MahasiswaController {
	@Autowired
	private MahasiswaService mahasiswaDAO;
	
	@Autowired
	private UniversitasService universitasDAO;
	
	@Autowired
	private FakultasService fakultasDAO;
	
	@Autowired
	private ProdiService prodiDAO;
	
	@PostConstruct
	private void initData() {
		System.out.print("run on initial start spring boot");
	}
	
	@RequestMapping("/")
    public String home(Model model) {
		model.addAttribute("title","Sistem Informasi Kemahasiswaan");
		System.out.print(mahasiswaDAO.selectNPMMax("17201185721321321"));
        return "home";
    }
	
	@RequestMapping("/mahasiswa")
    public String mahasiswa(Model model,
            @RequestParam(value = "npm", required = false) String npm) {
		MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
		if (mahasiswa != null) {
			System.out.print(mahasiswa.getId_prodi());
			ProdiModel prodi = prodiDAO.selectProdi(mahasiswa.getId_prodi());
			if (prodi != null) {
				FakultasModel fakultas = fakultasDAO.selectFakultas(prodi.getId_fakultas());
				if (fakultas != null) {
					UniversitasModel universitas = universitasDAO.selectUniversitas(fakultas.getId_univ());
					if (universitas != null) {
						model.addAttribute("title","Lihat Data Mahasiswa - "+npm);
						model.addAttribute("prodi",prodi.getNama_prodi());
						model.addAttribute("fakultas",fakultas.getNama_fakultas());
						model.addAttribute("universitas",universitas.getNama_univ());
						model.addAttribute("mahasiswa",mahasiswa);
						return "detail-mahasiswa";
					}
				}
			}
		}
		
		model.addAttribute("title","404 Not found");
		model.addAttribute("npm",npm);
		return "not-found";
    }
	
	@RequestMapping("/mahasiswa/tambah")
	public String tambahMahasiswa(Model model) {
		List<ProdiModel> arrProdi = prodiDAO.selectAllProdi();
		if (arrProdi != null && !arrProdi.isEmpty()) {
			model.addAttribute("arrProdi", arrProdi);	
			model.addAttribute("title","Tambah Mahasiswa");
			model.addAttribute("mahasiswa",new MahasiswaModel());
			return "form-tambah-mahasiswa";
		}
		model.addAttribute("title","List Prodi Not found");		
		return "not-found";
	}
	
	@RequestMapping(value = "mahasiswa/tambah/submit", method = RequestMethod.POST)
	public String tambahMahasiswaSubmit(Model model, @ModelAttribute MahasiswaModel mahasiswa) {
		//prepare value for NPM
		ProdiModel prodi = prodiDAO.selectProdi(mahasiswa.getId_prodi());
		if (prodi != null) {
			FakultasModel fakultas = fakultasDAO.selectFakultas(prodi.getId_fakultas());
			if (fakultas != null) {
				UniversitasModel universitas = universitasDAO.selectUniversitas(fakultas.getId_univ());
				if (universitas != null) {
					String tempNPM = mahasiswa.getTahun_masuk().substring(2)+universitas.getKode_univ()+prodi.getKode_prodi()+getKodeJalurMasuk(mahasiswa.getJalur_masuk());
					tempNPM = generateNPM(tempNPM);
					mahasiswa.setNpm(tempNPM);
					mahasiswaDAO.addMahasiswa(mahasiswa);
					model.addAttribute("npm", mahasiswa.getNpm());
					return "success-tambah-mahasiswa";
				}
			}
		}
		model.addAttribute("title","404 Prodi Not found");		
		return "not-found";
	}
	
	@RequestMapping("/mahasiswa/ubah/{npm}")
	public String ubahMahasiswa(Model model, @PathVariable(value = "npm") String npm) {
		List<ProdiModel> arrProdi = prodiDAO.selectAllProdi();
		MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
		if (arrProdi != null && !arrProdi.isEmpty() && mahasiswa != null) {
			model.addAttribute("arrProdi", arrProdi);	
			model.addAttribute("title","Tambah Mahasiswa");
			model.addAttribute("mahasiswa",mahasiswa);
			return "form-ubah-mahasiswa";
		}
		model.addAttribute("title","Data Mahasiswa Not found");		
		return "not-found";
	}
	
	@RequestMapping(value = "mahasiswa/ubah/{npm}", method = {RequestMethod.POST})
	public String ubahMahasiswaSubmit(Model model, @ModelAttribute MahasiswaModel mahasiswa) {
		MahasiswaModel oldMahasiswa = mahasiswaDAO.selectMahasiswa(mahasiswa.getNpm());
		ProdiModel prodi = prodiDAO.selectProdi(mahasiswa.getId_prodi());
		if (prodi != null) {
			FakultasModel fakultas = fakultasDAO.selectFakultas(prodi.getId_fakultas());
			if (fakultas != null) {
				UniversitasModel universitas = universitasDAO.selectUniversitas(fakultas.getId_univ());
				if (universitas != null && oldMahasiswa != null) {
					//check apakah ada perubahan data prodi/tahun/jalur
					if (oldMahasiswa.getId_prodi() != mahasiswa.getId_prodi() || !oldMahasiswa.getTahun_masuk().equals(mahasiswa.getTahun_masuk()) || !oldMahasiswa.getJalur_masuk().equals(mahasiswa.getJalur_masuk())) {
						String tempNPM = mahasiswa.getTahun_masuk().substring(2)+universitas.getKode_univ()+prodi.getKode_prodi()+getKodeJalurMasuk(mahasiswa.getJalur_masuk());
						tempNPM = generateNPM(tempNPM);
						mahasiswa.setNpm(tempNPM);
					}
					mahasiswaDAO.updateMahasiswa(mahasiswa);
					model.addAttribute("npm", mahasiswa.getNpm());
					return "success-ubah-mahasiswa";
				}
			}
		}
		model.addAttribute("title","404 Prodi Not found");		
		return "not-found";
	}
	
	@RequestMapping("/kelulusan")
	public String kelulusan(Model model,
			@RequestParam(value = "thn", required = false) String tahun,
			@RequestParam(value = "prodi", required = false, defaultValue = "0") int id_prodi) {
		if (tahun == null || id_prodi == 0) {
			List<ProdiModel> arrProdi = prodiDAO.selectAllProdi();
			model.addAttribute("title","Presentase Kelulusan");
			model.addAttribute("arrProdi",arrProdi);
			return "kelulusan";
		} else {
			ProdiModel prodi = prodiDAO.selectProdi(id_prodi);
			if (prodi != null) {
				FakultasModel fakultas = fakultasDAO.selectFakultas(prodi.getId_fakultas());
				if (fakultas != null) {
					UniversitasModel universitas = universitasDAO.selectUniversitas(fakultas.getId_univ());
					if (universitas != null) {
						String totalMahasiswa = mahasiswaDAO.totalMahasiswaPerProdi(id_prodi, tahun);
						String totalMahasiswaLulus = mahasiswaDAO.totalMahasiswaPerStatusPerProdi(id_prodi, "Lulus", tahun);
						if (totalMahasiswa != null && totalMahasiswaLulus != null) {
							double tempTotalMahasiswa = Double.parseDouble(totalMahasiswa);
							double tempTotalMahasiswaLulus = Double.parseDouble(totalMahasiswaLulus);
							if (tempTotalMahasiswa != 0 && tempTotalMahasiswaLulus != 0) {
								double percentage = tempTotalMahasiswaLulus/tempTotalMahasiswa * 100;
								String detailPercentage = totalMahasiswaLulus+" dari "+totalMahasiswa+" Mahasiswa telah lulus";
								model.addAttribute("tahun",tahun);
								model.addAttribute("prodi",prodi.getNama_prodi());
								model.addAttribute("fakultas",fakultas.getNama_fakultas());
								model.addAttribute("universitas",universitas.getNama_univ());
								model.addAttribute("percentage",new DecimalFormat("#0.00").format(percentage)+"%");
								model.addAttribute("detailPercentage",detailPercentage);
								model.addAttribute("title","Presentase Kelulusan");
								return "report-kelulusan";
							}
						}
					}
				}
			}
			model.addAttribute("title","Data dengan Tahun "+tahun+" tidak tersedia");		
			return "not-found";
		}
	}
	
	@RequestMapping("/mahasiswa/cari")
	public String cariMahasiswa(Model model,
			@RequestParam(value = "id_univ", required = false, defaultValue="0") int id_univ,
            @RequestParam(value = "id_fakultas", required = false, defaultValue="0") int id_fakultas,
            @RequestParam(value = "id_prodi", required = false, defaultValue="0") int id_prodi) {
		ProdiModel prodi = prodiDAO.selectProdi(id_prodi);
        if (id_univ != 0 && id_fakultas != 0 && id_prodi != 0) {
        		System.out.println("masuk 1");
        		List<MahasiswaModel> arrMahasiswa = mahasiswaDAO.selectMahasiswaByProdi(id_prodi);
        		model.addAttribute("prodi",prodi.getNama_prodi());
        		model.addAttribute("arrMahasiswa",arrMahasiswa);
        		return "list-mahasiswa";
        } else if (id_univ != 0 && id_fakultas != 0) {
	        	model.addAttribute("title","404 not found");		
	    		return "not-found";
        } else if (id_univ != 0) {
	        	model.addAttribute("title","404 not found");		
	    		return "not-found";
        } else {
	        	List<UniversitasModel> arrUniv = universitasDAO.selectAllUniversitas();
	    		if (arrUniv != null && !arrUniv.isEmpty()) {
	    			model.addAttribute("arrUniv", arrUniv);	
	    			model.addAttribute("title","Cari Mahasiswa");
	    			return "cari-mahasiswa";
	    		}
	    		model.addAttribute("title","404 not found");		
	    		return "not-found";
        }
	}
	
	@RequestMapping("/fakultas")
    public ResponseEntity<?> getArrFakultasAjax(@RequestParam(value = "id_univ", required = false, defaultValue="0") int id_univ) {
		List<FakultasModel> arrFakultas = fakultasDAO.selectFakultasByUnivId(id_univ);
        return ResponseEntity.ok(arrFakultas);
    }
	
	@RequestMapping("/prodi")
    public ResponseEntity<?> getArrProdiAjax(@RequestParam(value = "id_fakultas", required = false, defaultValue="0") int id_fakultas) {
		List<ProdiModel> arrProdi = prodiDAO.selectProdiByFakultasId(id_fakultas);
        return ResponseEntity.ok(arrProdi);
    }
	
	private String generateNPM(String initValue) {
		String maxNPM = mahasiswaDAO.selectNPMMax(initValue+"%");
		if (maxNPM != null) {
			int temp = Integer.parseInt(maxNPM.substring(9)) + 1;
			String endNPM = "";
			if (temp < 10) {
				endNPM = "00"+new Integer(temp).toString();
			} else if (temp >= 10 && temp < 100) {
				endNPM = "0"+new Integer(temp).toString();
			} else {
				endNPM = new Integer(temp).toString();
			}
			return initValue+endNPM;
		}
		return initValue+"001";
	}
	
	private String getKodeJalurMasuk(String jalurMasuk) {
		if (jalurMasuk.equals("Undangan Olimpiade")) {
			return "53";
		} else if (jalurMasuk.equals("Undangan Reguler/SNMPTN")) {
			return "54";
		} else if (jalurMasuk.equals("Undangan Paralel/PPKB")) {
			return "55";
		} else if (jalurMasuk.equals("Ujian Tulis Bersama/SBMPTN")) {
			return "57";
		} else {
			return "62";
		}
	}
}
