<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객문의 작성</title>
</head>
<body>
<form action="csWriteAction.bo" method="post" name="modifyForm">
	<input type="hidden" name="boardId" value="${boardId}">
	<input type="hidden" name="fullList" value="${fullList}">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<input type="hidden" name="ref" value="${ref}">
	<input type="hidden" name="refStep" value="${refStep}">
	<input type="hidden" name="refLevel" value="${refLevel}">
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
						<table class="input_table">
							<tr>
								<th colspan="2" style="text-align:center"> 문의작성 </th>
							</tr>
							<tr>
								<th>제목 </th>
								<td>
									<input class="input" type="text" name="title" maxlength="20"
										placeholder="게시글 제목" style="width:300px;">
								</td>
							</tr>
							<tr>
								<th>내용 </th>
								<td>
								<textarea class="input" rows="10" cols="50" name="content"
									placeholer="내용" word-break:break-all>
								</textarea>
								</td>
							</tr>
						</table>
						<div class="outer_content">
							<input class="little_btn" type="submit" value="글 쓰기">
							<input class="little_btn" type="reset" value="초기화">
							<input class="little_btn" type="button" value="목록"
								onclick="window.location='csList.bo?pageNum=${pageNum}'">
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