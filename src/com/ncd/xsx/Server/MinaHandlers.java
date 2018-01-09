package com.ncd.xsx.Server;

import java.io.IOException;
import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncd.xsx.Dao.RecordDao;
import com.ncd.xsx.Dao.ReportDao;
import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Define.StringDefine;
import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Tools.Sqlite3Tool;

public class MinaHandlers {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	//handle device request
	public void DeviceHandler(IoSession session, String message) {
		Record record = Sqlite3Tool.getInstance().ConvertStringToRecord(message);
		if(record != null)
		{
			StringBuffer stringBuffer = new StringBuffer();
			System.out.println("net recv success");
			
			stringBuffer.setLength(0);
			stringBuffer.append("success&");
			stringBuffer.append(record.getPihao());
			stringBuffer.append('&');
			stringBuffer.append(record.getPinum());
			
			if(RecordDao.getInstance().saveRecord(record))
				session.write(stringBuffer.toString());
			else
				session.write("fail");
		}
		else {
			System.out.println("net recv fail");
			session.write("fail");
		}
	}
	
	//handle client request
	public void ClientSummyHandler(IoSession session, String message) {
		
		try {
			RequestObject requestObject = mapper.readValue(message, RequestObject.class);
			ResponseObject responseObject = new ResponseObject(requestObject.getMethodName(), requestObject.getRequestId(), null, null, null, null, null);
			
			switch (requestObject.getMethodName()) {

				case StringDefine.QueryRecordUrl:
					
					RecordDao.getInstance().queryRecordByParms(requestObject, responseObject);
					break;
				
				case StringDefine.SaveReportUrl:
					
					if(ReportDao.getInstance().saveReport(requestObject.getReport()))
						responseObject.setResult(StringDefine.SuccessStr);
					else {
						responseObject.setResult(StringDefine.FailStr);
					}
					break;
					
				default:

					break;
			}
			String string = mapper.writeValueAsString(responseObject);

			session.write(string);
			System.out.println("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.write("fail");
			System.out.println("fail");
		}
	}
}
