package org.philambdaphi.tests;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class WriteFile {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException 
	{
		String userName = System.getProperty("user.name");
		
		PrintWriter writer = new PrintWriter("C:\\Users\\" + userName + "\\Desktop\\SoberShift.txt", "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
	}

}
