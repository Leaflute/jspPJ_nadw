<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../asset/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 글 쓰기 실패 -->
<c:if test="${insertCnt==0}">
	<script type="text/javascript">
		errorAlert(insertError);
	</script>
</c:if>
<c:if test="${insertCnt!=0}">
	<script type="text/javascript">
		alert("게시글을 작성했습니다.");
		window.location='boardList.bo?pageNum=${pageNum}';
	</script>
</c:if>	
</body>
</html>