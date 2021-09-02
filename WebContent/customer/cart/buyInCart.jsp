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
<form action="buyInCartAction.cu" method="post">
<%@ include file="../../include/header.jsp" %> 
<input type="hidden" name="adId" value="${aVo.adId}">
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<section class="wrapper">
			<div class="item_info">
				<div class="item_tree">
					<div class="outer_content">
					<div class="inner_content">
					<div class="title_letter">장바구니 구매하기</div>
						<table>
							<thead>
								<tr>
									<th>상품이미지</th>
									<th>상품명</th>
									<th>가격</th>
									<th>구매수량</th>
								</tr>
							</thead>
							<tbody>
							<c:set var="sumPrice" value="0"/>
								<c:forEach var="dtos" items="${coList}">
								<c:set var="sumPrice" value="${sumPrice + dtos.amount * dtos.price}"/>
								<input type="hidden" value="${dtos.caId}" name="caIdArray">
								<tr class="cartcol">
									<td>
										<img src="${dtos.smallImg}">
									</td>
									<td>
										<a href="itemDetail.cu?itemId=${dtos.itId}">${dtos.itName}</a>
									</td>
									<td>
										￦<fmt:formatNumber value="${dtos.price}" pattern="#,###"/>
										<input type="hidden" class="price" name="price" value="${dtos.price}">
									</td>
									<td>
										<input type="number" class="amount" name="amount" min="1" max="" value="${dtos.amount}" readonly>
									</td>
								</tr>
								<tr>
									<th colspan="4" style="font-size:20px;text-align:right;">
										상품가격 : <input type="text" name="rowPrice" class="rowPrice" id="rowPrice"
											value="${dtos.amount * dtos.price}" style="font-size:20px" readonly>원
									</th>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<div id="purchase_box">
							<div class="pur_box left">
								<ul>
									<li>받는사람: ${aVo.recipient}</li>
									<li>우편번호: ${aVo.zipcode}</li>
									<li>주소: ${aVo.main}</li>
									<li>상세주소: ${aVo.detail}</li>
									<li>연락처: ${aVo.tel}</li>
								</ul>
							</div>
							<div class="pur_box right">
								<ul>
									<li>총 결제액</li>
									<li>
										<span style="font-size:25px;font-weight:bold"><c:out value="${sumPrice}"/>원</span>
									</li>
									<li>
										<input type="submit" value="구매확정" class="little_btn">
									</li>
								</ul>
							</div>
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