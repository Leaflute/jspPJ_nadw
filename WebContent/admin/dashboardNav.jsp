<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../asset/setting.jsp" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<title>Insert title here</title>
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>

</head>
<body>
	<nav class="nav_bar">
		<div id="mem_pic">DASHBOARD
		</div>
		<div>
			<ul>
				<li class="page_title"><a href="dashboard.html">DASHBOARD</a></li>				
				<li><a href="#">관리자정보관리</a></li>
				<li><a href="dashboard_mem.html">회원관리</a></li>
				<li><a href="dashboard_order.html">주문관리</a></li>
				<li><a href="dashboard_item.html">상품관리</a></li>
				<li><a href="dashboard_report.html">결산관리</a></li>
				<li><a href="dashboard_bbs.html">게시판관리</a></li>
				<li><a href="../index.html">로그아웃</a></li>
			</ul>
		</div>
	</nav>
</body>
</html>