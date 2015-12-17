package com.tmind.qrcode.util;

import java.util.Properties;  

import javax.activation.DataHandler;  
import javax.activation.FileDataSource;  
import javax.mail.Address;  
import javax.mail.BodyPart;  
import javax.mail.Message;  
import javax.mail.Multipart;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;  
import javax.mail.internet.MimeMessage;  
import javax.mail.internet.MimeMultipart;  

public class Mail {
	private MimeMessage mimeMsg; //MIME�ʼ�����   
    private Session session; //�ʼ��Ự����   
    private Properties props; //ϵͳ����   
    private boolean needAuth = false; //smtp�Ƿ���Ҫ��֤   
    //smtp��֤�û��������   
    private String username;   
    private String password;   
    private Multipart mp; //Multipart����,�ʼ�����,����,���������ݾ���ӵ����к������MimeMessage����   
       
    /** 
     * Constructor 
     * @param smtp �ʼ����ͷ����� 
     */  
    public Mail(String smtp){   
        setSmtpHost(smtp);   
        createMimeMessage();   
    }   
  
    /** 
     * �����ʼ����ͷ����� 
     * @param hostName String  
     */  
    public void setSmtpHost(String hostName) {   
        System.out.println("mail.smtp.host名 = "+hostName);
        if(props == null)  
            props = System.getProperties(); //���ϵͳ���Զ���    
        props.put("mail.smtp.host",hostName); //����SMTP����   
    }   
  
  
    /** 
     * ����MIME�ʼ�����   
     * @return 
     */  
    public boolean createMimeMessage()   
    {   
        try {   
            System.out.println("createMimeMessage");
            session = Session.getDefaultInstance(props,null); //����ʼ��Ự����   
        }   
        catch(Exception e){   
            System.err.println("createMimeMessage Error"+e);
            return false;   
        }   
      
        System.out.println("createMimeMessage");
        try {   
            mimeMsg = new MimeMessage(session); //����MIME�ʼ�����   
            mp = new MimeMultipart();   
          
            return true;   
        } catch(Exception e){   
            System.err.println("createMimeMessage"+e);
            return false;   
        }   
    }     
      
    /** 
     * ����SMTP�Ƿ���Ҫ��֤ 
     * @param need 
     */  
    public void setNeedAuth(boolean need) {   
        System.out.println("setNeedAuthmail.smtp.auth = "+need);
        if(props == null) props = System.getProperties();   
        if(need){   
            props.put("mail.smtp.auth","true");   
        }else{   
            props.put("mail.smtp.auth","false");   
        }   
    }   
  
    /** 
     * �����û�������� 
     * @param name 
     * @param pass 
     */  
    public void setNamePass(String name,String pass) {   
        username = name;   
        password = pass;   
    }   
  
    /** 
     * �����ʼ����� 
     * @param mailSubject 
     * @return 
     */  
    public boolean setSubject(String mailSubject) {   
        System.out.println("setSubject");
        try{   
            mimeMsg.setSubject(mailSubject);   
            return true;   
        }   
        catch(Exception e) {   
            System.err.println("setSubject Error");
            return false;   
        }   
    }  
      
    /**  
     * �����ʼ����� 
     * @param mailBody String  
     */   
    public boolean setBody(String mailBody) {   
        try{   
            BodyPart bp = new MimeBodyPart();   
            bp.setContent(""+mailBody,"text/html;charset=GBK");   
            mp.addBodyPart(bp);   
          
            return true;   
        } catch(Exception e){   
        System.err.println("setBody Error"+e);
        return false;   
        }   
    }   
    /**  
     * ��Ӹ��� 
     * @param filename String  
     */   
    public boolean addFileAffix(String filename) {   
      
        System.out.println("addFileAffix"+filename);
        try{   
            BodyPart bp = new MimeBodyPart();   
            FileDataSource fileds = new FileDataSource(filename);   
            bp.setDataHandler(new DataHandler(fileds));   
            bp.setFileName(fileds.getName());   
              
            mp.addBodyPart(bp);   
              
            return true;   
        } catch(Exception e){   
            System.err.println("addFileAffix"+filename+"addFileAffix error"+e);
            return false;   
        }   
    }   
      
    /**  
     * ���÷����� 
     * @param from String  
     */   
    public boolean setFrom(String from) {   
        System.out.println("setFrom");
        try{   
            mimeMsg.setFrom(new InternetAddress(from)); //���÷�����   
            return true;   
        } catch(Exception e) {   
            return false;   
        }   
    }   
    /**  
     * ���������� 
     * @param to String  
     */   
    public boolean setTo(String to){   
        if(to == null)return false;   
        try{   
            mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));   
            return true;   
        } catch(Exception e) {   
            return false;   
        }     
    }   
      
    /**  
     * ���ó����� 
     * @param copyto String   
     */   
    public boolean setCopyTo(String copyto)   
    {   
        if(copyto == null)return false;   
        try{   
        mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto));   
        return true;   
        }   
        catch(Exception e)   
        { return false; }   
    }   
      
    /**  
     * �����ʼ� 
     */   
    public boolean sendOut()   
    {   
        try{   
            mimeMsg.setContent(mp);   
            mimeMsg.saveChanges();   
            System.out.println("sent out.....");
              
            Session mailSession = Session.getInstance(props,null);   
            Transport transport = mailSession.getTransport("smtp");   
            transport.connect((String)props.get("mail.smtp.host"),username,password);   
            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));   
            //transport.send(mimeMsg);   
              
            System.out.println("send out finished");
            transport.close();   
              
            return true;   
        } catch(Exception e) {   
            System.err.println("send out finished"+e);
            return false;   
        }   
    }   
  
    /** 
     * ����sendOut��������ʼ����� 
     * @param smtp 
     * @param from 
     * @param to 
     * @param subject 
     * @param content 
     * @param username 
     * @param password 
     * @return boolean 
     */  
    public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password) {  
        Mail theMail = new Mail(smtp);  
        theMail.setNeedAuth(true); //��Ҫ��֤  
          
        if(!theMail.setSubject(subject)) return false;  
        if(!theMail.setBody(content)) return false;  
        if(!theMail.setTo(to)) return false;  
        if(!theMail.setFrom(from)) return false;  
        theMail.setNamePass(username,password);  
          
        if(!theMail.sendOut()) return false;  
        return true;  
    }  
      
    /** 
     * ����sendOut��������ʼ�����,���� 
     * @param smtp 
     * @param from 
     * @param to 
     * @param copyto 
     * @param subject 
     * @param content 
     * @param username 
     * @param password 
     * @return boolean 
     */  
    public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password) {  
        Mail theMail = new Mail(smtp);  
        theMail.setNeedAuth(true); //��Ҫ��֤  
          
        if(!theMail.setSubject(subject)) return false;  
        if(!theMail.setBody(content)) return false;  
        if(!theMail.setTo(to)) return false;  
        if(!theMail.setCopyTo(copyto)) return false;  
        if(!theMail.setFrom(from)) return false;  
        theMail.setNamePass(username,password);  
          
        if(!theMail.sendOut()) return false;  
        return true;  
    }  
      
    /** 
     * ����sendOut��������ʼ�����,��� 
     * @param smtp 
     * @param from 
     * @param to 
     * @param subject 
     * @param content 
     * @param username 
     * @param password 
     * @param filename ����·�� 
     * @return 
     */  
    public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password,String filename) {  
        Mail theMail = new Mail(smtp);  
        theMail.setNeedAuth(true); //��Ҫ��֤  
          
        if(!theMail.setSubject(subject)) return false;  
        if(!theMail.setBody(content)) return false;  
        if(!theMail.addFileAffix(filename)) return false;   
        if(!theMail.setTo(to)) return false;  
        if(!theMail.setFrom(from)) return false;  
        theMail.setNamePass(username,password);  
          
        if(!theMail.sendOut()) return false;  
        return true;  
    }  
      
    /** 
     * ����sendOut��������ʼ�����,����ͳ��� 
     * @param smtp 
     * @param from 
     * @param to 
     * @param copyto 
     * @param subject 
     * @param content 
     * @param username 
     * @param password 
     * @param filename 
     * @return 
     */  
    public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password,String filename) {  
        Mail theMail = new Mail(smtp);  
        theMail.setNeedAuth(true); //��Ҫ��֤  
          
        if(!theMail.setSubject(subject)) return false;  
        if(!theMail.setBody(content)) return false;  
        if(!theMail.addFileAffix(filename)) return false;   
        if(!theMail.setTo(to)) return false;  
        if(!theMail.setCopyTo(copyto)) return false;  
        if(!theMail.setFrom(from)) return false;  
        theMail.setNamePass(username,password);  
          
        if(!theMail.sendOut()) return false;  
        return true;  
    }  
}
