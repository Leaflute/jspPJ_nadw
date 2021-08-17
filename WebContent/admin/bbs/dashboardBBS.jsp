<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../asset/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<title>DashBoard</title>
</head>
<body>
<%@ include file="../dashboardHeader.jsp" %>
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<%@ include file="../dashboardNav.jsp" %>	
			
			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="title_letter">고객문의관리</div>
						<table>
							<thead>
								<tr>
									<th>문의번호</th>
									<th>문의범주</th>
									<th>문의제목</th>
									<th>문의상태</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>상품문의</td>
									<td><a href="">AMD여신 빛사수님 찬양</a>
									<td>답변 중</td>
									<td>
										<input type="button" value="답변하기" class="little_btn">
										<br>
										<input type="button" value="삭제하기" class="little_btn">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="outer_content">
					<div class="inner_content">
						<h1>DASHBOARD홈</h1>
					</div>
				</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../../common/footer.jsp" %>

</body>
</html>