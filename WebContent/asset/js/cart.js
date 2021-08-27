/**
 * 상품 javaScript
 */

var insertError = "장바구니 추가에 실패하였습니다. \n확인 후 다시 시도하세요.";
var updateError = "수량 수정을 실패하였습니다. \n확인 후 다시 시도하세요.";
var deleteError = "장바구니 삭제를 실패하였습니다. \n확인 후 다시 시도하세요.";

$(function(){
	$("#chkAll").change(function(){
		var isChecked = $("#chkAll").is(":checked");
		$("#cartcol :checkbox").prop("checked", isChecked);
	});
});
