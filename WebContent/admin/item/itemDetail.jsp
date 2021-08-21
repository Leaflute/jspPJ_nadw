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
						<div class="title_letter">상품상세보기</div>
						<table align="center">
							<tr>
								<th>상품번호</th>
								<td align="center">${dto.itemId}</td>
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
								<td colspan="3" align="center">${dto.itemName}</td>
							</tr>
							<tr>
								<th>내용 </th>
								<td colspan="3" word-break:break-all>
									${dto.content}
								</td>
							</tr>
							<tr>
								<th colspan="4">
									<input class="button" type="button" value="수정하기"
										onclick="window.location='csUpdate.bo?num=${dto.itemId}&pageNum=${pageNum}&number=${number}'">
									<input class="button" type="button" value="삭제하기"
										onclick="window.location='csDelete.bo?num=${dto.itemId}'">
									<input class="button" type="button" value="목록"
										onclick="window.location='itemManagement.ad'">
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
</body>
</html>