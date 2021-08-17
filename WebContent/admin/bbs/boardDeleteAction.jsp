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
<!-- 비밀번호 일치 -->
<c:if test="${selectCnt==1}">
	<!-- 삭제 성공 -->
	<c:if test="${deleteCnt==1}">
		<script type="text/javascript">
			alert("게시글을 삭제했습니다.");
			window.location='boardList.bo?pageNum=${pageNum}';
		</script>
	</c:if>
	<!-- 삭제 실패 -->
	<c:if test="${deleteCnt!=1}">
		<script type="text/javascript">
			errorAlert(deleteError);
		</script>
	</c:if>
</c:if>
<!-- 비밀번호 불일치 -->
<c:if test="${selectCnt!=1}">
	<script type="text/javascript">
		errorAlert(passwordError);
	</script>	
</c:if>
</body>
</html>