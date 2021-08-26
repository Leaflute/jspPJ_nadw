/**
 * 상품 javaScript
 */

var insertError = "상품등록에 실패하였습니다. \n확인 후 다시 시도하세요.";
var updateError = "상품수정을 실패하였습니다. \n확인 후 다시 시도하세요.";
var deleteError = "상품삭제를 실패하였습니다. \n확인 후 다시 시도하세요.";

var numberRule = /^(0|[-]?[1-9]\d*)$/;

// 에러메세지 + 리다이렉트
function errorAlert(errorMsg) {
	alert(errorMsg);
	window.history.back();	// 이전 페이지로 이동
}

// 아이템 입력 체크
function addItemChk() {
	var iQuantity = document.additemform.quantity.value;
	var iCost = document.additemform.cost.value;
	var iPrice = document.additemform.price.value;
	
	var chkQuantity = numberRule.test(iQuantity);
	var chkCost = numberRule.test(iCost);
	var chkPrice = numberRule.test(iPrice);
	
	if(!iQuantity) {
		alert("수량을 입력하세요");
		document.additemform.quantity.focus();
		return false;
		
	} else if (!chkQuantity) {
		alert("잘못된 숫자 입력입니다.");
		document.additemform.quantity.focus();
		return false;
	
	} else if(!iCost) {
		alert("상품 원가를 입력하세요");
		document.additemform.cost.focus();
		return false;
		
	} else if (!chkCost) {
		alert("잘못된 숫자 입력입니다.");
		document.additemform.cost.focus();
		return false;
		
	} else if(!iPrice) {
		alert("판매 가격을 입력하세요.");
		document.additemform.price.focus();
		return false;
		
	} else if(!chkPrice) {
		alert("잘못된 숫자 입력입니다.");
		document.additemform.price.focus();
		return false;
	}	
}

// 상품카테고리 변환으로 url리다이렉트
function displayByCategory() {
	var categoryId = document.getElementById("selectedCategory");
	console.log(categoryId.value);
	window.location="itemManagement.ad?categoryId="+categoryId.value;
}

// 카트 추가후 장바구니 확인창 로드
function cartPop() {
	var iItemId = document.getElementById("itemId").value;
	var iAmount = document.getElementById("amount").value;
	var url = "cartPop.cu?amount=" + iAmount + "&itemId=" + iItemId;
	window.open(url, "cartPop", "menubar=no, width=500, height=300");	
}
