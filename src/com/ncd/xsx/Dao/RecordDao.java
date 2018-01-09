package com.ncd.xsx.Dao;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Entity.Record;

public class RecordDao {

	public static RecordDao recordDaoObject = null;
	private Dao<Record, Integer> recordDao = null;
	
	public static RecordDao getInstance()
	{
		if(recordDaoObject == null)
			recordDaoObject = new RecordDao();
		
		return recordDaoObject;
	}
	
	public void setRecordDao(Dao<Record, Integer> recordDao) {
		this.recordDao = recordDao;
	}

	
	public Dao<Record, Integer> getRecordDao() {
		return recordDao;
	}

	public Boolean saveRecord(Record record) {
		
		if(record == null)
			return false;
		
		if(recordDao == null)
			return false;
		
		try {
			
			Record tempRecord = this.queryByCardPiNum(record);
			if(tempRecord == null)
				recordDao.create(record);
			else
			{
				record.setId(tempRecord.getId());
				recordDao.update(record);
			}
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		} 
	}

	public Record queryByCardPiNum(Record record) {
		try {
        	QueryBuilder<Record, Integer> queryBuilder = recordDao.queryBuilder();  
        	
			queryBuilder.where().eq("PiHao", record.getPihao()).and().eq("PiNum", record.getPinum());
			
			Record tempRecord = recordDao.queryForFirst(queryBuilder.prepare());  
			  
	        return tempRecord;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		} 
	}
	
	public void queryRecordByParms(RequestObject requestObject, ResponseObject responseObject)
	{
        try {
        	QueryBuilder<Record, Integer> queryBuilder = recordDao.queryBuilder();  
        	queryBuilder.offset(requestObject.getRequestPageIndex()*20);
			queryBuilder.limit(20L);
			
			if (requestObject.getRecord().getPrintted() != null) {
				queryBuilder.where().eq("IsPrint", requestObject.getRecord().getPrintted());
			}

			if(queryBuilder.countOf() % 20 != 0)
				responseObject.setTotalPageNum(queryBuilder.countOf() / 20 + 1l);
			else
				responseObject.setTotalPageNum(queryBuilder.countOf() / 20);
			
			List<Record> tempRecord = recordDao.query(queryBuilder.prepare());
			responseObject.setCurrentNum(tempRecord.size());
			responseObject.setRecords(tempRecord);
			responseObject.setReports(null);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			responseObject.setCurrentNum(0);
			responseObject.setRecords(null);
			responseObject.setTotalPageNum(0l);
			responseObject.setReports(null);
		}  
	}
}
