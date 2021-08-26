<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<title>Leafcom 이메일 인증 처리 완료 페이지</title>
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<script src="${jsPath}member.js" type="text/javascript"></script>
</head>
<body>

<%@ include file="../../include/header.jsp" %>

<!-- article 시작 -->
<article class="container">
	<section class="wrapper">
		<div id="my_page_box">
			<%@ include file="../../include/nav.jsp" %>
			
			<c:if test="${selectCnt==0}">
				<script type="text/javascript">
					alert("인증 키 값이 일치하지 않습니다. 초기 페이지로 돌아갑니다.");
					window.location='index.co';
				</script>
			</c:if>
			<c:if test="${selectCnt!=0}">
				<c:if test="${updateCnt==0}">
					<script type="text/javascript">
						alert("권한이 활성화되지 않았습니다. 이메일을 다시 확인해주세요.");
						window.location='emailChk.co';
					</script>
				</c:if>
				<c:if test="${updateCnt!=0}">
					<c:if test="${empty sessionScope.member}">
						<script type="text/javascript">
							alert("이메일 인증을 완료했습니다. 로그인 해주세요.");
							window.location='login.co';
						</script>
					</c:if>
					<c:if test="${not empty sessionScope.member}">
						<script type="text/javascript">
							alert("이메일 인증을 완료했습니다.");
							window.location='index.co';
						</script>
					</c:if>
				</c:if>		
			</c:if>
		</div>
	</section>
</article>	

<%@ include file="../../include/footer.jsp" %>
	
</body>
</html>