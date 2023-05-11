
	var imgfile;
	$(document).ready(function() {
		$("#imgfile").on("change", handleImgFileSelect);
	});
	
	function handleImgFileSelect(e){
		var files = e.target.files;
		var filesArr = Array.prototype.slice.call(files);
		
		filesArr.forEach(function(f){
			
			imgfile = f;
			
			var reader = new FileReader();
			reader.onload = function(e){
				$("#img-thumbnail").attr("src", e.target.result);
			}
			reader.readAsDataURL(f);
			
		});
	}
	
	$(function () {
		$('#icon').click(function (e) {
			e.preventDefault();
			$('#imgfile').click();
			});
		});                  
