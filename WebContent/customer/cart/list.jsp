<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${jsPath}cart.js"></script>
<title>상품관리</title>
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
							<c:forEach var="dto" items="${cartList}">
							<tr class="cartcol">
								<td><input type="checkbox" name="caIdArray" value="${dto.caId}" checked></td>
								<td><img src="${dto.smallImg}"></td>
								<td><a href="itemDetail.cu?itemId=${dto.itId}">${dto.itName}</a></td>
								<td>￦<fmt:formatNumber value="${dto.price}" pattern="#,###"/></td>
								<td><input type="number" value="${dto.amount}" min="1" max="" onchange="delCart()"></td>
								<td>
									<input type="submit" value="X" class="little_btn" formaction="deleteCart.cu">
								</td>		
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
						<div class="outer_content">
							상품금액 = <span><fmt:formatNumber value="0" pattern="#.###"/></span>
						</div>
						<br>
						<div class="outer_content">
							<input type="submit" value="구매하기" class="little_btn" formaction="">
							<br>
							<input type="submit" value="선택삭제" class="little_btn" formaction="">
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