/**
 * 
 */

var excelService = (function(){
	function read(fileName, callback) {
		
		/*$.ajax({
			type: 'post',
			url: '/readExcel',
			processData: false,
			contentType	: "application/json; charset=utf-8",
			//data: formData,
			type: 'POST',
			dataType: 'json',
			success: function(result, status, xhr){
				//console.log(result);
				
				if (callback){
					callback(result);
				}
			}
		});*/
		
		$.getJSON('/readExcel.json', {fileName : fileName}, function(data){
			if (callback){
				callback(data);
			}
		});
	}
	
	function addList(list, callback, error){
		console.log("====> add");
		
		$.ajax({
			type		: "post",
			url			: "/registList",
			data		: JSON.stringify(list),
			contentType	: "application/json; charset=utf-8",
			success		: function(result, status, xhr){
				if (callback) {
					callback(result);
				}
			},
			error		: function (xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	return {
		read: read,
		addList: addList
		
	}
})();