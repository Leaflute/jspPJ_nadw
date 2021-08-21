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
<title>DashBoard</title>
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
									<th><input type="checkbox" name="allSelectChk"></th>
									<th>상품번호</th>
									<th>상품이미지</th>
									<th>상품명</th>
									<th>가격</th>
									<th>재고</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="dto" items="${dtos}">
							<tr>
								<td><input type="checkbox" name="selectChk"></td>
								<td><a href="">{dto.itemId}</a></td>
								<td><a href="">{dto.smallImg}</a></td>
								<td>{dto.categoryName}</td>
								<td><a href="">{dto.itemName}</a></td>
								<td>{dto.price}</td>
								<td>{dto.quantity}</td>
								<td><a href=""><input type="button" value="수정하기" class="little_btn"></a>
							</tr>
							 </c:forEach>
							</tbody>
							<!-- 페이지 컨트롤 -->
							<tr>
								<th colspan="6">
									<!-- 게시글 존재 여부 -->
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
												<a href="csList.bo?pageNum=${i}">[${i}]</a>
											</c:if>
										</c:forEach>
										<!-- 다음블럭[▶]/ 마지막[▶▶] -->
										<c:if test="${pageCount > endPage}">
											<a href="csList.bo?pageNum=${startPage + pageBlock}">[▶]</a>
											<a href="csList.bo?pageNum=${pageCount}">[▶▶]</a>
										</c:if>
									</c:if>
								</th>
							</tr>
						</table>
						<div class="outer_content">
							<select name="category" onchange="window.location='itemManagement.ad?categoryId=${category.categoryId}'">
								<c:forEach var="category" items="${dtos}">
									<option id="selected" value="${category.categoryId}">${category.categoryId}</option>
								</c:forEach>
							</select>
						</div>
						<div class="outer_content">
							<input type="button" value="상품추가" class="little_btn" onclick="window.location='addItem.ad'">
							<input type="button" value="선택제거" class="little_btn">
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