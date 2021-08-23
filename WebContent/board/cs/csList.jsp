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
					<h1>고객문의</h1>
						<table>
							<tr>
								<th colspan="6" style="height:25px">
									문의 목록(게시물 수:${cnt})
								</th>
							</tr>
							<tr>
								<th>No</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<c:if test="${sessionScope.member.role==1}">
									<th>IP</th>
								</c:if>
							</tr>
							<c:if test="${cnt>0}">
								<%-- <c:forEach var="작은바구니 참조변수" items="${ParamName}"> --%>
								<c:forEach var="dto" items="${dtos}">
									<tr>
										<td>${number}
											<c:set var="number" value="${number-1}"/>
										</td>
										<td>
										<!-- 답글 -->
										<!-- 답글인 경우: 들여쓰기 > 1 -->
										<c:if test="${dto.refLevel > 1}">
											<c:set var="wid" value="${(dto.refLevel-1)*10}"/>
											<img src="${iPath}level.gif" border="0" width="${wid}" height="15">
										</c:if>
										
										<!-- 답글인 경우: 들여쓰기 > 0 -->
										<c:if test="${dto.refLevel > 0}">
											<c:set var="wid" value="${(dto.refLevel-1)*10}"/>
											<img src="${iPath}re.gif" border="0" width="20" height="15">
										</c:if>
										
										<!-- 게시글 내용 보기 -->
										<a href="csDetail.bo?boardId=${boardId}&fullList=${fullList}&num=${dto.postNum}&pageNum=${pageNum}&number=${number+1}">${dto.title}</a></td>
										<td>${dto.writer}</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dto.regDate}"/></td>
										<c:if test="${sessionScope.member.role==1}">
											<td>${dto.ip}</td>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${cnt==0}">
								내  고객문의가 존재하지 않습니다.
							</c:if>
						</table>	
						<!-- 페이지 컨트롤 -->
						<div class="outer_content">
						<!-- 게시글 존재 여부 -->
						<c:if test="${cnt>0}">
							<!-- 처음[◀◀]/이전블록[◀]/ -->
							<c:if test="${startPage > pageBlock}">
								<a href="csList.bo?boardId=${boardId}&fullList=${fullList}">◀◀</a>
								<a href="csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${startPage - pageBlock}">◀</a>
							</c:if>
							<!-- 블럭 내의 페이지 번호 -->
							<c:forEach var="i" begin="${startPage}" end="${endPage}">
								<c:if test="${i==currentPage}">
									<span><b>[${i}]</b></span>
								</c:if>
								<c:if test="${i!=currentPage}">
									<a href="csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${i}">[${i}]</a>
								</c:if>
							</c:forEach>
							<!-- 다음블럭[▶]/ 마지막[▶▶] -->
							<c:if test="${pageCount > endPage}">
								<a href="csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${startPage + pageBlock}">[▶]</a>
								<a href="csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${pageCount}">[▶▶]</a>
							</c:if>
						</c:if>
						<br><br>
						</div>
						<c:if test="${sessionScope.member.role!=1}">
						<div class="outer_content"> 
							<a href="csWrite.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${pageNum}">
								<input type="button" value="문의하기" class="little_btn">
							</a>
						</div>
						</c:if>	
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