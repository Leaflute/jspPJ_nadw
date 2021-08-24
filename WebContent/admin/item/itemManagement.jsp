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
									<th>No</th>
									<th>상품이미지</th>
									<th>카테고리</th>
									<th>상품명</th>
									<th>가격</th>
									<th>재고</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="itemDto" items="${itemDtos}">
							<tr>
								<td>${itemDto.itemId}</td>
								<td><img src="${itemDto.smallImg}"></td>
								<td>${itemDto.categoryName}</td>
								<td><a href="itemDetail.ad?itemId=${itemDto.itemId}&categoryId=${itemDto.categoryId}&pageNum=${pageNum}&number=${number}">${itemDto.itemName}</a></td>
								<td>￦<fmt:formatNumber value="${itemDto.price}" pattern="#,###"/></td>
								<td>${itemDto.stock}</td>
								<td>
									<input type="button" value="수정하기" class="little_btn"
										onclick="window.location='updateItem.ad?itemId=${itemDto.itemId}&categoryId=${itemDto.categoryId}&pageNum=${pageNum}'">
									<input type="button" value="삭제하기" class="little_btn"
										onclick="window.location='deleteItem.ad?itemId=${itemDto.itemId}&categoryId=${itemDto.categoryId}&pageNum=${pageNum}'">
								</td>		
							</tr>
							 </c:forEach>
							</tbody>
						</table>
						<!-- 페이지 컨트롤 -->
						<!-- 게시글 존재 여부 -->
						<div class="outer_content">	
							<c:if test="${cnt>0}">
								<!-- 처음[◀◀]/이전블록[◀]/ -->
								<c:if test="${startPage > pageBlock}">
									<a href="itemManagement.ad?">◀◀</a>
									<a href="itemManagement.ad?pageNum=${startPage - pageBlock}">◀</a>
								</c:if>
								<!-- 블럭 내의 페이지 번호 -->
								<c:forEach var="i" begin="${startPage}" end="${endPage}">
									<c:if test="${i==currentPage}">
										<span><b>[${i}]</b></span>
									</c:if>
									<c:if test="${i!=currentPage}">
										<a href="itemManagement.ad?pageNum=${i}">[${i}]</a>
									</c:if>
								</c:forEach>
								<!-- 다음블럭[▶]/ 마지막[▶▶] -->
								<c:if test="${pageCount > endPage}">
									<a href="itemManagement.ad?pageNum=${startPage + pageBlock}">[▶]</a>
									<a href="itemManagement.ad?pageNum=${pageCount}">[▶▶]</a>
								</c:if>
							</c:if>
						</div>	
						<br>
						<div class="outer_content">
							<select id="selectedCategory" onchange="displayByCategory()">
								<option value="0">전체보기</option>
								<c:forEach var="categoryMap" items="${categoryMap}">
									<option value="${categoryMap.key}" ${categoryMap.key==categoryId ? 'selected="selected"':''}>${categoryMap.value}</option>
								</c:forEach>
							</select>
						</div>
						<br>
						<div class="outer_content">
							<input type="button" value="상품추가" class="little_btn" onclick="window.location='addItem.ad'">
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