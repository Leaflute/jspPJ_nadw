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

<title>배송지관리</title>
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
					<div class="title_letter">MY배송지</div>
						<table>
							<thead>
								<tr>
									<th>주소 상태</th>
									<th>받는 사람</th>
									<th>연락처</th>
									<th>우편번호</th>
									<th>주소</th>
									<th>상세 주소</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="dtos" items="${addressList}">
							<tr>
								<td>
								<c:if test="${dtos.condition==1}">[기본]</c:if>
								<c:if test="${dtos.condition!=1}">[일반]</c:if>
								</td>
								<td>${dtos.recipient}</td>
								<td>${dtos.tel}</td>
								<td>${dtos.zipcode}</td>
								<td>${dtos.main}</td>
								<td>${dtos.detail}</td>
								<td>
									<input type="button" value="수정하기" class="little_btn"
										onclick="window.location='updateAddress.cu?adId=${dtos.adId}'">
									<input type="button" value="삭제하기" class="little_btn"
										onclick="window.location='deleteAddress.cu?adId=${dtos.adId}'">
								</td>		
							</tr>
							 </c:forEach>
							</tbody>
						</table>
	
						<br>
						<div class="outer_content">
							<input type="button" value="배송지 추가" class="little_btn" onclick="window.location='addAddress.cu'">
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