$(document).ready(function () {
	//some stuff here
});

function searchFakultas() {
	$('#container_prodi').remove();
	var id_univ = $("#cari_mahasiswa_univ").val();
	if (id_univ == "--select--") {
		$('#container_fakultas').remove();
		return;
	}
	  $.ajax({
	  type: "GET",
	  url: "/fakultas?id_univ="+id_univ,
	  cache: true,
	  timeout: 600000,
	  success: function (data) {
		  	  var html = '<div id="container_fakultas">';
		  	  html += '<label for="id_fakultas">Fakultas</label>';
		  	  html += '<select class="form-control" name="id_fakultas" id="cari_mahasiswa_fakultas" onchange="searchProdi()">';
		  	  var len = data.length;
		  	  html += '<option th:value="--select--"></option>';
	              for ( var i = 0; i < len; i++) {
	            	  html += '<option value="' + data[i].id + '">'+ data[i].nama_fakultas + '</option>';
	              }
	              html += '</select>';
	          html += '</div>';
		      $('#selection_fakultas').html(html);
		  },
		  error: function (e) {
			  console.log("ERROR : ", e);
		  }
	  });
}

function searchProdi() {
	var id_fakultas = $("#cari_mahasiswa_fakultas").val();
	if (id_fakultas == "") {
		$('#container_prodi').remove();
		return;
	}
	$.ajax({
		  type: "GET",
		  url: "/prodi?id_fakultas="+id_fakultas,
		  cache: true,
		  timeout: 600000,
		  success: function (data) {
			  	  var html = '<div id="container_prodi">';
			  	  html += '<label for="id_prodi">Prodi</label>';
			  	  html += '<select class="form-control" name="id_prodi" id="cari_mahasiswa_prodi">';
			  	  var len = data.length;
		              for ( var i = 0; i < len; i++) {
		            	  html += '<option value="' + data[i].id + '">'+ data[i].nama_prodi + '</option>';
		              }
		              html += '</select>';
		              html += '</div>';
			      $('#selection_prodi').html(html);
			  },
			  error: function (e) {
				  console.log("ERROR : ", e);
			  }
		  });
}