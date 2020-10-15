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
	$("#file").on("change", function (e) {
		var files = $(this)[0].files;
		var fileName = files[0].name;
		$("#path").val(fileName);
		
		excelService.read(fileName, function(result){
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
						}
						html.push("<td id='column_" + i + j + "'>" + data + "</td>");
					}
				});
				html.push("</tr>");
			});
			html.push("</table>");
			
			$("#excelList").append(html.join(""));
			
		});
	});
	
	$("#registBtn").on("click", function(){
		var item = [];
		var payDate = [];
		var amount = [];
		
		$("select[id^=selectCol] option:selected").each(function(){
			if ($(this).val() != "") {
				var type = $(this).val();
				
				var selectedColumn = $(this).parent().attr("id");
				selectedColumn = selectedColumn.split("_")[1];
				
				$("input[type=checkbox]:checked").each(function(){
					var selectedRow = $(this).attr("id");
					selectedRow = selectedRow.split("_")[1];
					if (type == "item") {
						item.push($("#column_" + selectedRow + selectedColumn).text());
					} else if (type == "payDate") {
						payDate.push($("#column_" + selectedRow + selectedColumn).text());
					} else if (type == "amount") {
						amount.push($("#column_" + selectedRow + selectedColumn).text());
					}
				});
			}
		}); 
		
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
					
					obj[type] = $("#column_" + selectedRow + selectedColumn).text();
					
					arr.push(obj);
				}
			}); 
		});
		console.log(arr);
		
		/* var $select = $("select[id^=selectCol]");
		
		$select.each(function(){
			var $this = $(this);
			$this.
		});
		*/
		
		
	});
});

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
		<div id="fileLoad" >
			<input type="file" id="file" style="display:none;"/>
			<input type="text" id="path"/> <button id="btn" onclick="$('#file').click();">Load</button>
		</div>
		<button id="registBtn">등록하기</button>
		
		<div id="excelList">
		</div>
	</div>
</div>
</body>
</html>