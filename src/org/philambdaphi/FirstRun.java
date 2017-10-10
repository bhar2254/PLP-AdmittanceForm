package org.philambdaphi;

import com.trolltech.qt.gui.QWidget;

public class FirstRun extends QWidget
{
public static QWidget firstRunWindow;

	public static void run()
	{
    	AddOnlineResources.addImages();
    	
    	firstRunWindow = new QWidget();   
    	
    	firstRunWindow.show();
	}
}
