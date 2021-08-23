<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="${jsPath}jquery-3.6.0.min.js" type="text/javascript"></script>
<script src="${jsPath}item.js"></script>
<title>상품 삭제요청</title>
</head>
<body>
<form action="deleteItemAction.ad" method="post" name="deleteitemform">
	<input type="hidden" name="itemId" value="${dto.itemId}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<input type="hidden" name="categoryId" value="${dto.categoryId}">
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
						<h2>상품 삭제</h2>
						<!-- input type="hidden" form 태그 안에서 작성 -->
						<table>
							<tr>
								<th>No</th><th>이름</th>
							</tr>
							<tr>
								<td>${dto.itemId}</td><td>${dto.itemName}</td>
							</tr>
						</table>
						<br>	
						<div class="outer_content">
							정말로 삭제하시겠습니까?
						</div>
						<br><br>
						<div class="outer_content">
							<input class="little_btn" type="submit" value="확인">
							<input class="little_btn" type="reset" value="취소"
								onclick="window.location='itemDetail.ad?itemId=${dto.itemId}&categoryId=${categoryId}&pageNum=${pageNum}'">
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