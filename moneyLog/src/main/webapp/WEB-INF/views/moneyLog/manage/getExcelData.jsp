<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<style>

body{
	margin: 0px;
}
.header{
	width: 100%;
	height: 40px;
	background-color: black;
	margin-bottom: 20px;
}
.left{
	width: 30%;
	float: left;
}

.right{
	width: 70%;
	float: right;
}
.content {
	width: 100%
}
</style>
<script src="/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/resources/js/excel.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	// TEST용
	/* excelService.read("C:\\upload\\2020\\10\\19\\065e86cf-54d8-4af6-bcf0-ad7e98e233c0_거래내역조회20200916.xls", function(result){
		setData(result);
	}); */
	
	var list = [];
	$("#excelList").on("change", "select[id^=selectCol]", function(){
		var selectVal	= $(this).val();
		var prevVal		= $(this).data('val');
		
		if ($.inArray(selectVal, list) < 0) {
			if (selectVal != "") {
				list.push(selectVal);
			}
		} else {
			alert("이미 선택된 값입니다.");
			$(this).val(prevVal);
			return;
		}
		
		if (prevVal !== undefined) {
			list.splice($.inArray(prevVal, list), 1);
		}
		
		$(this).data('val', $(this).val());
	});
	
	$("#file").on("change", function (e) {
		var file = $(this)[0].files[0];
		var fileName = file.name;
		 
		$("#path").text(fileName);
		
		var formData = new FormData();
		formData.append("uploadFile", file);
		
		$.ajax({
			type: 'post',
			url: '/uploadFile',
			processData : false,
			contentType: false,
			data: formData,
			type: 'POST',
			dataType: 'text',
			success: function(result){
				var path = decodeURIComponent(result);
				
				excelService.read(path, function(result){
					setData(result);
				});
			}
		});
	});
	
	$("#registBtn").on("click", function(){
		var item = [];
		var payDate = [];
		var amount = [];
		
		// 선택 안된 값 체크
		
		var arr = new Array();
		$("input[type=checkbox]:checked").each(function(){
			var selectedRow = $(this).attr("id");
			selectedRow = selectedRow.split("_")[1];
			
			var obj = new Object();
			
			$("select[id^=selectCol] option:selected").each(function(){
				if ($(this).val() != "") {
					var type = $(this).val();
					var selectedColumn = $(this).parent().attr("id");
					selectedColumn = selectedColumn.split("_")[1];
					
					if (type == "payDate") {
						obj[type] = toDate($("#column_" + selectedRow + selectedColumn).text());
					} else if (type == "amount") {
						obj[type] = parseInt($("#column_" + selectedRow + selectedColumn).text());
					} else {
						obj[type] = $("#column_" + selectedRow + selectedColumn).text();
					}
				}
			}); 
			arr.push(obj);
		});
		
		excelService.addList(arr, function(result) {
			alert(result);
		});
		
	});
	
	$("#loadBtn").on("click", function(){
		$('#file').click();
	});
	
});
function toDate(str){
	var yyyyMMdd = String(str);
	
	var year = yyyyMMdd.substring(0,4);
	var month = yyyyMMdd.substring(5,7);
	var date = yyyyMMdd.substring(8,10);
	
	return new Date(Number(year), Number(month)-1, Number(date))
}

function setData(result) {
	
	var html = [];
	
	html.push("<table>");
	$.each(result, function(i, row){
		html.push("<tr>");
		$.each(row, function(j, data){
			if (i == 0) {
				if (j == 0) {
					html.push("<td></td>");
				}
				html.push("<th>");
				html.push("	<select id='selectCol_" + j +"'>");
				html.push("		<option value=''>-</option>");
				html.push("		<option value='payDate'>날짜</option>");
				html.push("		<option value='item'>항목</option>");
				html.push("		<option value='amount'>비용</option>");
				html.push("	</select><br/>");
				html.push(data);
				html.push("</th>");
			} else {
				if (j == 0) {
					html.push("<td><input type='checkbox' id='row_" + i + "' checked/></td>");
					//html.push("<td><input type='checkbox' id='row_" + i + "' /></td>");
				}
				html.push("<td id='column_" + i + j + "'>" + data + "</td>");
			}
		});
		html.push("</tr>");
	});
	html.push("</table>");
	
	$("#excelList").append(html.join(""));
		
}
</script>
<body>
<div class="header">
</div>
<div class="content" style="min-width:1000px;">
	<div class="left">
		<li>사용자 정보 수정</li>
		<li>캘린더 데이터 가져오기</li>
	</div>
	
	<div class="right">
		<div id="top" style="height:30px; width:70%;">
			<span id="fileLoad" style="float:left;">
				<input type="file" id="file" style="display:none;" accept="application/vnd.ms-excel" />
				<button id="loadBtn">Load</button><span id="path"></span> 
			</span>
			<button id="registBtn" style="float:right;">등록하기</button>
		</div>
		<div id="excelList">
		</div>
	</div>
</div>
</body>
</html>