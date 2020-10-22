<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<style>
#calendar {
	display: table;
	width: 100%;
}
.day {
	border: 1px solid #999;
	width: 40px;
	/*height: 20px;
	float: left;*/
	display: table-cell;
	padding: 3px 10px;
}

.week {
	display: table-row;
}
.heading {
	background-color: #EEE;
	display: table-header-group;
	font-weight: bold;
}
.left{
	width: 45%;
	float: left;
	margin-left: 50px;
}

.right{
	width: 45%;
	float: right;
	margin-right:50px;
}
.itemList{
	height:100px;
	font-size:5px;
}
.header{
	width: 100%;
	height: 40px;
	background-color: black;
	margin-bottom: 20px;
}

#list {
	overflow-y: scroll;
	height: 674px;
	border : 1px solid #EEE;
}

#list div[id^=list] {
	padding: 5px 0;
	margin: 10px 0;
	background-color: #EEE;
}

body{
	margin: 0px;
}
</style>
<jsp:useBean id="today" class="java.util.Date" />
<fmt:formatDate value="${today}" pattern="yyyyMMddhhmm" var="nowDate" />
<script src="/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/calendar.js"><c:param name="v" value="${nowDate}"/></c:url>"></script>

<script type="text/javascript">
	
	var days = new Array('SUN', 'MON', 'TUE', 'WEN', 'THU', 'FRI', 'SAT');
	$(document).ready(function(){
		
		setCalendar(2020, 08);
		setDateList(2020, 08);
	});
	
	function setCalendarHeader() {
		var html = "<div class='heading'>";
		
		for(var j=0; j < 7; j++) {
			html += "<div id='" + days[j] + "' class='day'>";
			html += days[j];
			html += "</div>";
		}
			html += "</div>";
		$("#calendar").append(html);
	}
	
	const setCalendar = (Year, Month) => {
		
		const startDate = new Date(Year, Month - 1, 1);
		const endDate = new Date(Year, Month, 0);
		const prevDate = new Date(Year, Month - 1, 0);
		const nextDate = new Date(Year, Month, 1);
		
		let html = "";
		let day = 1;
		
		setCalendarHeader();
		
		for (let i=0; i < Math.ceil(endDate.getDate() / 7); i++) {
			if (endDate.getDate() >= day) {
				html += "<div id='week" + (i+1) + "' class='week'>";
				for (let j=0; j < 7; j++) {
					let date = 0;
					let dateObj = startDate;
					
					if (i == 0 && j < startDate.getDay()) {
						date = (prevDate.getDate() - (startDate.getDay() - 1) + j);
						dateObj = prevDate;
					} else if (endDate.getDate() < day) {
						date = (j - endDate.getDay());
						dateObj = nextDate;
					} else {
						date = day++;
					}
					
					html += "<div id='" + ['date_', dateObj.getFullYear(), setDate(dateObj.getMonth() + 1), setDate(date)].join('') + "' class='day'";
					html += (dateObj.getMonth() + 1 == Month ? "" : " style='color:grey;'" ) + ">";
					html += "<div class='date'><span>" + setDate(date) + "</span></div>";
					html += "<div class='itemList'></div>";
					html += "</div>";
					
				}
				html += "</div>";
			}
		}
		$("#calendar").append(html);
		showItems(setDate(Month));
	}
	
	const setDate = date => {
		if (date < 10) {
			date = "0" + date;
		}
		return date;
	}
	
	const setDateList = (Year,Month) => {
		const endDate = new Date(Year, Month, 0);
		let html = "";
		
		for (let i=1; i <= endDate.getDate(); i++) {
			html += "<div id='list_" + [Year, (Month > 9 ? '' : '0') + Month,(i > 9 ? '' : '0') + i].join('') + "'>";
			html += [Year , '/', setDate(Month) , '/', setDate(i)].join('') + "</div>";
			html += "<div class = 'itemList'></div>";
		}
		$("#list").append(html);
	}
	
	function showItems(month) {
		MoneyService.getListByMonth(month, function(list) {
			
			for(var i=0, len = list.length||0; i < len ; i++) {
				let html = "";
				html += "<div style='width:100%;' class='item' id='item" + i +"'>";
				html += "<span>" + list[i].item +  " " + list[i].amount + "</span>"
				html += "</div>";
				
				$("#date_"+MoneyService.displayTime(list[i].payDate)).children(".itemList").append(html);
				
				html = "";
				html += "<div>" + list[i].item+  " " + list[i].amount + "</div>";
				$("#list_"+MoneyService.displayTime(list[i].payDate)).next().append(html);
			}
			
		});
	}
	
</script>
<body>
<div class="header">
</div>
<div class="content" style="min-width:1000px;">
	<div class="left">
		<div id="calendar">
		</div>
	</div>
	<div class="right">
		<div id="list">
		</div>
	</div>
</div>
</body>
</html>