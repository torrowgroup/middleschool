package com.torrow.school.entity;

import java.io.Serializable;
/*
 * 留言板类
 */
public class TbMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer meId;	//编号

    private String meTitle;	//留言标题

    private String meHide;	//是否匿名，匿名则“匿名”，否则内容为留言人填写的个人信息

    private String meIssuingdate;	//留言日期

    private String meStatus;	//留言状态(未回复，已回复)

    private String meContent;	//留言内容

    private String meReply;		//回复内容
    
    private String mePicture;	//上传图片地址,不用了，无用字段
    
    private String spare;	//备用

	public TbMessage() {
		super();
	}
	//写入留言
	public TbMessage( String meTitle, String meHide, String meIssuingdate, String meStatus,
			String meContent) {
		super();
		this.meTitle = meTitle;
		this.mePicture = "";
		this.meHide = meHide;
		this.meIssuingdate = meIssuingdate;
		this.meStatus = meStatus;
		this.meContent = meContent;
		this.meReply = "";
		this.spare = null;
	}
	
	public TbMessage( String meTitle, String mePicture, String meHide, String meIssuingdate, String meStatus,
			String meContent, String meReply, String spare) {
		super();
		this.meTitle = meTitle;
		this.mePicture = mePicture;
		this.meHide = meHide;
		this.meIssuingdate = meIssuingdate;
		this.meStatus = meStatus;
		this.meContent = meContent;
		this.meReply = meReply;
		this.spare = spare;
	}

	public Integer getMeId() {
		return meId;
	}

	public void setMeId(Integer meId) {
		this.meId = meId;
	}

	public String getMeTitle() {
		return meTitle;
	}

	public void setMeTitle(String meTitle) {
		this.meTitle = meTitle;
	}

	public String getMePicture() {
		return mePicture;
	}

	public void setMePicture(String mePicture) {
		this.mePicture = mePicture;
	}

	public String getMeHide() {
		return meHide;
	}

	public void setMeHide(String meHide) {
		this.meHide = meHide;
	}

	public String getMeIssuingdate() {
		return meIssuingdate;
	}

	public void setMeIssuingdate(String meIssuingdate) {
		this.meIssuingdate = meIssuingdate;
	}

	public String getMeStatus() {
		return meStatus;
	}

	public void setMeStatus(String meStatus) {
		this.meStatus = meStatus;
	}

	public String getMeContent() {
		return meContent;
	}

	public void setMeContent(String meContent) {
		this.meContent = meContent;
	}

	public String getMeReply() {
		return meReply;
	}

	public void setMeReply(String meReply) {
		this.meReply = meReply;
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
		return "TbMessage [meId=" + meId + ", meTitle=" + meTitle + ", mePicture=" + mePicture + ", meHide=" + meHide
				+ ", meIssuingdate=" + meIssuingdate + ", meStatus=" + meStatus + ", meContent=" + meContent
				+ ", meReply=" + meReply + ", spare=" + spare + "]";
	}

}