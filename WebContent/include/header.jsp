<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ include file="./setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${cssPath}header.css">
<script src="https://kit.fontawesome.com/ef75f47104.js" crossorigin="anonymous"></script>
</head>
<body>
<!-- header 시작 -->
<header>
<c:if test="${sessionScope.sessionRole!=1}">
<!-- header 상단 -->
	
	<div class="full_width" >
		<div class="wrapper" id="hd_top">			
			<ul id="top_left">
				<li><i class="fab fa-facebook"></i></li>
				<li><i class="fab fa-youtube"></i></li>
			</ul>

			<ul id="top_right">
				<c:if test="${empty sessionScope.sessionID}">
					<li><a href="login.co">로그인</a></li>
					<li><a href="signIn.co">회원가입</a></li>
					<li><a href="" id="">고객센터</a></li>
				</c:if>
				<c:if test="${not empty sessionScope.sessionID}">
					<li><b>${sessionScope.sessionID}</b>님</li>
					<li><a href="logout.co">로그아웃</a></li>
					<li><a href="" id="">고객센터</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	
	<!-- header 중단 -->
	<div class="wrapper" id="hd_mid">
		<div>
			<a href="index.co"><img src="${imgPath}leafcom-logo.png"></a>
		</div>
		<div id="search_box">
			<ul>
				<li><input type="search" placeholder="검색어를 입력하세요."></li>
				<li id="search_btn">
				<i class="fas fa-search"></i></li>
			</ul>
		</div>
		<div id="menu_icons">
			<ul>
				<li>
					<c:if test="${empty sessionScope.sessionID}">
						<a href="login.co">
							<i class="xi-user-o"></i>
						</a>
					</c:if>
					<c:if test="${not empty sessionScope.sessionID}">
						<a href="myPageMain.co">
							<i class="xi-user-o"></i>
						</a>
					</c:if>
				</li>
				<li><a href="customer/mycart.html"><i class="xi-cart-o"></i></a></li>
			</ul>
		</div>
	</div>
	
	<!-- header 하단 -->
	<div class="full_width" id="hd_bot">	
		<div class="wrapper" id="bar_menu">
			<div id="bar_toggleBtn">
				<a href="#"><i class="fas fa-bars"></i></a>
			</div>
			<div id="bar_general">
				<ul>
					<li><a href="">이벤트</a></li>
					<li><a href="">온라인견적</a></li>
				</ul>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${sessionScope.sessionRole==1}">
	<div class="full_width" >
		<div class="wrapper" id="hd_top">
			<img src="${imgPath}logo_little.png">			
		</div>
	</div>
</c:if>		
</header>
<!-- header 끝 -->
</body>
</html>