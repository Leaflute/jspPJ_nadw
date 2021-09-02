<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${jsPath}address.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr += ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("detail").value = extraAddr;
                
                } else {
                    document.getElementById("detail").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                document.getElementById("main").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detail").focus();
            }
        }).open();
    }

	function conditionChange() {
		if($("#condition").is(":checked")){
			$("#condition").val(1);
		} else {
			$("#condition").val(0);
		}
	}
</script>
<title>배송지 추가</title>
</head>
<body>
<form action="addAddressAction.cu" method="post">
<%@ include file="../../include/header.jsp" %> 
<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<%@ include file="../../include/nav.jsp" %> 
			
			<!-- section -->
			<section class="mem_content">
			<div class="outer_content">
				<div class="inner_content">
					<div class="title_letter">주소록 추가</div>
						<table>
							<tr>
								<th>받는 사람*</th>
								<td>
									<input type="text" name="recipient" placeholder="받는 사람">
								</td>
							</tr>
							<tr>
								<th>연락처*</th>
								<td>
									<input type="text" name="tel" placeholder=" 연락처">
								</td>
							</tr>
							<tr>
								<th><label id="address">우편번호*</label></th>
								<td>
									<input type="text" name="zipcode" id="zipcode" placeholder=" 우편번호" size=6>
									<input type="button" onclick="execDaumPostcode();" value=" 주소찾기">
								</td>
							</tr>
							<tr>
								<th>주소*</th>
								<td>
									<input type="text" name="main" id="main" placeholder=" 주소">
								</td>
							</tr>
							<tr>	
								<th>상세주소*</th>
								<td>
									<input type="text" name="detail" id="detail" placeholder=" 상세주소" size=30>
								</td>
							</tr>
							<tr>
								<th colspan="2"><input type="checkbox" id="condition" name="condition" value="0"  onchange="conditionChange()">기본 주소지 등록</th>
							</tr>
						</table>
						<br>
						<div class="outer_content">
							<input type="submit" value="배송지 추가" class="little_btn">
							<input type="button" value="돌아가기" class="little_btn">
						</div>
					</div>
				</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../../include/footer.jsp" %>
</form>									
</body>
</html>