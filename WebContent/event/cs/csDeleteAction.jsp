<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삭제 처리</title>
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

					<!-- 삭제 성공 -->
					<c:if test="${deleteCnt==1}">
						<script type="text/javascript">
							alert("게시글을 삭제했습니다.");
							window.location='csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${pageNum}';
						</script>
					</c:if>
					<!-- 삭제 실패 -->
					<c:if test="${deleteCnt!=1}">
						<script type="text/javascript">
							errorAlert(deleteError);
						</script>
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