package com.facebook.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.facebook.entity.UserEntity;
import com.facebook.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// 비밀번호 찾기 요청한 유저의 이메일로 임시 생성된 비밀번호를 전송  
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

	private final UserRepository userRepository;
	private final JavaMailSender javaMailSender;
	
	public MimeMessage createMail(String tmpPassword, String memberEmail) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		UserEntity user = userRepository.findByEmail(memberEmail);
		String msg = "";
		message.addRecipients(RecipientType.TO, memberEmail);
		message.setSubject("[Spring-facebook] 임시 비밀번호 발급 안내");
		
		msg+="<div>";
	    msg+="<div>";
	    msg+="<div><u></u>";
	    msg+="<div style='margin:0;padding:0' bgcolor='#ffffff'>";
	    msg+="<table align='center' border='0' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;background:#ffffff'>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='1' colspan='3' style='line-height:1px'><span style='color:#ffffff;font-size:1px;opacity:0'>회원님의 Facebook 비밀번호 재설정 요청을 받았습니다.</span></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='15' style='line-height:15px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='left' width='32' style='height:32;line-height:0px'><img width='32' src='https://static.xx.fbcdn.net/rsrc.php/v3/yc/r/I92GqZOkKcu.png' height='32' style='border:0'></td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td width='100%'><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:19px;line-height:32px;color:#1877f2'></span></td>";
	    msg+="</tr>";
	    msg+="<tr style='border-bottom:solid 1px #e5e5e5'>";
	    msg+="<td height='15' style='line-height:15px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='4' style='line-height:4px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823'><span style='font-size:15px'>";
	    msg+="<p></p>";
	    msg+="<div style='margin-top:16px;margin-bottom:20px'>" + user.getUserName() + " 님, 안녕하세요!</div>";
	    msg+="<div>회원님의 Facebook 비밀번호 재설정 요청을 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " 에 받았습니다.</div>발급된 임시 비밀번호를 통해 로그인 후 원하는 비밀번호로 변경하세요:<p></p>";
	    msg+="<table border='0' cellspacing='0' cellpadding='0' style='border-collapse:collapse;width:max-content;margin-top:20px;margin-bottom:20px'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td style='font-size:11px;font-family:LucidaGrande,tahoma,verdana,arial,sans-serif;padding:14px 32px 14px 32px;background-color:#f2f2f2;border-left:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;border-bottom:1px solid #ccc;text-align:center;border-radius:7px;display:block;border:1px solid #1877f2;background:#e7f3ff'><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:16px;line-height:21px;color:#141823'><span style='font-size:17px;font-family:Roboto;font-weight:700;margin-left:0px;margin-right:0px'>" + tmpPassword + "</span></span></td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>또는 비밀번호를 직접 변경할 수도 있습니다.<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='middle'><a href='#' style='color:#1b74e4;text-decoration:none' target='_blank'>";
	    msg+="<table border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td style='border-collapse:collapse;border-radius:6px;text-align:center;display:block;background:#1877f2;padding:8px 20px 8px 20px'><a href='#' style='color:#1b74e4;text-decoration:none;display:block' target='_blank'>";
	    msg+="<font><span style='font-family:Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;white-space:nowrap;font-weight:bold;vertical-align:middle;color:#ffffff;font-weight:500;font-family:Roboto-Medium,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:17px'>비밀번호&nbsp;변경</span></font>";
	    msg+="</a></td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</a></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='8' style='line-height:8px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table><br>";
	    msg+="<div><span style='color:#333333;font-weight:bold'>이 변동 요청을 하지 않으셨나요?</span></div>새 비밀번호를 요청하지 않으셨다면 <a href='https://www.facebook.com/login/recover/cancel/?n=735812&amp;id=100092282715924&amp;i=www' style='color:#0a7cff;text-decoration:none' target='_blank'>저희에게 알려주세요</a>.";
	    msg+="</span></span></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='50' style='line-height:50px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="<td>";
	    msg+="<table align='left' border='0' width='100%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>";
	    msg+="<tbody>";
	    msg+="<tr style='border-top:solid 1px #e5e5e5'>";
	    msg+="<td height='19' style='line-height:19px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td style='font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;font-size:11px;color:#8a8d91;line-height:16px;font-weight:400'>";
	    msg+="<table border='0' cellspacing='0' cellpadding='0' style='border-collapse:collapse;color:#8a8d91;text-align:center;font-size:12px;font-weight:400;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif'>";
	    msg+="<tbody>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-bottom:6px'>from</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-top:6px;padding-bottom:6px'><img width='74' height='22' src='https://facebook.com/images/email/meta_logo.png' style='border:0'></td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-top:6px;padding-bottom:6px'>© Facebook. Meta Platforms, Inc., Attention: Community Support, 1 Facebook Way, Menlo Park, CA 94025</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td align='center' style='font-size:12px;font-family:Roboto-Regular,Roboto,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Lucida Grande,tahoma,verdana,arial,sans-serif;color:#8a8d91;text-align:center;font-weight:400;padding-top:6px'>이 메시지는 <a style='color:#1b74e4;text-decoration:none' href='mailto:kimkymack1@gmail.com' target='_blank'>kimkymack1@gmail.com</a> 주소로 전송되었습니다. <br>계정 보안을 위해 이 이메일은 전달하지 마세요. <a style='color:#1b74e4;text-decoration:none' href='https://www.facebook.com/help/213481848684090/' target='_blank'>더 알아보기</a></td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='10' style='line-height:10px'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</td>";
	    msg+="<td width='15' style='display:block;width:15px'>&nbsp;&nbsp;&nbsp;</td>";
	    msg+="</tr>";
	    msg+="<tr>";
	    msg+="<td height='20' style='line-height:20px' colspan='3'>&nbsp;</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table><span><img src='https://www.facebook.com/email_open_log_pic.php?mid=5fabf81359386G5b088cf4bf14G5fabfcacb9658G178' style='border:0;width:1px;height:1px' te6d2yrib=''></span>";
	    msg+="</td>";
	    msg+="</tr>";
	    msg+="</tbody>";
	    msg+="</table>";
	    msg+="</div>";
	    msg+="</div>";
	    msg+="</div>";
	    msg+="</div>";
	    message.setText(msg, "UTF-8", "HTML");
	    message.setFrom(new InternetAddress("kimkymack1@gmail.com", "Spring-facebook"));
		
		javaMailSender.send(message);
		return message;
		
	}
	
}
