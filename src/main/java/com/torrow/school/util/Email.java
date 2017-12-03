package com.torrow.school.util;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * @author 张金高
 * 2017年11月9日下午1:11:10
 * 发送邮件
 */
public class Email {

	private String fromEmail;	//发件人邮箱
	private String password;	//发件人邮箱密码
	private String from;	//自定义昵称
	private String toMail;	//收件人
	private String mailTitle;	//邮件标题
	private String mailContent;		//邮件内容
	private Date date;		//设置定时发送

	public Email() {}
	
	public Email(String fromEmail,String password, String from, String toMail, String mailTitle, String mailContent, Date date) {
		this.fromEmail = fromEmail;
		this.password = password;
		this.from = from;
		this.toMail = toMail;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
		this.date = date;
	}
	//发送网易云邮件
	public void sendMail() throws MessagingException, UnsupportedEncodingException{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.ym.163.com");
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		Session session = Session.getInstance(props);
		session.setDebug(true);//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态

		MimeMessage msg = new MimeMessage(session);//由邮件会话创建一个扩展信息对象，包装信息格式，可以只是简单文本
		String nick = MimeUtility.encodeText(from);//防止乱码
		msg.setFrom(new InternetAddress(nick+"<"+fromEmail+">"));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));//设置收件人，并设置其接受类型为to
		msg.setSubject(mailTitle);
		msg.setContent(mailContent,"text/html;charset=UTF-8");
		msg.setSentDate(date);	
		msg.saveChanges();
		Transport tran = session.getTransport("smtp");//使用smtp协议获取session环境的Transprot对象来发送邮件 javamial使用Transport对象来管理发送邮件服务
		tran.connect(props.getProperty("mail.smtp.host"),fromEmail,password);//链接邮箱服务器，发送邮件的邮箱，以及授权码
		tran.sendMessage(msg, msg.getAllRecipients());//发送邮件，getAllRecipients()是所有已设好的收件人地址
		tran.close();
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Email [fromEmail=" + fromEmail + ", password=" + password + ", from=" + from + ", toMail=" + toMail
				+ ", mailTitle=" + mailTitle + ", mailContent=" + mailContent + ", date=" + date + "]";
	}
	
}
