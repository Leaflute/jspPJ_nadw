package leafcom.util;

public interface Code {
	// 관리자 정보
	public final static String ACTIVATION_HELPER = "leafcom.activation.helper@gmail.com";
	public final static String PW = "!Q@W#E$R1";
	
	// 회원 롤 상수코드
	public final static int GUEST = 0;	// 일반 회원 
	public final static int HOST = 1;	// 관리자
	
	// 회원상태 상수코드
	public final static int NOT_ACTIVATED = 0;	// 이메일 미인증
	public final static int NORMAL = 1;		// 정상 회원
	public final static int REST = 2;		// 휴면 회원
	public final static int WITHRAWL = 3;	// 탈퇴 회원

	// 카테고리 상수코드
	public final static int CPU = 1;	// CPU
	public final static int RAM = 2;	// RAM
	public final static int MBD = 3;	// 메인보드
	public final static int GPU = 4;	// 그래픽카드
	public final static int PWS = 5;	// 파워 서플라이
	public final static int SSD = 6;	// SSD
	public final static int HDD = 7;	// HDD
	public final static int CSE = 8;	// 케이스
	public final static int MNT = 9;	// 모니터
	
	// 게시판 아이디 상수코드
	public final static int CUSTOMER_SERVICE = 1;	// 고객문의 게시판 상수코드
	public final static int EVENT = 2;				// 이벤트 게시판 상수코드
	public final static int ESTIMATE = 3;			// 견적서 게시판 상수코드
	
	// 게시글 상태 상수코드
	public final static int HAS_REPLY = 1;	// 답변이 존재
	public final static int NOTICE = 2;		// 공지사항
	
	// 주문상태 상수코드
	public final static int PURCHASE_REQUEST = 1;	// 구매 요청(결제 완료)
	public final static int PURCHASE_CANCEL = 2;	// 결제 취소 
	public final static int PURCHASE_APPROVAL = 3;	// 구매 승인
	public final static int SHIPPING = 4;			// 배송중
	public final static int EXCHANGE_REQUEST = 5;	// 교환 요청
	public final static int REFUND_REQUEST = 6;	 	// 환불 요청
	public final static int REFUND_COMPLETE = 7;	// 환불 완료
	public final static int PURCHASE_COMPLETE = 8;	// 구매 확정
	
	// 주소록 상수코드
	public final static int NORMAL_ADDRESS = 0;		// 일반주소
	public final static int MAIN_ADDRESS = 1;		// 메인주소
}
