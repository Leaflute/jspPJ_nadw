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
<title>수정 요청</title>
</head>
<body>
<c:if test="${insertCnt==0}">
	<script type="text/javascript">
		alert('주문 상태 수정에 실패했습니다.');
		window.location='orderList.ad';
	</script>
</c:if>
<c:if test="${insertCnt!=0}">
	<script type="text/javascript">
		alert('주문 상태를 수정했습니다');
		window.location='orderList.ad';
	</script>
</c:if>
</body>
</html>