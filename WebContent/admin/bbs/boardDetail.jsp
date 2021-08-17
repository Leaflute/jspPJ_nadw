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
	<h2><center>상세페이지</center></h2>
	<table align="center">
		<tr>
			<th style="width:150px">글 번호</th>
			<td style="width:150px" align="center">${number}</td>
			<th style="width:150px">조회수</th>
			<td style="width:150px">${dto.readCount}</td>>
		</tr>
		<tr>
			<th style="width:150px">작성자</th>
			<td style="width:150px" align="center">${dto.writer}</td>
			<th style="width:150px">작성일</th>
			<td style="width:150px"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dto.regDate}"/></td>
		</tr>
		<tr>
			<th>제목 </th>
			<td colspan="3" align="center">${dto.subject}</td>
		</tr>
		<tr>
			<th>내용 </th>
			<td colspan="3" style="width:200px" word-break:break-all>
			${dto.content}
			<!-- word-break:break-all => 글자단위 자동 줄바꿈 - 권장 -->
			<!-- word-break:kep-all => 단어단위 자동 줄바꿈 -->
			</td>
		</tr>
		<tr>
			<th colspan="4">
				<input class="button" type="button" value="수정하기"
					onclick="window.location='boardModify.bo?num=${dto.num}&pageNum=${pageNum}'">
				<input class="button" type="button" value="삭제하기"
					onclick="window.location='boardDelete.bo?num=${dto.num}&pageNum=${pageNum}'">
				<input class="button" type="button" value="답글하기"
					onclick="window.location='boardWrite.bo?num=${dto.num}&pageNum=${pageNum}&ref=${dto.ref}&refStep=${dto.refStep}&refLevel=${dto.refLevel}'">
				<input class="button" type="button" value="목록"
					onclick="window.location='boardList.bo?pageNum=${pageNum}'">
			</th>
		</tr>
	</table>
</body>
</html>