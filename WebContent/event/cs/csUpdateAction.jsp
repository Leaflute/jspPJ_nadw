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
						<c:if test="${updateCnt==0}">
							<script type="text/javascript">
								errorAlert(updateError);
							</script>
						</c:if>
						<c:if test="${updateCnt!=0}">
							<script type="text/javascript">
								alert("게시글을 수정했습니다.");
								window.location='csList.bo?boardId=${boardId}&fullList=${fullList}&pageNum=${pageNum}';
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