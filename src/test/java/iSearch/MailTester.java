package iSearch;

import com.tmind.qrcode.util.Mail;

public class MailTester {

	public static void main(String args[]){
		String smtp = "smtp.163.com";  
	    String from = "13851483034@163.com";  
	    String to = "179012331@qq.com";  
	    String subject = "���Ӳɹ�";  
	    String content = "���Ӳɹ�,����10�䣬��ϵ�绰13851483034";  
	    String username="13851483034@163.com";  
	    String password="19850924";  
	    Mail.send(smtp, from, to, subject, content, username, password);  
	}
}
