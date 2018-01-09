package com.ncd.xsx.Dao;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.ncd.xsx.Entity.Report;

public class ReportDao {

	public static ReportDao reportDaoObject = null;
	private Dao<Report, Integer> reportDao = null;
	
	public static ReportDao getInstance()
	{
		if(reportDaoObject == null)
			reportDaoObject = new ReportDao();
		
		return reportDaoObject;
	}
	
	public void setReportDao(Dao<Report, Integer> reportDao) {
		this.reportDao = reportDao;
	}

	public Boolean saveReport(Report report) {

		if(report == null)
			return false;
		
		if(reportDao == null)
			return false;
		
		try {
			if(report.getId() == null) {
				reportDao.create(report);
			}
			else {
				Report tempReport = reportDao.queryForId(report.getId());
				if(tempReport == null)
				{
					reportDao.create(report);
				}
				else
				{
					report.setId(tempReport.getId());
					reportDao.update(report);
				}
			}
			
			RecordDao.getInstance().getRecordDao().update(report.getRecord());

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		} 
	}

}
