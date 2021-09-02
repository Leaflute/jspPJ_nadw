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
	<c:if test="${insertCnt==0}">
		<script type="text/javascript">
			alert('구매요청에 실패했습니다. 확인 후 다시 시도해주세요.');
			window.location='index.co';
		</script>
	</c:if>	
	<c:if test="${insertCnt!=0}">
		<script type="text/javascript">
			alert('구매를 확정했습니다.');
			window.location='index.co';
		</script>	
	</c:if>
</body>
</html>