/**
 * 회원가입, 로그인
 */
var insertError = "회원가입에 실패하였습니다. \n확인 후 다시 시도하세요.";
var updateError = "정보수정을 실패하였습니다. \n확인 후 다시 시도하세요.";
var deleteError = "회원탈퇴를 실패하였습니다. \n확인 후 다시 시도하세요.";
var passwordError = "비밀번호가 일치하지 않습니다. \n확인 후 다시 시도하세요.";
var notExistMemError = "존재하지 않는 회원입니다. \n확인 후 다시 시도하세요.";

// 이메일 정규식 (알파벳or숫자@알파벳or숫자.알파벳2,3자리)
var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

// 비밀번호 정규식(최소 하나의 문자 및 하나의 숫자)
var pwRule = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;

// 이름 정규식 (입력 시작부터 입력 끝까지 한글만 2~6자까지 입력하는 패턴으로 정규표현 객체를 생성)
var nameRule = /^[가-힣]{2,6}$/;

// 폰번호 정규식 (첫 숫자는 010,011,017,018 중 하나로 시작하고, 다음 숫자는 3~4개까지 오고, 마지먁 숫자는 숫자 4개)
var phoneRule =/^(010|011|017|018)\d{3,4}\d{4}$/;


function errorAlert(errorMsg) {
	alert(errorMsg);
	window.history.back();	// 이전 페이지로 이동
}

// 회원가입 정보 입력 타당성 검사
function signInChk() {

	var iEmail = document.signInForm.email.value;
	var chkEmail = emailRule.test(iEmail);

	var iPw = document.signInForm.pw.value;
	var chkPw = pwRule.test(iPw);
	var iRePw = document.signInForm.rePw.value;
	
	var iName = document.signInForm.name.value;
	var chkName = nameRule.test(iName);
	
	var iPhone = document.signInForm.phone.value;
	var chkPhone = phoneRule.test(iPhone);
	
	if(!iEmail) {
		alert("이메일을 입력하세요.");
		document.signInForm.email.focus();
		return false;
		
	} else if (!chkEmail) {
		alert("올바른 이메일 형식이 아닙니다.");
		document.signInForm.email.focus();
		return false;
	
	} else if(!iPw) {
		alert("비밀번호를 입력하세요.");
		document.signInForm.pw.focus();
		return false;
		
	} else if (!chkPw) {
		alert("올바른 비밀번호 형식이 아닙니다.\n숫자와 영문자를 조합해 8~20자리를 입력하세요.");
		document.signInForm.pw.focus();
		return false;
		
	} else if(iPw!=iRePw) {
		alert("비밀번호가 일치하지 않습니다. 비밀번호는 영문 대소문자를 구분합니다.");
		document.signInForm.pw.focus();
		return false;
		
	} else if(!iName) {
		alert("이름을 입력하세요.");
		document.signInForm.name.focus();
		return false;
	} else if(!chkName) {
		alert("이름은 한글로 2~6자로만 입력할 수 있습니다.");
		document.signInForm.name.focus();
		return false;
		
	} else if(!iPhone) {
		alert("휴대폰 번호를 입력하세요.");
		document.signInForm.phone.focus();
		return false;
	} else if(!chkPhone) {
		alert("올바른 번호형식이 아닙니다.");
		document.signInForm.phone.focus();
		return false;	
	
	// 2. 중복확인 버튼을 클릭하지 않는 경우 "중복확인을 해주세요."
	// signIn.jsp - hiddenId : 중복확인 버튼 클릭여부 체크(0: 클릭x, 1:클릭)
	} else if (document.signInForm.hiddenEmail.value == 0) {
		alert("이메일 중복확인을 하세요.");
		document.signInForm.emailDupChk.focus();
		return false;
	}
}	

//회원가입 - 아이디 중복 확인 페이지 - confirmId()
//1. 중복확인 버튼 클릭 시 서브창 open
function confirmEmail() {
	var iEmail = document.signInForm.email.value;
	var chkEmail = emailRule.test(iEmail);
	
	if(!iEmail) {
		alert("이메일을 입력하세요.");
		document.signInForm.email.focus();
	} else if(!chkEmail) {
		alert("올바른 이메일 형식이 아닙니다.");
		document.signInForm.email.focus();
	} else {
		var url = "emailDupChk.co?email=" + document.signInForm.email.value;
		window.open(url, "confirmEmail", "menubar=no, width=500, height=300");
	}
}

// 이메일 적합도
function confirmEmailChk() {
	var iEmail = document.confirmForm.email.value;
	var chkEmail = emailRule.test(iEmail);
	
	if(!iEmail) {
		alert("이메일을 입력하세요.");
		document.confirmForm.email.focus();
		return false;
	} else if (!chkEmail) {
		alert("올바른 이메일 형식이 아닙니다.");
		document.confirmForm.email.focus();
		return false;
	}	
}

//4. 자식창에서 부모창으로 ID값 전달
function setEmail(email) {
	// opener : window 객체의 open() 메서드로 열린 새 창(=중복확인창()에서 부모창(=회원가입)에 접근할 때 사용
	// self.close() : 메시지 없이 현재창을 닫을 때 사용
	opener.document.signInForm.email.value = email;
	opener.document.signInForm.hiddenEmail.value = 1;	// 중복확인 완료
	self.close();
}

// 팝업 창 크기를 HTML 크기에 맞추어 자동으로 크기를 조정하는 함수
$(document).ready(function() {
    var strWidth;
    var strHeight;

    //innerWidth / innerHeight / outerWidth / outerHeight 지원 브라우저 
    if ( window.innerWidth && window.innerHeight && window.outerWidth && window.outerHeight ) {
        strWidth = $('.popup').outerWidth() + (window.outerWidth - window.innerWidth);
        strHeight = $('.popup').outerHeight() + (window.outerHeight - window.innerHeight);
    }
    else {
        var strDocumentWidth = $(document).outerWidth();
        var strDocumentHeight = $(document).outerHeight();

        window.resizeTo ( strDocumentWidth, strDocumentHeight );

        var strMenuWidth = strDocumentWidth - $(window).width();
        var strMenuHeight = strDocumentHeight - $(window).height();

        strWidth = $('.popup').outerWidth() + strMenuWidth;
        strHeight = $('.popup').outerHeight() + strMenuHeight;
    }

    //resize 
    window.resizeTo( strWidth, strHeight );

});

//회원정보 수정 입력 타당성 검사
function updateFormChk() {
	var iPw = document.updateForm.pw.value;
	var chkPw = pwRule.test(iPw);
	
	var iRePw = document.updateForm.rePw.value;
	
	var iName = document.updateForm.name.value;
	var chkName = nameRule.test(iName);
	
	var iPhone = document.updateForm.phone.value;
	var chkPhone = phoneRule.test(iPhone);
	
	if(!iEmail) {
		alert("이메일을 입력하세요.");
		document.updateForm.email.focus();
		return false;
		
	} else if (!chkEmail) {
		alert("올바른 이메일 형식이 아닙니다.");
		document.updateForm.email.focus();
		return false;
	
	} else if(!iPw) {
		alert("비밀번호를 입력하세요.");
		document.updateForm.pw.focus();
		return false;
		
	} else if (!chkPw) {
		alert("올바른 비밀번호 형식이 아닙니다.\n숫자와 영문자를 조합해 8~20자리를 입력하세요.");
		document.updateForm.pw.focus();
		return false;
		
	} else if(iPw!=iRePw) {
		alert("비밀번호가 일치하지 않습니다. 비밀번호는 영문 대소문자를 구분합니다.");
		document.updateForm.pw.focus();
		return false;
		
	} else if(!iName) {
		alert("이름을 입력하세요.");
		document.updateForm.name.focus();
		return false;
	} else if(!chkName) {
		alert("이름은 한글로 2~6자로만 입력할 수 있습니다.");
		document.updateForm.name.focus();
		return false;
		
	} else if(!iPhone) {
		alert("휴대폰 번호를 입력하세요.");
		document.updateForm.phone.focus();
		return false;
	} else if(!chkPhone) {
		alert("올바른 번호형식이 아닙니다.");
		document.updateForm.phone.focus();
		return false;	
	}
	
}

// 회원탈퇴 마지막 체크
function withrawFinalChk() {
	var confirmDelete = confirm("정말로 탈퇴하시겠습니까?")
	if (!confirmDelete) {
		return false;
	}
}