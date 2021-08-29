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
<style type="text/css">

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown_content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 240px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    top: 100%;
    z-index: 15;
}

.dropdown_content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    font-size: 24px;
}

.dropdown_content a:hover {
    background-color: #f1f1f1
}
.dropdown:hover .dropdown_content {
    display: block;
}
.dropdown:hover .dropbtn {
    background-color: #3e8e41;
}

</style>
</head>
<body>
<!-- header 시작 -->
<header>
<c:if test="${sessionScope.member.role!=1}">
<!-- header 상단 -->
	
	<div class="full_width" >
		<div class="wrapper" id="hd_top">			
			<ul id="top_left">
				<li><i class="fab fa-facebook"></i></li>
				<li><i class="fab fa-youtube"></i></li>
			</ul>

			<ul id="top_right">
				<c:if test="${empty sessionScope.member}">
					<li><a href="login.co">로그인</a></li>
					<li><a href="signIn.co">회원가입</a></li>
					<li><a href="login.co" onclick="alert('로그인 후 이용해주세요.')">고객센터</a></li>
				</c:if>
				<c:if test="${not empty sessionScope.member}">
					<li><b>${sessionScope.member.name}</b>님이 접속중입니다.</li>
					<li><a href="logout.co">로그아웃</a></li>
					<li><a href="csList.bo?boardId=1&fullList=false">고객센터</a></li>
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
					<c:if test="${empty sessionScope.member}">
						<a href="login.co">
							<i class="xi-user-o"></i>
						</a>
					</c:if>
					<c:if test="${not empty sessionScope.member}">
						<a href="myPageMain.co">
							<i class="xi-user-o"></i>
						</a>
					</c:if>
				</li>
				<li><a href="cartList.cu"><i class="xi-cart-o"></i></a></li>
			</ul>
		</div>
	</div>
	
	<!-- header 하단 -->
	<div class="full_width" id="hd_bot">	
		<div class="wrapper" id="bar_menu">
			<div class="dropdown" id="bar_toggleBtn">
				<i class="fas fa-bars"></i>
				<div class="dropdown_content">
					<a href="itemList.cu?categoryId=1">CPU</a>
					<a href="itemList.cu?categoryId=2">RAM</a>
					<a href="itemList.cu?categoryId=3">메인보드</a>
					<a href="itemList.cu?categoryId=4">그래픽카드</a>
					<a href="itemList.cu?categoryId=5">파워서플라이</a>
					<a href="itemList.cu?categoryId=6">SSD</a>
					<a href="itemList.cu?categoryId=7">HDD</a>
					<a href="itemList.cu?categoryId=8">케이스</a>
					<a href="itemList.cu?categoryId=9">모니터</a>
				 </div>
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
<c:if test="${sessionScope.member.role==1}">
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