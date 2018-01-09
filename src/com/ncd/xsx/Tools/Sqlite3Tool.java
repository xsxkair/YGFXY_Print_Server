package com.ncd.xsx.Tools;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ncd.xsx.Dao.RecordDao;
import com.ncd.xsx.Dao.ReportDao;
import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Entity.Report;

public class Sqlite3Tool {

	public static Sqlite3Tool sqlite3Tool = null;
	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private Dao<Record, Integer> recordDao = null;
	private Dao<Report, Integer> reportDao = null;
	private static final String databaseUrl = "jdbc:sqlite:data.db";
	
	public static Sqlite3Tool getInstance()
	{
		if(sqlite3Tool == null)
			sqlite3Tool = new Sqlite3Tool();
		
		return sqlite3Tool;
	}
	
	public void SqliteDaoInit() throws SQLException {
		//创建一个JDBC连接  
		ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
				
		//创建Table  
		TableUtils.createTableIfNotExists(connectionSource, Record.class); 
		TableUtils.createTableIfNotExists(connectionSource, Report.class); 
		        
		//实例化 record DAO,对表进行数据操作  
		recordDao = DaoManager.createDao(connectionSource, Record.class);
		RecordDao.getInstance().setRecordDao(recordDao);
		
		//实例化 report DAO,对表进行数据操作  
		reportDao = DaoManager.createDao(connectionSource, Report.class);
		ReportDao.getInstance().setReportDao(reportDao);

	}
	
	public Record ConvertStringToRecord(String str)
	{
		String fildStr[] = str.split("\\|");
		
		if(fildStr.length != 14)
		{
			System.out.println("Length error");
			System.out.println(str);
			return null;
		}
		
		if(!"AA".equals(fildStr[0]))
		{
			System.out.println("AA error");
			System.out.println(str);
			return null;
		}
		
		if(!fildStr[13].startsWith("BB"))
		{
			System.out.println("BB error");
			System.out.println(str);
			return null;
		}
		
		try {
			Record record = new Record();
			record.setTesttime(Timestamp.valueOf(fildStr[1]));
			record.setSampleid(fildStr[2]);
			record.setTesttype(fildStr[3]);
			record.setPihao(fildStr[4]);
			record.setPinum(fildStr[5]);
			record.setDeviceid(fildStr[6]);
			record.setTester(fildStr[7]);
			record.setItem(fildStr[8]);
			record.setDanwei(fildStr[9]);
			record.setCankaozhi(fildStr[10]);
			record.setValue(fildStr[11]);
			
			if(fildStr[12].equals("Y"))
				record.setError(true);
			else
				record.setError(false);
			
			record.setPrintted(false);
			
			return record;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	public void Test() throws SQLException
	{

        //创建一个JDBC连接  
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);  

        //创建Table  
        TableUtils.createTableIfNotExists(connectionSource, Record.class);  
  
  
        //实例化一个DAO,对表进行数据操作  
        Dao<Record, Integer> dao = DaoManager.createDao(connectionSource, Record.class);  
  
        //添加两条条数据  
        Record channel1 = new Record(new Timestamp(System.currentTimeMillis()), "7", "ctni", false);  
  
        dao.create(channel1);  
  
        Record channel2 = new Record(new Timestamp(System.currentTimeMillis()), "8", "ctni", false);  
  
        dao.create(channel2);  
  
  
        //查询一条数据  
  
        Record channel = dao.queryForId(1);  
        System.out.println(channel.getSampleid());
        channel.setSampleid("changed2");
        dao.update(channel);
        channel = dao.queryForId(1);
        System.out.println(channel.getSampleid());
        
        System.out.println( dao.countOf());
  
  
//        //删除一条记录  
//        dao.deleteById(2);  
//        dao.delete(channel);  
  
  
        //更改一条记录;  
//        Record.setIconUrl("http://sssss");  
//        dao.update(channel);  
  
        //按条件查询多条记录并分页并倒序 这里用到QueryBuilder  
        QueryBuilder<Record, Integer> queryBuilder = dao.queryBuilder();  
  
        queryBuilder.where().eq("SampleId", "333").or().eq("SampleId", "444");  
        queryBuilder.orderBy("id", false);
        queryBuilder.limit((long) 10);  
  
        List<Record> channels = dao.query(queryBuilder.prepare());  
  
        for (Record channel3 : channels) {  
        	System.out.println(channel.getSampleid()); 
        }
	}
}
