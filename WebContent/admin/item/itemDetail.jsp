<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script type="text/javascript" src="${jsPath}item.js"></script>
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
						<div class="title_letter">상품상세보기</div>
						<table align="center">
							<tr>
								<th>상품번호</th>
								<td align="center">${dto.itemId}</td>
								<th>제조사</th>
								<td>${dto.company}</td>
							</tr>
							<tr>
								<th>카테고리</th>
								<td align="center">${dto.categoryName}</td>
								<th>등록일</th>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dto.regDate}"/></td>
							</tr>
							<tr>
								<th>상품명</th>
								<td colspan="3" align="center">${dto.itemName}</td>
							</tr>
							<tr>
								<th>상품정보</th>
								<td colspan="3" align="left" style="word-break:break-all">
									${dto.info} 
								</td>
							</tr>
							<tr>
								<th>작은 이미지</th>
								<td colspan="3"><img src="${dto.smallImg}"></td>
							</tr>
							<tr>
								<th>큰 이미지</th>
								<td colspan="3"><img src="${dto.largeImg}"></td>
							</tr>
							<tr>
								<th>상품 설명 이미지</th>
								<td colspan="3" ><img src="${dto.detailImg}"></td>
							</tr>
							<tr>
								<th>상품원가</th><td>￦<fmt:formatNumber value="${dto.cost}" pattern="#,###"/></td>
								<th>판매가격</th><td>￦<fmt:formatNumber value="${dto.price}" pattern="#,###"/></td>
							</tr>
							<tr>
								<th>재고</th><td>${dto.stock}</td>
								<th>평점</th>
								<td colspan="3">${dto.grade}</td>
							<tr>
						</table>	
						<div class="outer_content">
							<input class="little_btn" type="button" value="수정하기"
								onclick="window.location='updateItem.ad?itemId=${dto.itemId}&categoryId=${dto.categoryId}&pageNum=${pageNum}'">
							<input class="little_btn" type="button" value="삭제하기"
								onclick="window.location='deleteItem.ad?itemId=${dto.itemId}&categoryId=${dto.categoryId}&pageNum=${pageNum}'">
							<input class="little_btn" type="button" value="목록"
								onclick="window.location='itemManagement.ad?categoryId=${dto.categoryId}&pageNum=${pageNum}&number=${number}'">
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
</body>
</html>