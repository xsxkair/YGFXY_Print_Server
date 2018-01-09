package com.ncd.xsx.Lunch;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;

import com.ncd.xsx.Define.DataConfig;
import com.ncd.xsx.Server.HidUsbServer;
import com.ncd.xsx.Server.MinaServer;
import com.ncd.xsx.Tools.Sqlite3Tool;

public class Lunch{

	private DataConfig dataConfig = null;
	private MinaServer minaServer = null;
	private HidUsbServer hidUsbServer = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Lunch().startApplication();
	}
	
	private void startApplication() {
		Boolean isSuccessStartted = false;
		dataConfig = new DataConfig();
		minaServer = new MinaServer();
		hidUsbServer = new HidUsbServer();

		Properties pps = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream("Config.properties");
			pps.load(fileInputStream);
			
			dataConfig.setNcdport(Integer.valueOf(pps.getProperty("NCDPort")));
			dataConfig.setPoctserverip(pps.getProperty("PoctServerIp"));
			dataConfig.setPoctusername(pps.getProperty("PoctServerUserName"));
			dataConfig.setPoctpassword(pps.getProperty("PoctServerPassword"));
			dataConfig.setPoctpath(pps.getProperty("PoctServerPath"));
				
			Sqlite3Tool.getInstance().SqliteDaoInit();
			
			hidUsbServer.startUsbServer();
				
			isSuccessStartted = minaServer.startMinaServer();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			isSuccessStartted = false;
		}
		
		if (SystemTray.isSupported())
		{
	         // 获得Image对象
			Image image = null;
			String toolTipString = null;
			
			if(isSuccessStartted)
			{
				image = Toolkit.getDefaultToolkit().getImage(Lunch.class.getResource("/RES/logoOK.png"));
				toolTipString = "Server OK";
			}
			else
			{
				image = Toolkit.getDefaultToolkit().getImage(Lunch.class.getResource("/RES/logoError.png"));
				toolTipString = "Server Error";
			}
			
			//3.弹出菜单popupMenu  
            PopupMenu popMenu = new PopupMenu();   
            MenuItem itmExit = new MenuItem("退出");  
            itmExit.addActionListener(new ActionListener(){  
            	public void actionPerformed(ActionEvent e) {  
                    System.exit(0);  
                }  
            });  
            popMenu.add(itmExit); 
			
	         TrayIcon icon = new TrayIcon(image, toolTipString, popMenu);
	         
	         // 获得系统托盘对象
	         SystemTray systemTray = SystemTray.getSystemTray();
	         try
	         {
	            // 为系统托盘加托盘图标
	            systemTray.add(icon);
	         }
	         catch (Exception e)
	         {
	            e.printStackTrace();
	         }
		}
		else
			System.out.println("not surport");
	}
}
