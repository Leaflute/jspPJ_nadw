package leafcom.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSendHandler {

	public void sendActivationEmail(String id, String email, String key) {
		final String username = Code.ACTIVATION_HELPER;
		final String password = Code.PW;
		final String host = "smtp.gmail.com";
		
		// SMTP(메일 서버) 설정
		Properties props = new Properties();
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "enable");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
		// porpert값 설정
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("admin@leafcom.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			
			String content = "LEAFCOM 회원가입을 축하합니다. 보이는 링크를 눌러 회원가입을 완료하세요. "
							+ "<a href='http://localhost/jsp_pj_ndw/emailChkAction.co?key=" + key + "&id=" + id + "'>링크</a>";
			
			msg.setSubject("LEAFCOM 회원가입 인증메일입니다.");
			msg.setContent(content, "text/html; charset=utf-8"); 
			
			System.out.println("메일 전송 시작");
			Transport.send(msg);
			System.out.println("메일 전송 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
