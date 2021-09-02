<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">

google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawTitleSubtitle);

function drawTitleSubtitle() {
	var day = document.getElementsByName("day");
	var year = document.getElementsByName("year");
	var month = document.getElementsByName("month");
	var sales = document.getElementsByName("sales");
	var margin = document.getElementsByName("margin");

	var rowArr = new Array;
	
	for(var i=0; i<day.length; i++) {
		rowArr[i] = [new Date(year[i].value, month[i].value, day[i].value) , margin[i].value, sales[i].value];
	}
	
	var data = new google.visualization.DataTable();
	data.addColumn('date', '일자');
	data.addColumn('string', '영업이익');
	data.addColumn('string', '총 매출액');
	
	data.addRows(rowArr);

    var options = {
			chart: {
				title: '날짜별 매출액과 영업이익 비교',
				subtitle: '리프컴'
			},
			hAxis: {
				title: 'date',
				format: 'yyyy-mm-dd',
			},
			vAxis: {
				title: '비교'
			}
    };

      var materialChart = new google.charts.Bar(document.getElementById('chart_div'));
      materialChart.draw(data, options);
    }
</script>
<title>주문관리</title>
</head>
<body>
<form action="">
<%@ include file="../../include/header.jsp" %> 
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<%@ include file="../../include/nav.jsp" %> 
			
			<!-- section -->
			<section class="mem_content">
			<div class="outer_content">
				<div class="inner_content">
					<div class="title_letter">결산</div>
			  		<div id="chart_div" style="width:100%;height:600px">
					</div>
					<c:forEach var="dtos" items="${reportList}">
					<ul>
						<li><input type="hidden" name="sales" value="${dtos.sales}"></li>
						<li><input type="hidden" name="margin" value="${dtos.margin}"></li>
						<li><input type="hidden" name="year" value="${dtos.year}"></li>
						<li><input type="hidden" name="month" value="${dtos.month}"></li>
						<li><input type="hidden" name="day" value="${dtos.day}"></li>
					</ul>
					</c:forEach>
				</div>
			</div>	
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../../include/footer.jsp" %>
</form>
</body>
</html>