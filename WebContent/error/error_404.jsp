<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ include file="../../include/setting.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 ERROR</title>
</head>
<body>
<%@ include file="../../include/header.jsp" %>
<article class="container">
<!-- 컨테이너 -->
	<div class="wrapper">
		<!-- section -->
		<section class="mem_content">
			<div class="outer_content">
				<div class="inner_content">
					<img src="${imgPath}404.jpg">
				</div>
			</div>
		</section>
		<!-- section 종료 -->
	</div>
</article>
<!-- article 끝 -->				
<%@ include file="../../include/footer.jsp" %>
</body>
</html>