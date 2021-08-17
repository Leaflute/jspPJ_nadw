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
<h2><center>게시글 수정</center></h2>
<!-- input type="hidden" form 태그 안에서 작성 -->
<form action="boardModifyDetail.bo" method="post" name="pwForm">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<table align="center">
		<tr>
			<th colspan="2">
				비밀번호를 입력하세요.
			</th>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<input class="input" type="password" name="pw" maxlength="20" placeholder="비밀번호">
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input class="inputbutton" type="submit" value="확인">
				<input class="inputbutton" type="reset" value="취소"
					onclick="window.history.back();">
				
			</th>
		</tr>
	</table>
</form>
</body>
</html>