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
	<h2><center>글 수정 페이지</center></h2>
	<c:if test="${selectCnt==0}">
		<script type="text/javascript">
			errerAlert(passwordError);
		</script>
	</c:if>
	<c:if test="${selectCnt!=0}">
	<form action="boardModifyAction.bo" method="post" name="modifyForm">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<table align="center">
		<tr>
			<th colspan="2"> 수정할 정보를 입력하세요 </th>
		</tr>
		<tr>
			<th style="width:150px">작성자</th>
			<td>
				<input class="input" type="text" name="writer" maxlength="20"
					value="${dto.writer}" style="width:150px;" autofocus required>
			</td>
		</tr>
		<tr>
			<th style="width:150px">비밀번호</th>
			<td>
				<input class="input" type="password" name="pw" maxlength="20"
					value="${dto.pw}" style="width:150px;" required>
			</td>
		</tr>
		<tr>
			<th>제목 </th>
			<td>
				<input class="input" type="text" name="subject" maxlength="20"
					value="${dto.subject}" style="width:270px;">
			</td>
		</tr>
		<tr>
			<th>내용 </th>
			<td>
			<!-- word-break:break-all => 글자단위 자동 줄바꿈 - 권장 -->
			<!-- word-break:kep-all => 단어단위 자동 줄바꿈 -->
			<textarea class="input" rows="10" cols="50" name="content" align="left">
				${dto.content}
			</textarea>
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input class="button" type="submit" value="수정하기">
				<input class="button" type="reset" value="초기화">
				<input class="button" type="button" value="목록"
					onclick="window.history.back()">
			</th>
		</tr>
	</table>
	</form>	
	</c:if>
</body>
</html>