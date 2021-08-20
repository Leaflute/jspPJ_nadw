<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>     
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
	<c:if test="${sessionScope.sessionRole==1}">
		<script type="text/javascript">
			alert("관리자 로그인에 성공했습니다.");
			window.location="dashboardMain.ad";
		</script>
	</c:if>
	<c:if test="${sessionScope.sessionRole==0}">
		<script type="text/javascript">
			alert("회원 로그인에 성공했습니다.")
			window.location="index.co";
		</script>
	</c:if>
</c:if>

</body>
</html>