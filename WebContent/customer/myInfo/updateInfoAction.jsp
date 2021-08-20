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
<c:if test="${updateCnt==1}">	
<%
	//비밀번호 변경 후에는 세션 초기화 필요(세션을 그대로 둘 시 변경 전 비밀번호로 로그인이 가능하기 때문)	
	request.getSession().invalidate();	
%>
	<script type="text/javascript">
		setTimeout(function() {
			alert("회원정보를 수정했습니다.");
			window.location="index.co";
		}, 1000);
	</script>
</c:if>	
<c:if test="${updateCnt!=1}">
	<script type="text/javascript">
		errorAlert(updateError);
	</script>
</c:if>	
</body>
</html>