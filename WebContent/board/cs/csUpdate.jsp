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
<title>my문의</title>
</head>
<body>
<form action="csUpdateAction.bo" method="post" name="modifyForm">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<input type="hidden" name="boardId" value="${boardId}">
	<input type="hidden" name="fullList" value="${fullList}">
	<input type="hidden" name="number" value="${number}">
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
						<table>
							<tr>
								<th colspan="2"> 수정할 정보를 입력하세요 </th>
							</tr>
							<tr>
								<th>제목 </th>
								<td>
									<input class="input" type="text" name="title" maxlength="20"
										value="${dto.title}" style="width:270px;">
								</td>
							</tr>
							<tr>
								<th>내용 </th>
								<td>
								<textarea class="input" rows="10" cols="50" name="content" align="left">
									${dto.content}
								</textarea>
								</td>
							</tr>
							<tr>
								<th colspan="2">
									<input class="little_btn" type="submit" value="수정하기">
									<input class="little_btn" type="reset" value="초기화">
									<input class="little_btn" type="button" value="목록"
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