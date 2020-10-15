/**
 * 
 */

var MoneyService = (function() {
	function getList(callback, error){
		
		$.getJSON("/moneyLog/list", function(data) {
			if(callback) {
				callback(data);
			}
		});
	}
	
	function displayTime(timeValue) {
		var dateObj = new Date(timeValue);
		
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		
		return [year, '/', (month > 9 ? '' : '0') + month, '/',(date > 9 ? '' : '0') + date].join('');
	}
	
	return {
		getList: getList,
		displayTime : displayTime
	}
})();