package com.ncd.xsx.Server;

import org.hid4java.HidDevice;

import com.ncd.xsx.Dao.RecordDao;
import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Tools.Sqlite3Tool;

public class UsbDeviceThread extends Thread {
	
	private HidDevice hidDevice = null;
	
	private byte data[] = new byte[1024];
	private int recvLen = 0;
	private byte tempdata[] = new byte[64];
	
	private StringBuffer recvStringBuffer = new StringBuffer();
	
	public UsbDeviceThread(String name) {
		super.setName(name);
		this.start();
	}

	public HidDevice getHidDevice() {
		return hidDevice;
	}

	public void setHidDevice(HidDevice hidDevice) {
		this.hidDevice = hidDevice;
	}

	public boolean openDevice() {
		try {
			if(!hidDevice.isOpen())
				hidDevice.open();
			System.out.format("Device PID: %d Open\r\n", hidDevice.getProductId());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public void closeDevice() {
		try {
			if(hidDevice.isOpen())
				hidDevice.close();
			
			System.out.format("Device PID: %d Close\r\n", hidDevice.getProductId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		int readNum = 0;
		
		while (true) 
		{
			
			if(hidDevice != null) 
			{
				if(hidDevice.isOpen()) 
				{
					readNum = hidDevice.read(tempdata, 10000);

					if(readNum > 0)
					{
						String dataStr = new String(tempdata);
						
						if(dataStr.startsWith("AA"))
						{
							recvLen = 0;
							recvStringBuffer.setLength(0);
						}
						
						System.arraycopy(tempdata, 0, data, recvLen, 64);
						recvLen += 64;
						//recvStringBuffer.append(new String(data));
						
						if(dataStr.indexOf("BB") > -1) {

							dataStr = new String(data);
							
							Record record = Sqlite3Tool.getInstance().ConvertStringToRecord(dataStr);
							
							if(record != null)
							{
								if(RecordDao.getInstance().saveRecord(record))
								{
									recvStringBuffer.setLength(0);
									recvStringBuffer.append("success&");
									recvStringBuffer.append(record.getPihao());
									recvStringBuffer.append('&');
									recvStringBuffer.append(record.getPinum());
									hidDevice.write(recvStringBuffer.toString().getBytes(), 64, (byte) 0x00);
								}
							}
							else
								System.out.println("split error");
						}
					}
					else if (readNum == 0) {
						System.out.format("Device PID: %d recv len 0\r\n", hidDevice.getProductId());
					}
					else if (readNum == -1) {
						System.out.println("recv error");
						closeDevice();
					}
				}
				else 
				{	
					openDevice();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	    }
	}
}
