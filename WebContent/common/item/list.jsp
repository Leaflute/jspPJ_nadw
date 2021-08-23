<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<link rel="stylesheet" type="text/css" href="${cssPath}item_category.css">
<title>상품 리스트</title>
</head>
<body>
<%@ include file="../../include/header.jsp" %>
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<nav class="nav_bar">
				<c:forEach var="" items="">
				<ul>
					<li>${category}</li>
				</ul>
				</c:forEach>
			</nav>
			
			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="category_name">
						<span>CPU</span>
						</div>
						<div class="product_tap_area">
							<ul>
								<li>추천순</li>
								<li>인기상품순</li>
								<li>낮은가격순</li>
								<li>높은가격순</li>
								<li>등록일순</li>
							</ul>
						</div>
						<div class="product_table">
							<ul class="product_list">
								<c:forEach var="dto" items="dtos">
								<li>
									<div class="box a">
										<img src="${dto.smallImg}">
									</div>
									<div class="box b">
									<a href="item_detail.html"></a>
									</div>
									<div class="box c">
										<ul>
											<li>${dto.price}</li>
											<li>${dto.grade}점</li>
											<li>
												<input type="button" class="little_btn" value="장바구니"
													onclick="window.location='${not empty sessionScope.sessionID ? addCart.cu?dto : login.co}'">
												<input type="button" class="little_btn" value="구매하기"
													onclick="window.location=''">
											</li>
										</ul>
									</div>
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->
<%@ include file="../../include/footer.jsp" %>
</body>
</html>