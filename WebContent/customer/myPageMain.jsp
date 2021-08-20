<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="../asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<title>My page</title>

</head>
<body>
<%@ include file="../include/header.jsp" %>

<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<%@ include file="../include/nav.jsp" %>

			<!-- section -->
			<section class="mem_content">
				<div id="my_condition">
					<div>
						<ul>
							<li><i class="fas fa-truck"></i></li>
							<li>주문, 배송</li>
							<li>0</li>
						</ul>
					</div>
					<div>
						<ul>
							<li><i class="fas fa-shopping-cart"></i></li>
							<li>장바구니</li>
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
							<li>1:1문의</li>
							<li>0</li>
						</ul>	
					</div>
				</div>
				
				<div class="outer_content">
					<div class="inner_content">
						<div class="title_letter">MY페이지</div>
					</div>
				</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../include/footer.jsp" %>

</body>
</html>