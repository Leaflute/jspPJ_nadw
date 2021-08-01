<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/setting.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입처리</title>
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<h3>회원가입 처리 페이지</h3>
<!-- request.getAttribute("insertCnt") -->
<c:if test="${insertCnt==0}">
	<script type="text/javascript">
		errorAlert(insertError);
	</script>
</c:if>

<c:if test="${insertCnt==1}">
	<script type="text/javascript">
		alert("회원가입을 축하합니다! 로그인 해주세요.");
	</script>
	<c:redirect url="login.me" />
</c:if>
</body>
</html>