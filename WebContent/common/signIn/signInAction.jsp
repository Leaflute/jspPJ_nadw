<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입처리</title>
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<!-- request.getAttribute("insertCnt") -->
<c:if test="${insertCnt==0}">
	<script type="text/javascript">
		errorAlert(insertError);
	</script>
</c:if>

<c:if test="${insertCnt==1}">
	<script type="text/javascript">
		alert("회원가입을 축하합니다! 이메일 인증완료 하세요.");
		window.location="index.co";
	</script>
</c:if>
</body>
</html>