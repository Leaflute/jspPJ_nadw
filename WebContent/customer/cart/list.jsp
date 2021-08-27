<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script type="text/javascript" src="${jsPath}item.js"></script>
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<title>상품관리</title>
</head>
<body>
<form action="" method="post">
<%@ include file="../../include/header.jsp" %> 
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<%@ include file="../../include/nav.jsp" %> 
			
			<!-- section -->
			<section class="mem_content">
			<div class="outer_content">
				<div class="inner_content">
					<div class="title_letter">상품관리</div>
						<table>
							<thead>
								<tr>
									<th><input type="checkbox" id="chkAll" onclick="chkAll()">
									<th>No</th>
									<th>상품이미지</th>
									<th>상품명</th>
									<th>가격</th>
									<th>재고</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="cart" items="${cartList}" varStatus="status">
							<tr id="cartcol">
								<td><input type="checkbox" name="chkEach" value="${cartList.caId}" checked></td>
								<td>${cart.caId}</td>
								<td><img src="${cart.smallImg}"></td>
								<td><a href="itemDetail.ad?itemId=${cart.itId}>${cart.itName}"></a></td>
								<td>￦<fmt:formatNumber value="${cart.price}" pattern="#,###"/></td>
								<td>${cart.amount}</td>
								<td>
									<input type="submit" value="구매하기" class="little_btn" formaction="buyCart.">
									<input type="submit" value="삭제하기" class="little_btn" formaction="deleteCart.">
								</td>		
							</tr>
							 </c:forEach>
							</tbody>
						</table>
						<!-- 페이지 컨트롤 -->
						<!-- 게시글 존재 여부 -->
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
							<input type="submit" value="전체삭제" class="little_btn" formaction="">
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