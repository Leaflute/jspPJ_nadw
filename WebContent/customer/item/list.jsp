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
<script type="text/javascript" src="${jsPath}item.js"></script>
<title>상품 리스트</title>
</head>
<body>
<form action="buyNow.cu" method="post" name="itemform">
<%@ include file="../../include/header.jsp" %>
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<nav class="nav_bar">
				<ul>
				<c:forEach var="categoryMap" items="${categoryMap}">
					<li><a href="itemList.cu?categoryId=${categoryMap.key}">${categoryMap.value}</a></li>
				</c:forEach>
				</ul>
			</nav>
			
			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="category_name">
						<span>${categoryName}</span>
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
								<c:forEach var="dto" items="${itemDtos}">
								<input type="hidden" id="itemId" value="${dto.itemId}">
								<li>
									<div class="box a">
										<img src="${dto.smallImg}">
									</div>
									<div class="box b">
										<ul>
									<li><a href="itemDetail.cu?itemId=${dto.itemId}&categoryId=${dto.categoryId}&pageNum=${pageNum}">${dto.itemName}</a></li>
									<li><a href="itemDetail.cu?itemId=${dto.itemId}&categoryId=${dto.categoryId}&pageNum=${pageNum}">${dto.info}</a></li>
										</ul>
									</div>
									<div class="box c">
										<ul>
											<li>￦<fmt:formatNumber value="${dto.price}" pattern="#,###"/></li>
											<li>																																											
												${dto.grade}점
											</li>
											<li>
												<input type="number" value="1" min="1" max="${dto.stock}" name="amount" id="amount">
											</li>
											<li>
												<input type="button" class="little_btn" value="장바구니"
													onclick="cartPop()">
												<input type="submit" class="little_btn" value="구매하기" formaction="buyNow.cu?itemId=${dto.itemId}">
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
</form>
</body>
</html>