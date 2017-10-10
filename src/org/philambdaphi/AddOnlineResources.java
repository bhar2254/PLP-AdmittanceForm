package org.philambdaphi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class AddOnlineResources 
{
	private static String soberShiftPNG = "https://raw.githubusercontent.com/bhar2254/PLP-AdmittanceForm/master/res/soberShiftPNG.png";
	
	public static void addImages()
	{
		File file = new File(SoberForm.fileDir + "\\images\\soberShift.png");
		
		if(!file.exists())
	    {
			System.out.println("Making new PNG");
			try {
				// lets say saving the png file from google.
				URL url = new URL(soberShiftPNG);
				URLConnection urlConnection = url.openConnection();
	
				// creating the input stream from google image
				BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
				// my local file writer, output stream
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(SoberForm.fileDir + "\\images\\soberShift.png" ));
	
				// until the end of data, keep saving into file.
				int i;
				while ((i = in.read()) != -1) {
				    out.write(i);
				}
				out.flush();
	
				// closing all the shits
				out.close();
				in.close();
			} catch (IOException e) {
				
			} 
	    } else {
			System.out.println("PNG Already Exists!");
		}
	}
}
