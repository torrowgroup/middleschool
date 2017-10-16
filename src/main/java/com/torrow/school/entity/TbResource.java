package com.torrow.school.entity;

import java.io.Serializable;


import java.util.Date;


/*
 * 资源类
 */
public class TbResource implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer reId;	//编号

    private Integer caId;	//类别类编号

    private String caName;	//类别类名称

    private String reTitle;		//资源标题，图片简介，链接名称

    private String reIssuer;	//发布人或者通知对象

    private Date reIssuingdate;	//发布日期

    private String reContent;	//内容
    
    private String spare;	//备用

	public TbResource() {
		super();
	}

	public TbResource(Integer reId,Integer caId, String caName, String reTitle, String reIssuer, Date reIssuingdate,
			String reContent) {
		super();
		this.reId=reId;
		this.caId = caId;
		this.caName = caName;
		this.reTitle = reTitle;
		this.reIssuer = reIssuer;
		this.reIssuingdate = reIssuingdate;
		this.reContent = reContent;
	}

	public TbResource(Integer caId, Date reIssuingdate,String caName, String reTitle,
			String reContent) {
		super();
		this.caId = caId;
		this.reIssuingdate = reIssuingdate;
		this.caName = caName;
		this.reTitle = reTitle;
		this.reContent = reContent;
	}
	
	public TbResource(Integer caId,String caName, String reTitle,
			String reContent) {
		super();
		this.caId = caId;
		this.caName = caName;
		this.reTitle = reTitle;
		this.reContent = reContent;
	}
	
	public TbResource(String caName,String reTitle,
			String reContent) {
		super();
		this.caName = caName;
		this.reTitle = reTitle;
		this.reContent = reContent;
	}
	
	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
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

	public String getReTitle() {
		return reTitle;
	}

	public void setReTitle(String reTitle) {
		this.reTitle = reTitle;
	}

	public String getReIssuer() {
		return reIssuer;
	}

	public void setReIssuer(String reIssuer) {
		this.reIssuer = reIssuer;
	}

	public Date getReIssuingdate() {
		return reIssuingdate;
	}

	public void setReIssuingdate(Date reIssuingdate) {
		this.reIssuingdate = reIssuingdate;
	}

	public String getReContent() {
		return reContent;
	}

	public void setReContent(String reContent) {
		this.reContent = reContent;
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
		return "TbResource [reId=" + reId + ", caId=" + caId + ", caName=" + caName + ", reTitle=" + reTitle
				+ ", reIssuer=" + reIssuer + ", reIssuingdate=" + reIssuingdate + ", reContent=" + reContent
				+ ", spare=" + spare + "]";
	}

}