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
<title>MY주소록</title>
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
									<th><input type="checkbox" id="chkAll" checked>
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
										<input type="checkbox" class="chkBox" name="caIdArray" value="${dto.caId}" onchange="changePrice()" checked>
										<input type="hidden" class="price" name="price" value="${dto.price}">
										<input type="hidden" class="rowPrice" name="rowPrice" value="${dto.price * dto.amount}">
										<input type="hidden" class="caId" name="caId" value="${dto.caId}">
									</td>
									<td><img src="${dto.smallImg}"></td>
									<td><a href="itemDetail.cu?itemId=${dto.itId}">${dto.itName}</a></td>
									<td>￦<fmt:formatNumber value="${dto.price}" pattern="#,###"/></td>
									<td><input type="number" name="amount" class="amount" min="1" max=""  value="${dto.amount}" readonly></td>
									<td>
										<input type="button" value="X" class="little_btn" onclick="location.href='deleteCart.cu?caId=${dto.caId}'">
									</td>
								</tr>
								<tr>
									<th colspan="7" style="font-size:20px;text-align:right;">
										구매가 : <input type="text" style="font-size:20px" name="rowPrice" class="rowPrice"
											value="<fmt:formatNumber value='${dto.amount * dto.price}' pattern='￦#.###'/>" readonly="readonly">	
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
							<div class="title_letter">총 결제금액 :<input type="text" id="totalPrice" name="totalPrice" value="<fmt:formatNumber value="0" pattern="￦#.###"/>" style="font-size:20px;"></div>
						</div>
						
						<br>
						<div class="outer_content">
							<input type="submit" value="구매하기" class="little_btn" formaction="buyInCart.cu">
							<br>
							<input type="submit" value="선택삭제" class="little_btn" formaction="deleteCartList.cu?caIdArray=${dto.caId}">
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