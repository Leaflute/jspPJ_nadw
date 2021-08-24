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
<title>게시글 삭제요청</title>
</head>
<body>
<form action="csDeleteAction.bo" method="post" name="pwForm">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<input type="hidden" name="boardId" value="${boardId}">
	<input type="hidden" name="fullList" value="${fullList}">
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
						<h2>게시글 삭제</h2>
						<!-- input type="hidden" form 태그 안에서 작성 -->
						<table>
							<tr>
								<th colspan="2">
									정말로 삭제하시겠습니까?
								</th>
							</tr>
							<tr>
								<th colspan="2">
									<input class="little_btn" type="submit" value="확인">
									<input class="little_btn" type="reset" value="취소"
										onclick="window.location='csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${pageNum}'">
								</th>
							</tr>
						</table>
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