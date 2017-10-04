package com.torrow.school.entity;

import java.io.Serializable;

/**
 * 
 * @author 张金高
 *
 * 2017年10月2日下午10:11:10
 */
/*
 * 类别类，包括学校概括，学校简介；学校新闻，校园风光；教研组；管理员等类别
 */
public class TbCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer caId;	//类别类编号

    private Integer caPid;	//类别类父id，概括类1，新闻类2，机构部3，管理员4
  
    private String caName;	//类别名称

    private String spare;	//备用

    
	public TbCategory() {
		super();
	}

	public TbCategory( Integer caPid, String caName, String spare) {
		super();
		this.caPid = caPid;
		this.caName = caName;
		this.spare = spare;
	}

	public Integer getCaId() {
		return caId;
	}

	public void setCaId(Integer caId) {
		this.caId = caId;
	}

	public Integer getCaPid() {
		return caPid;
	}

	public void setCaPid(Integer caPid) {
		this.caPid = caPid;
	}

	public String getCaName() {
		return caName;
	}

	public void setCaName(String caName) {
		this.caName = caName;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	@Override
	public String toString() {
		return "TbCategory [caId=" + caId + ", caPid=" + caPid + ", caName=" + caName + ", spare=" + spare + "]";
	}

}