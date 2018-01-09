package com.ncd.xsx.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.hid4java.HidDevice;
import org.hid4java.HidException;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.HidServicesSpecification;
import org.hid4java.ScanMode;
import org.hid4java.event.HidServicesEvent;

public class HidUsbServer implements HidServicesListener {
	
	private HidServices hidServices = null;
	
	private static final int NCD_Vid = 0x0911;
	
	private ArrayList<UsbDeviceThread> myUsbDeviceThreadPool = null;
	
	public void startUsbServer () throws HidException
	{
		HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
	    hidServicesSpecification.setAutoShutdown(true);
	    hidServicesSpecification.setScanInterval(500);
	    hidServicesSpecification.setPauseInterval(5000);
	    hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);

	    myUsbDeviceThreadPool = new ArrayList<UsbDeviceThread>();
	    for(int i=0; i<10; i++)
	    	myUsbDeviceThreadPool.add(new UsbDeviceThread(String.valueOf(i)));
	    
	    // Get HID services using custom specification
	    hidServices = HidManager.getHidServices(hidServicesSpecification);
	    hidServices.addHidServicesListener(this);

	    // Start the services
	    hidServices.start();
	    
	    for (HidDevice hidDevice : hidServices.getAttachedHidDevices()) {
	        if(hidDevice.getVendorId() == NCD_Vid)
	        {
	        	for (UsbDeviceThread usbDeviceThread : myUsbDeviceThreadPool) {
					if(usbDeviceThread.getHidDevice() == null)
					{
						usbDeviceThread.setHidDevice(hidDevice);
						break;
					}
				}
	        }
	      }
	}
	
	public void shutDownUsbServer()
	{
		hidServices.shutdown();
	}

	@Override
	public void hidDeviceAttached(HidServicesEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getHidDevice().getVendorId() == NCD_Vid)
        {
        	for (UsbDeviceThread usbDeviceThread : myUsbDeviceThreadPool) {
				if(usbDeviceThread.getHidDevice() == null)
				{
					usbDeviceThread.setHidDevice(arg0.getHidDevice());
					break;
				}
			}
        }
	}

	@Override
	public void hidDeviceDetached(HidServicesEvent arg0) {
		// TODO Auto-generated method stub
		for (UsbDeviceThread usbDeviceThread : myUsbDeviceThreadPool) 
		{
			if(arg0.getHidDevice().equals(usbDeviceThread.getHidDevice()))
			{
				usbDeviceThread.closeDevice();
				usbDeviceThread.setHidDevice(null);
			}
		}
	}

	@Override
	public void hidFailure(HidServicesEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
