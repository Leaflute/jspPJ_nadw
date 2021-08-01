<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}member.css">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<title>이메일 확인</title>
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<article class="container">
<section class="wrap_inner">
<div class="DupChkPop">
<form action="confirmEmail.me" method="post" name="confirmForm" onsubmit="return confirmEmailChk();">
<c:if test="${selectCnt==1}">
	<table>
		<tr class="cell_white">
			<th colspan="2">
				<span>${email}</span>는 사용할 수 없습니다.
			</th>
		</tr>
		<tr>
			<th class="cell_white"> 다른 이메일 주소 입력 </th>
		</tr>
		<tr>	
			<td>
				<input class="input" type="text" name="email" maxlength="40"
					style="width:200px" autofocus required>
			</td>
		</tr>
		
		<tr>
			<th colspan="2" class="cell_white">
				<input class="btn_green" type="submit" value="확인">
				<input class="btn_green" type="reset" value="돌아가기" onclick="self.close();">
			</th>
		</tr>
	</table>
</c:if>		
<c:if test="${selectCnt!=1}">
	<table>
		<tr class="cell_white">
			<th>
				<span>${email}</span>는 사용가능한 이메일입니다.
			</th>
		</tr>
		<tr align="center">
			<td class="cell_white">
				<input class="btn_green" type="button" value="확인" onclick="setEmail('${email}');">
			</td>
		</tr>
	</table>
</c:if>
</form>		
</div>
</section>
</article>
</body>
</html>