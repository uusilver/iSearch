package iSearch;

import com.tmind.qrcode.util.Mail;

public class MailTester {

	public static void main(String args[]){
		String smtp = "smtp.163.com";  
	    String from = "13851483034@163.com";  
	    String to = "179012331@qq.com";  
	    String subject = "测试邮件";
	    String content = "测试结果123";
	    String username="13851483034@163.com";  
	    String password="19850924";  
	    Mail.send(smtp, from, to, subject, content, username, password);  
	}
}
