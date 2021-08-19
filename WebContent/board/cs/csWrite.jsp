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
<h2><center>글쓰기</center></h2>
	<form action="boardWriteAction.bo" method="post" name="modifyForm">
	<!-- input type="hidden"은 form 태그 안에 지정 -->
	<!-- 
		글 작성 클릭 => boardWrite.bo?pageNum=1
		답변글 클릭 => boardWrite.bo?num=${dto.num}&pageNum=${pageNum}&ref=${dto.ref}&refStep=${dto.refStep}&refLevel=${dto.refLevel}
	 -->
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="pageNum" value="${pageNum}">
	<input type="hidden" name="ref" value="${ref}">
	<input type="hidden" name="refStep" value="${refStep}">
	<input type="hidden" name="refLevel" value="${refLevel}">
	<table align="center">
		<tr>
			<th colspan="2"> 게시글을 작성하세요. </th>
		</tr>
		<tr>
			<th style="width:150px">작성자</th>
			<td>
				<input class="input" type="text" name="writer" maxlength="20"
					placeholder="작성자 이름" style="width:150px;" autofocus required>
			</td>
		</tr>
		<tr>
			<th style="width:150px">비밀번호</th>
			<td>
				<input class="input" type="password" name="pw" maxlength="20"
					placeholder="비밀번호" style="width:150px;" required>
			</td>
		</tr>		
		<tr>
			<th>제목 </th>
			<td>
				<input class="input" type="text" name="subject" maxlength="20"
					placeholder="게시글 제목" style="width:300px;">
			</td>
		</tr>
		<tr>
			<th>내용 </th>
			<td>
			<!-- word-break:break-all => 글자단위 자동 줄바꿈 - 권장 -->
			<!-- word-break:kep-all => 단어단위 자동 줄바꿈 -->
			<textarea class="input" rows="10" cols="50" name="content"
				placeholer="내용" word-break:break-all>
			</textarea>
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<input class="button" type="submit" value="글 쓰기">
				<input class="button" type="reset" value="초기화">
				<input class="button" type="button" value="목록"
					onclick="window.location='boardList.bo?pageNum=${pageNum}'">
			</th>
		</tr>
	</table>
</body>
</html>