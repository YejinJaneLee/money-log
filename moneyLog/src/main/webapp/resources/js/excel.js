/**
 * 
 */

var excelService = (function(){
	function read(path, callback) {
		$.getJSON('/readExcel.json', {path : path}, function(data){
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