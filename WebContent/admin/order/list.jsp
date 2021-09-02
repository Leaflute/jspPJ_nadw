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
<script src="${jsPath}order.js" type="text/javascript"></script>
<title>주문관리</title>
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
					<div class="title_letter">주문관리</div>
						<table>
							<thead>
								<tr>
									<th>주문 상태</th>
									<th>No</th>
									<th>주문자</th>
									<th>상품명</th>
									<th>가격</th>
									<th>주문 수량</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="dtos" items="${orderList}">
							<tr>
								<td>
								<c:if test="${dtos.condition eq 1}">
									[구매요청]
								</c:if>
								<c:if test="${dtos.condition eq 2}">
									[결제취소]
								</c:if>
								<c:if test="${dtos.condition eq 3}">
									[구매승인]
								</c:if>
								<c:if test="${dtos.condition eq 4}">
									[배송중]
								</c:if>
								<c:if test="${dtos.condition eq 5}">
									[배송완료]
								</c:if>
								<c:if test="${dtos.condition eq 6}">
									[교환요청]
								</c:if>
								<c:if test="${dtos.condition eq 7}">
									[환불요청]
								</c:if>
								<c:if test="${dtos.condition eq 8}">
									[환불완료]
								</c:if>
								<c:if test="${dtos.condition eq 9}">
									[구매확정]
								</c:if>
								</td>
								<td>${dtos.odId}</td>
								<td>${dtos.meId}</td>
								<td>${dtos.itName}</td>
								<td><fmt:formatNumber value="${dtos.price}" pattern="￦#,###"/></td>
								<td>${dtos.quantity}</td>
								<td colspan="6" align="right">
									<c:choose>
										<c:when test="${dtos.condition eq 1}">
										<input type="button" value="결제취소" class="little_btn"
											onclick="window.location='updateOrder.cu?odId=${dtos.odId}&condition=2'">
										<input type="button" value="구매승인" class="little_btn"
											onclick="window.location='updateOrder.cu?odId=${dtos.odId}&condition=3'">	
										</c:when>
										<c:when test="${dtos.condition eq 3}">
										<input type="button" value="배송중" class="little_btn"
											onclick="window.location='updateOrder.cu?odId=${dtos.odId}&condition=4'">
										</c:when>
										<c:when test="${dtos.condition eq 4}">
										<input type="button" value="배송완료" class="little_btn"
											onclick="window.location='updateOrder.cu?odId=${dtos.odId}&condition=5'">
										</c:when>
										<c:when test="${dtos.condition eq 7}">
										<input type="button" value="환불승인" class="little_btn"
											onclick="window.location='updateOrder.cu?odId=${dtos.odId}&condition=8'">
										</c:when>
									</c:choose>
									</td>
								</tr>
							 </c:forEach>
							</tbody>
						</table>
						<!-- 게시글 존재 여부 -->
						<div class="outer_content">	
							<c:if test="${cnt>0}">
								<!-- 처음[◀◀]/이전블록[◀]/ -->
								<c:if test="${startPage > pageBlock}">
									<a href="orderList.ad?">◀◀</a>
									<a href="orderList.ad?pageNum=${startPage - pageBlock}">◀</a>
								</c:if>
								<!-- 블럭 내의 페이지 번호 -->
								<c:forEach var="i" begin="${startPage}" end="${endPage}">
									<c:if test="${i==currentPage}">
										<span><b>[${i}]</b></span>
									</c:if>
									<c:if test="${i!=currentPage}">
										<a href="orderList.ad?pageNum=${i}">[${i}]</a>
									</c:if>
								</c:forEach>
								<!-- 다음블럭[▶]/ 마지막[▶▶] -->
								<c:if test="${pageCount > endPage}">
									<a href="orderList.ad?pageNum=${startPage + pageBlock}">[▶]</a>
									<a href="orderList.ad?pageNum=${pageCount}">[▶▶]</a>
								</c:if>
							</c:if>
						</div>	
						<div class="outer_content">
							<select id="selectedCondition" onchange="displayByCondition()">
								<option value="0">전체보기</option>
								<c:forEach var="odConMap" items="${odConMap}">
									<option value="${odConMap.key}" ${odConMap.key==condition ? 'selected="selected"':''}>${odConMap.value}</option>
								</c:forEach>
							</select>
						</div>
						<br>
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