<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<link rel="stylesheet" type="text/css" href="${cssPath}item_category.css">
<script type="text/javascript" src="${jsPath}item.js"></script>
<meta charset="UTF-8">
<title>장바구니 이동 확인</title>
</head>
<body>
<form action="cartList.cu" method="post" onsubmit="opener.location.href='cartList.cu'">
<table>
	<tr><th>장바구니로 이동하시겠습니까?</th></tr>
	<tr>
		<th><input type="submit" value="확인" class="little_btn" onclick="self.close()">
		<input type="button" value="취소" class="little_btn" onclick="self.close()"></th>
	</tr>
</table>
</form>
</body>
</html>