<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 처리</title>
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<c:if test="${empty sessionScope.sessionID}">
	<c:if test="${selectCnt==0}">
		<script type="text/javascript">
			errorAlert(notExistMemError);
		</script>
	</c:if>
	<c:if test="${selectCnt==-1}">
		<script type="text/javascript">
			errorAlert(passwordError);
		</script>
	</c:if>		
</c:if>
<c:if test="${not empty sessionScope.sessionID}">
	<c:if test="${sessionScope.isAdmin==1}">
		<c:redirect url="main.ad"></c:redirect>
	</c:if>
	<c:if test="${sessionScope.isAdmin==0}">
		<c:redirect url="index.me"></c:redirect>
	</c:if>
</c:if>

</body>
</html>