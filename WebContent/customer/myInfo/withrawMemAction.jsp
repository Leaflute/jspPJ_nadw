<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 처리</title>
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
</head>
<body>
<form action="withrawMemAction.co" method="post">
<%@ include file="../../include/header.jsp" %>
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<%@ include file="../../include/nav.jsp" %>

			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="title_letter">MY정보관리</div>
						<c:if test="${selectCnt==1}">
							<c:if test="${deleteCnt==1}">
								<%-- 세션 초기화 --%> 
								<% request.getSession().invalidate(); %>
								<script type="text/javascript">
									setTimeout(function() {
										alert("회원 탈퇴 처리되었습니다.");
										window.location="index.co";
									}, 1000);	// 1초 후에 main.do로 이동
								</script>
							</c:if>
							
							<c:if test="${deleteCnt!=1}">
								<script type="text/javascript">
									errorAlert(deleteError);
								</script>
							</c:if>	
						</c:if>
						
						<c:if test="${selectCnt!=1}">
							<script type="text/javascript">
								errorAlert(passwordError);
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
</form>	
</body>
</html>