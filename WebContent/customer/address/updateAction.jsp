<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${jsPath}cart.js"></script>
<title>주소록 수정 요청</title>
</head>
<body>
<c:if test="${insertCnt==0}">
	<script type="text/javascript">
		alert('주소록 수정에 실패했습니다.');
		window.location='addressList.cu';
	</script>
</c:if>
<c:if test="${insertCnt!=0}">
	<script type="text/javascript">
		alert('주소록을  수정했습니다');
		window.location='addressList.cu';
	</script>
</c:if>
</body>
</html>