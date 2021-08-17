<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../asset/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<title>DashBoard</title>
</head>
<body>
<%@ include file="./dashboardHeader.jsp" %>
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<%@ include file="./dashboardNav.jsp" %>	
			
			<!-- section -->
			<section class="mem_content">
				<div id="my_condition">
					<div>
						<ul>
							<li><i class="fas fa-receipt"></i></li>
							<li>주문관리</li>
							<li>0</li>
						</ul>
					</div>
					<div>
						<ul>
							<li><i class="fas fa-reply"></i></li>
							<li>환불요청</li>
							<li>0</li>
						</ul>	
					</div>
					<div>
						<ul>
							<li><i class="fas fa-file-word"></i></li>
							<li>견적문의</li>
							<li>0</li>
						</ul>					
					</div>
					<div>
						<ul>
							<li><i class="fas fa-headset"></i></li>
							<li>고객문의</li>
							<li>0</li>
						</ul>	
					</div>
				</div>
				
				<div class="outer_content">
					<div class="inner_content">
						<h1>DASHBOARD홈</h1>
					</div>
				</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../../common/footer.jsp" %>

</body>
</html>