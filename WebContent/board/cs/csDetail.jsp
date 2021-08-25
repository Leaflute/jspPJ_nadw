<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
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
						<h1>My문의</h1>
						<table align="center">
							<tr>
								<th> 번호</th>
								<td align="center">${number}</td>
								<th>조회수</th>
								<td>${dto.hit}</td>
							</tr>
							<tr>
								<th>작성자</th>
								<td align="center">${dto.writer}</td>
								<th>작성일</th>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dto.regDate}"/></td>
							</tr>
							<tr>
								<th>제목 </th>
								<td colspan="3" align="center">${dto.title}</td>
							</tr>
							<tr>
								<th>내용 </th>
								<td colspan="3" word-break:break-all>
									${dto.content}
								</td>
							</tr>
						</table>	
						<div class="outer_content">
							<c:if test="${selectCnt==1}">
								<input class="little_btn" type="button" value="수정하기"
									onclick="window.location='csUpdate.bo?boardId=${boardId}&fullList=${fullList}&num=${dto.postNum}&pageNum=${pageNum}&number=${number}'">
								<input class="little_btn" type="button" value="삭제하기"
									onclick="window.location='csDelete.bo?boardId=${boardId}&fullList=${fullList}&num=${dto.postNum}&pageNum=${pageNum}'">
							</c:if>
							<c:if test="${sessionScope.member.role==1&&dto.refLevel>0}">
								<input class="little_btn" type="button" value="답글하기"
									onclick="window.location='csWrite.bo?boardId=${boardId}&fullList=${fullList}&num=${dto.postNum}&pageNum=${pageNum}&ref=${dto.ref}&refStep=${dto.refStep}&refLevel=${dto.refLevel}'">
							</c:if>
							<input class="little_btn" type="button" value="목록"
								onclick="window.location='csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${pageNum}'">
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