package com.torrow.school.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.Email;

import sun.misc.BASE64Encoder;
/**
 * @author 张金高
 * 2017年10月3日下午4:36:56
 */
@Controller
public class LoginController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @param usEmail
	 * @param usPassword
	 * @param model
	 * @return 登陆的方法   张金高改，合并身份页面
	 */
	@RequestMapping("userLogin")
	public ModelAndView login(String usEmail,String usPassword,Model model){
		TbUser tbUser=userService.login(usEmail,usPassword);
		String view = "index";
		if(null!=tbUser) {
			categoryService.findAllCategory(model);			//这个方法是为了获得首页的显示数据
			TbCategory tbCategory = categoryService.selectByPrimaryKey(tbUser.getCaId());
			if(tbUser.getCaName().equals("管理员")){
				session.setAttribute("admin",tbUser);
				view = "admin/index";
			} else {
				session.setAttribute("teacher", tbUser);
				if(tbCategory.getCaPid()==3) {//当用户属于教育教研
					model.addAttribute("identity", "教研组");
				} else if(tbCategory.getCaName().equals("政教处")) {//当用户是政教处
					model.addAttribute("identity", "政教处");
				}
				view = "educationoffice/index";
			}
		}else {
			model.addAttribute("msg", "用户名或密码错误");
		}
		return new ModelAndView(view);
	}
	
	/**
	 * @return web-app下html用于跳转到登录界面
	 */
	@RequestMapping("login")
	public ModelAndView login(){
		log.info("登录");
		return new ModelAndView("index");
	}
	
	/**
	 * @return web-app下html用于跳转到游客首页界面
	 * @throws ParseException 
	 */
	@RequestMapping("index")
	public ModelAndView index(Model model) throws ParseException{
		categoryService.getCategory(0,model);
		List<TbCategory> categoryList = categoryService.selectAll();
		resourceService.getResource(categoryList,model);//得到新闻，公告等,追加资源下载,只应用于首页
		resourceService.getTimeInfor(model);//得到考试和联系信息,追加底部链接
		List<TbUser> userAll = userService.selectAll();//首页教师剪影
		if(userAll.size()>0){
			model.addAttribute("famousTeacherOne", userService.selectAll().get(0));
		}
		if(userAll.size()>1){
			int a=0,b=0;
			Random rd = new Random();
			do{
				a = rd.nextInt(userAll.size());
				b = rd.nextInt(userAll.size());
			}while(a==b);
			model.addAttribute("famousTeacherOne", userService.selectAll().get(a));
			model.addAttribute("famousTeacherTwo", userService.selectAll().get(b));
		}	
		return new ModelAndView("visitor/index");
	}
	
	/**
	 * @return	到达输入找回密码邮箱界面
	 */
	@RequestMapping("toInputEmail")
	public ModelAndView toInputEmail() {
		return new ModelAndView("inputemail");
	}
	
	/**
	 * @param email	找回密码的邮箱
	 * @return	ajax验证找回密码的邮箱是否存在
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws MessagingException 
	 */
	@SuppressWarnings("restriction")
	@RequestMapping("getTestCode")
	public @ResponseBody Map<String , Boolean> getTestCode(String email,Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException{
		TbUser user = userService.selectByEmail(email);
		Map<String, Boolean> map = new HashMap<String, Boolean>();//用于返回值给前台
		if(user!=null){
			session.setAttribute("missEmail", email);
			map.put("msg", true);
			String testCode = "";	//得到一个6位的随机数验证码
			Random random = new Random();
			for (int i = 0; i < 6; i++) {
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
				if ("char".equalsIgnoreCase(charOrNum)) // 字符串
				{
					int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
					testCode += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
				{
					testCode += String.valueOf(random.nextInt(10));
				}
			}
			if(!this.sendEmail(email, testCode)){//将邮件发送到使用者邮箱中
				map.put("msg", false);
				return map;
			}
			log.info("email :"+email+" testCode "+testCode);
			testCode =testCode.toLowerCase();
	    	MessageDigest md5 = MessageDigest.getInstance("MD5");//指定MD5加密算法
			BASE64Encoder base64en = new BASE64Encoder();	//Base64的编码
			testCode = base64en.encode(md5.digest(testCode.getBytes("utf-8")));
			session.setAttribute("randomCode", testCode);
		} else {
			map.put("msg", false);
		}
		return map;
	}
	
	
	/**
	 * @param email	接收邮件的邮箱
	 * @param testCode 验证码
	 */
	public boolean sendEmail(String email,String testCode) {
		String from = "郑州一二二中学";
		String mailTitle = "找回密码";
		String mailContent = "您正在进行登录密码的重置，验证码为<strong>"+testCode+"</strong>，该验证码您只能使用一次，您的信息安全由我们保障！";
		Email sEmail = new Email("2016zjg@wlgzs.org","jian0503",from,email,mailTitle,mailContent,new Date());
		try {
			sEmail.sendMail();
		} catch (Exception e) {
			log.info("邮箱已注册该系统，但该邮箱不合法，无法找回密码");
			return false;
		}
		return true;
	}
	
	/**
	 * @param testCode	用户输入的验证码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException 
	 */
	@SuppressWarnings("restriction")
	@RequestMapping("testCode")
	public ModelAndView testCode(String testCode,Model model) throws NoSuchAlgorithmException, IOException{
		testCode = testCode.toLowerCase();
		MessageDigest md5 = MessageDigest.getInstance("MD5");//指定MD5加密算法
		BASE64Encoder base64en = new BASE64Encoder();	//Base64的编码
		testCode = base64en.encode(md5.digest(testCode.getBytes("utf-8")));
		String randomCode = (String)session.getAttribute("randomCode");
		session.removeAttribute("randomCode");
		String message = "验证码错误！请重新获取验证码。";
		if(testCode.equals(randomCode)){
			log.info("验证码正确");
			return new ModelAndView("updatepassword");
		}
		model.addAttribute("message", message);
		return new ModelAndView("inputemail");
	}
	
	@RequestMapping("updatePassword")
	public ModelAndView updatePassword(String password,Model model){
		String usEmail = (String)session.getAttribute("missEmail");
		session.removeAttribute("missEmail");
		TbUser user = userService.selectByEmail(usEmail);
		if(user!=null){
			user.setUsPassword(password);	
		}
		if(userService.updateByPrimaryKey(user)==1){
			model.addAttribute("message", "密码修改成功！请返回重新登录");
		} else {
			model.addAttribute("message", "密码修改失败！请返回重新找回");
		}
		return new ModelAndView("updatepassword");
	}
	
	/**
	 * 管理员的退出登录
	 * @return 退出登录清空session
	 */
	@RequestMapping("logout")
	public String logout() {
		TbUser tbUser=(TbUser)session.getAttribute("admin");
		session.removeAttribute("admin");
		log.info("--------------------"+tbUser);
		return "index";
	}
	
	/**
	 * 教师/教研组/政教处的退出登录
	 * @return 退出登录清空session
	 */
	@RequestMapping("loginOut")
	public String loginOut() {
		TbUser tbUser=(TbUser)session.getAttribute("teacher");
		session.removeAttribute("teacher");
		log.info("--------------------"+tbUser);
		return "index";
	}
}