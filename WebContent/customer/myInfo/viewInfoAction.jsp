<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="${jsPath}jquery-3.6.0.min.js" type="text/javascript"></script>
<script src="${jsPath}member.js" type="text/javascript"></script>
<title>My page</title>
</head>
<body>
<%@ include file="../../include/header.jsp" %>

<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<%@ include file="../../include/nav.jsp" %>
			
			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="title_letter">MY정보관리</div>
						<form action="updateMemInfoAction.co" method="post" name="updateForm" onsubmit="return updateFormChk();">
						<table>
							<c:if test="${selectCnt==1}">
								<tr>
									<th class="icon" ><i class="far fa-envelope"></i></th>
									<td align="left">
										${vo.email}
									</td>	
								</tr>
								<tr>
									<th class="icon"><i class="fas fa-key"></i></th>
									<td align="left"><input type="password" name="pw" size="40" placeholder="비밀번호"></td>
								</tr>
								
								<tr>
									<th class="icon"><i class="fas fa-key"></i></th>
									<td align="left"><input type="password" name="rePw" size="40" placeholder="비밀번호 확인"></td>
								</tr>
								<tr>
									<th class="icon"><i class="far fa-id-badge"></i></th>
									<td><input type="text" name="name" value="${vo.name}" size="40" placeholder="이름"></td>
								</tr>
								<tr>
									<th class="icon"><i class="fas fa-mobile-alt"></i></th>
									<td align="left">
										<input type="text" name="phone" value="${vo.phone}" size="11" placeholder="휴대폰 번호">
										<input type="button" value="인증" class="little_btn">
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<input type="checkbox" name="consent_to_receive"> SMS 및 이메일 수신 동의
									</td>
								<tr>
									<td colspan="2" align="center" class="cell_green">
									<input type="submit" value="정보수정" class="little_btn">
									<input type="button" value="탈퇴하기" class="little_btn" onclick="window.location='withrawMem.co'">
									<input type="button" value="돌아가기" class="little_btn" onclick="window.location='myPageMain.co'">
									</td>
								</tr>
							</c:if>
							<c:if test="${selectCnt!=1}">
								<tr>
									<th>비밀번호가 틀렸습니다. 확인해주세요.</th>
								</tr>
								<tr>
									<td colspan="2" align="center" class="cell_white">
									<input type="button" value="돌아가기" class="little_btn" onclick="window.location='myPageMain.co'">
									</td>
								</tr>
							</c:if>	
						</table>
						</form>
						</div>
					</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../../include/footer.jsp" %>

</body>
</html>