package org.philambdaphi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock 
{
	public static String getTime()
	{
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
	
	public static String getDate()
	{
		String timeStamp = new SimpleDateFormat("yy-MM-dd").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
}
