<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${jsPath}cart.js"></script>
<title>장바구니</title>
</head>
<body>
<form action="" method="post">
<%@ include file="../../include/header.jsp" %> 
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<section class="wrapper">
			<div class="item_info">
				<div class="item_tree">
					<div class="outer_content">
					<div class="inner_content">
					<div class="title_letter">장바구니 보기</div>
						<table>
							<thead>
								<tr>
									<th><input type="checkbox" name="caIdArray" id="caIdArray" onclick="selectAll(this)">
									<th>상품이미지</th>
									<th>상품명</th>
									<th>가격</th>
									<th>구매수량</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="dto" items="${cartList}" varStatus="status">
								<tr class="cartcol">
									<td>
										<input type="checkbox" name="caIdArray" id="caIdArray" value="${dto.caId}"
											onchange="totalPrice();">
									</td>
									<td>
										<img src="${dto.smallImg}">
									</td>
									<td>
										<a href="itemDetail.cu?itemId=${dto.itId}">${dto.itName}</a>
									</td>
									<td>
										￦<fmt:formatNumber value="${dto.price}" pattern="#,###"/>
										<input type="hidden" class="price" name="price" value="${dto.price}">
									</td>
									<td>
										<input type="number" class="amount" name="amount" min="1" max=""  value="${dto.amount}">
									</td>
									<td>
										<input type="button" value="삭제" class="little_btn" onclick="location.href='deleteCart.cu?caId=${dto.caId}'">
									</td>
								</tr>
								<tr>
									<th colspan="6" style="font-size:20px;text-align:right;">
										구매가 : <input type="text" name="rowPrice" class="rowPrice"
											value="${dto.amount * dto.price}" style="font-size:20px" readonly>원
									</th>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<!-- 페이지 컨트롤 -->
						<!-- 장바구니 존재 여부 -->
						<c:if test="${empty cartList}">
							장바구니 리스트가 존재하지 않습니다.
						</c:if>	
						<br>
						<c:if test="${not empty cartList}">
						<div class="outer_content">
							<div class="title_letter">총 결제금액 : <input type="text" id="purchasePrice" name="purchasePrice" value="0" style="font-size:25px;text-align:center" readonly> 원</div>
						</div>
						
						<br>
						<div class="outer_content">
							<input type="submit" value="선택구매" class="little_btn" formaction="buyInCart.cu">
							<br>
							<input type="submit" value="선택삭제" class="little_btn" formaction="deleteCartList.cu">
						</div>
						</c:if>
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