package com.torrow.school.entity;

import java.io.Serializable;
/*
 * 用户类
 */
public class TbUser implements Serializable {

	private static final long serialVersionUID = 1L;
    
	private Integer usId;	//用户编号
    private String usEmail;	//用户邮箱，账号
    private String usPassword;	//用户密码
    private String usName;	//用户姓名
    private String usSex;	//用户性别
    private Integer caId;	//身份编号，类别类编号
    private String caName;	//身份名称
    private String usPhone;	//联系方式	
    private String usPicture;	//用户照片
    private String usIntroduction;	//用户简介
    private String usAchievements;	//用户成就
    private String spare;	//备用

	public TbUser() {
		super();
	}
	
	public TbUser( String usEmail, String usPassword, String usName, String usSex, Integer caId,
			String caName, String usPhone, String usPicture, String usIntroduction, String usAchievements,
			String spare) {
		super();
		this.usEmail = usEmail;
		this.usPassword = usPassword;
		this.usName = usName;
		this.usSex = usSex;
		this.caId = caId;
		this.caName = caName;
		this.usPhone = usPhone;
		this.usPicture = usPicture;
		this.usIntroduction = usIntroduction;
		this.usAchievements = usAchievements;
		this.spare = spare;
	}

	public TbUser(Integer caId, String caName) {
		this.caId = caId;
		this.caName = caName;
	}

	public Integer getUsId() {
		return usId;
	}

	public void setUsId(Integer usId) {
		this.usId = usId;
	}

	public String getUsEmail() {
		return usEmail;
	}

	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}

	public String getUsPassword() {
		return usPassword;
	}

	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}

	public String getUsName() {
		return usName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	public String getUsSex() {
		return usSex;
	}

	public void setUsSex(String usSex) {
		this.usSex = usSex;
	}

	public Integer getCaId() {
		return caId;
	}

	public void setCaId(Integer caId) {
		this.caId = caId;
	}

	public String getCaName() {
		return caName;
	}

	public void setCaName(String caName) {
		this.caName = caName;
	}

	public String getUsPhone() {
		return usPhone;
	}

	public void setUsPhone(String usPhone) {
		this.usPhone = usPhone;
	}

	public String getUsPicture() {
		return usPicture;
	}

	public void setUsPicture(String usPicture) {
		this.usPicture = usPicture;
	}

	public String getUsIntroduction() {
		return usIntroduction;
	}

	public void setUsIntroduction(String usIntroduction) {
		this.usIntroduction = usIntroduction;
	}

	public String getUsAchievements() {
		return usAchievements;
	}

	public void setUsAchievements(String usAchievements) {
		this.usAchievements = usAchievements;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TbUser [usId=" + usId + ", usEmail=" + usEmail + ", usPassword=" + usPassword + ", usName=" + usName
				+ ", usSex=" + usSex + ", caId=" + caId + ", caName=" + caName + ", usPhone=" + usPhone + ", usPicture="
				+ usPicture + ", usIntroduction=" + usIntroduction + ", usAchievements=" + usAchievements + ", spare="
				+ spare + "]";
	}

}