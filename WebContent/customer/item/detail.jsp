<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<link rel="stylesheet" type="text/css" href="${cssPath}item_detail.css">
<script type="text/javascript" src="${jsPath}item.js"></script>
<meta charset="UTF-8">
<title>상세 페이지</title>
</head>
<body>
<form action="buyNow.cu" method="Post" name="itemform">
<input type="hidden" name="itemId" id="itemId" value="${dto.itemId}">
<%@ include file="../../include/header.jsp" %>
<!-- article 시작 -->
<article class="container">
	<!-- section -->
	<section class="wrapper">
		<div class="outer_content">
			<div class="item_info">
				<div class="item_tree">
					<ul>
						<li><a href="index.co">홈</a></li>
						<li>></li>
						<li><a href="itemList.cu?categoryId=${categoryId}"></a>${dto.categoryName}</li>
						<li>></li>
						<li>[${dto.company}] ${dto.categoryName}</li>
					</ul>
				</div>
				<div class="item_title">
					<dl>
						<dt>[${dto.company}] ${dto.itemName} </dt>
						<dd>${dto.info}</dd>
					</dl>
				</div>
				<div class="item_content">
					<div class="item_content_img">
						<img src="${dto.largeImg}">
					</div>
					<div class="item_content_tbl">
						<table>
							<tr><th>판매가</th><td>￦<fmt:formatNumber value="${dto.price}" pattern="#,###"/></td></tr>
							<tr><th>인기도</th><td>${dto.grade}점</td></tr>
							<tr><th>배송정보</th><td>기본배송 | 당일발송</td></tr>
							<tr><th>재고:${dto.stock}</th>
							<td>
								<input type="number" min=1 max="${dto.stock}" id="amount" name="amount" placeholder="수량">
							</td>
						</table>
						<div class="item_content_btn">
							<input type="button" id="addCart" value="장바구니" class="little_btn"
								onclick="cartPop()">
							<input type="submit" id="buyNow" value="바로구매" class="little_btn">
							<input type="button" value="목록" class="little_btn"
								onclick="window.history.back()">		
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- section 종료 -->
</article>
<!-- article 끝 -->

<%@ include file="../../include/footer.jsp" %>
</form>
</body>
</html>