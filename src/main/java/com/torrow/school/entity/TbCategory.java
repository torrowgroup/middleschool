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

	/* 类别类父id，概括类1，新闻类2，机构部(语文教研组，数学教研组)3，
	 * 用户(管理员、教师、政教处)4，通知公告6,校园风光类7,底部链接8,上传类(学生管理和教师成长)9,
	 * 资源下载11,校园文学12,其他信息（联系我们，考试时间）13
	 */
    private Integer caPid;
    
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
	
	public TbCategory( Integer caPid, String caName) {
		super();
		this.caPid = caPid;
		this.caName = caName;
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