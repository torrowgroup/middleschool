package com.torrow.school.entity;

import java.io.Serializable;


/*
 * 资源类
 */
public class TbResource implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer reId;	//编号

    private Integer caId;	//类别类编号

    private String caName;	//类别类名称

    private String reTitle;		//资源标题，图片简介，链接名称,轮播图类别名称

    private String reIssuer;	//发布人或者通知对象(姓名)

    private String reIssuingdate;	//发布日期

    private String reContent;	//内容
    
    private String spare;	//是否置顶(状态：是代表置顶按钮变成取消置顶、否代表非置顶，按钮是置顶状态),

	public TbResource() {
		super();
	}

	//张金高用
	public TbResource(Integer caId,String reIssuer, String reIssuingdate,String caName, String reTitle,
			String reContent) {
		super();
		this.caId = caId;
		this.reIssuer = reIssuer;
		this.reIssuingdate = reIssuingdate;
		this.caName = caName;
		this.reTitle = reTitle;
		this.reContent = reContent;
	}
	//安李杰在添加学校新闻时用到
	public TbResource(Integer caId, String reIssuingdate,String caName, String reTitle,
			String reContent) {
		super();
		this.caId = caId;
		this.reIssuingdate = reIssuingdate;
		this.caName = caName;
		this.reTitle = reTitle;
		this.reContent = reContent;
	}
	
	public TbResource(Integer caId,String caName,
			String reContent) {
		super();
		this.caId = caId;
		this.caName = caName;
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
	
	public TbResource(Integer caId, String caName) {
		this.caId = caId;
		this.caName = caName;
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

	public String getReIssuingdate() {
		return reIssuingdate;
	}

	public void setReIssuingdate(String reIssuingdate) {
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