package com.ncd.xsx.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Report {

	@DatabaseField(generatedId = true, allowGeneratedIdInsert = true) 
	private Integer id;
	
	@DatabaseField(columnName = "name")
	private String name;
	
	@DatabaseField(columnName = "sex")
	private String sex;
	
	@DatabaseField(columnName = "age")
	private String age;
	
	@DatabaseField(columnName = "sickId")
	private String sickId;
	
	@DatabaseField(columnName = "department")
	private String department;
	
	@DatabaseField(columnName = "chuanghao")
	private String chuanghao;
	
	@DatabaseField(columnName = "bloodType")
	private String bloodType;
	
	@DatabaseField(columnName = "device")
	private String device;
	
	@DatabaseField(columnName = "barcode")
	private String barcode;
	
	@DatabaseField(columnName = "sendDoctor")
	private String sendDoctor;
	
	@DatabaseField(columnName = "sickdesc")
	private String sickdesc;
	
	@DatabaseField(columnName = "gettime")
	private String gettime;
	
	@DatabaseField(columnName = "desc")
	private String desc;
	
	@DatabaseField(columnName = "recvTime")
	private String recvTime;
	
	@DatabaseField(columnName = "reportTime")
	private String reportTime;
	
	@DatabaseField(columnName = "checker")
	private String checker;
	
	@DatabaseField(columnName = "rechecker")
	private String rechecker;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = "record_id")  
	private Record record;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSickId() {
		return sickId;
	}

	public void setSickId(String sickId) {
		this.sickId = sickId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getChuanghao() {
		return chuanghao;
	}

	public void setChuanghao(String chuanghao) {
		this.chuanghao = chuanghao;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSendDoctor() {
		return sendDoctor;
	}

	public void setSendDoctor(String sendDoctor) {
		this.sendDoctor = sendDoctor;
	}

	public String getSickdesc() {
		return sickdesc;
	}

	public void setSickdesc(String sickdesc) {
		this.sickdesc = sickdesc;
	}

	public String getGettime() {
		return gettime;
	}

	public void setGettime(String gettime) {
		this.gettime = gettime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getRechecker() {
		return rechecker;
	}

	public void setRechecker(String rechecker) {
		this.rechecker = rechecker;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
}
