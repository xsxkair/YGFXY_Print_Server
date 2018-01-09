package com.ncd.xsx.Entity;

import java.sql.Timestamp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Record {

	@DatabaseField(generatedId = true, allowGeneratedIdInsert = true) 
	private Integer id;
	
	@DatabaseField(columnName = "TestTime",columnDefinition = "datetime", canBeNull = false) 
	private java.sql.Timestamp testtime;
	
	@DatabaseField(columnName = "SampleId",columnDefinition = "varchar(32)", canBeNull = false) 
	private String sampleid;
	
	@DatabaseField(columnName = "TestType",columnDefinition = "varchar(15)")
	private String testtype;
	
	@DatabaseField(columnName = "PiHao",columnDefinition = "varchar(15)")
	private String pihao;
	
	@DatabaseField(columnName = "PiNum",columnDefinition = "varchar(15)")
	private String pinum;

	@DatabaseField(columnName = "Did",columnDefinition = "varchar(32)")
	private String deviceid;
	
	@DatabaseField(columnName = "Tester",columnDefinition = "varchar(32)")
	private String tester;
	
	@DatabaseField(columnName = "Item",columnDefinition = "varchar(32)", canBeNull = false)
	private String item;

	@DatabaseField(columnName = "Danwei",columnDefinition = "varchar(32)")
	private String danwei;
	
	@DatabaseField(columnName = "Cankaozhi",columnDefinition = "varchar(100)")
	private String cankaozhi;
	
	@DatabaseField(columnName = "Value",columnDefinition = "varchar(32)")
	private String value;

	@DatabaseField(columnName = "Error",columnDefinition = "varchar(32)")
	private Boolean error;

	@DatabaseField(columnName = "IsPrint", defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
	private Boolean printted;


	public Record() {

	}

	public Record(Timestamp testtime, String sampleid, String item, Boolean isprint) {
		super();
		this.testtime = testtime;
		this.sampleid = sampleid;
		this.item = item;
		this.printted = isprint;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.sql.Timestamp getTesttime() {
		return testtime;
	}

	public void setTesttime(java.sql.Timestamp testtime) {
		this.testtime = testtime;
	}

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	public String getTesttype() {
		return testtype;
	}

	public void setTesttype(String testtype) {
		this.testtype = testtype;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getCankaozhi() {
		return cankaozhi;
	}

	public void setCankaozhi(String cankaozhi) {
		this.cankaozhi = cankaozhi;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getTester() {
		return tester;
	}

	public void setTester(String tester) {
		this.tester = tester;
	}

	public Boolean getPrintted() {
		return printted;
	}

	public void setPrintted(Boolean printted) {
		this.printted = printted;
	}

	public String getPihao() {
		return pihao;
	}

	public void setPihao(String pihao) {
		this.pihao = pihao;
	}

	public String getPinum() {
		return pinum;
	}

	public void setPinum(String pinum) {
		this.pinum = pinum;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

}
