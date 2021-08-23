package leafcom.util;

public interface Code {
	// 관리자 정보
		public final static String ACTIVATION_HELPER = "leafcom.activation.helper@gmail.com";
		public final static String PW = "!Q@W#E$R1";
		
		// 회원상태 상수코드
		public final static int NAV = 0;	// 이메일 미인증
		public final static int NRM = 1;	// 정상 회원
		public final static int RST = 2;	// 휴면 회원
		public final static int WIT = 3;	// 탈퇴 회원

		// 카테고리 상수코드
		public final static int CPU = 1;
		public final static int RAM = 2;
		public final static int MBD = 3;
		public final static int GPU = 4;
		public final static int PWS = 5;
		public final static int SSD = 6;
		public final static int HDD = 7;
		public final static int CSE = 8;
		public final static int MNT = 9;
		
		// 게시판 상수코드
		public final static int CSB = 1;	// 고객문의 게시판 상수코드
		public final static int EVB = 2;	// 이벤트 게시판 상수코드
		public final static int ESB = 3;	// 견적서 게시판 상수코드
		
		// 게시글 상태 상수코드
		
		// 주문상태 상수코드
}
