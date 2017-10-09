package org.philambdaphi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.trolltech.qt.gui.*;

public class SoberForm extends QWidget {

    private QLineEdit orgLineEdit, orgOutput;
    private static String userName = System.getProperty("user.name");
    private static String rootDir = "C:\\Users\\" + userName + "\\Desktop\\";
    private static String fileDir = rootDir + "SoberShift\\";
    private QComboBox orgComboBox;
    private static String orgIndex[] = new String[64];
    private static int orgCount = 0;
    private QCheckBox soberCheckBox;

    public static void main(String args[]) 
    {
    	setUpDirectory();
    	
        setupIndexArray();
        QApplication.initialize(args);

        SoberForm lineedits = new SoberForm();
        lineedits.show();

        QApplication.instance().exec();
    }

    public SoberForm() {
        this(null);
    }

    public SoberForm(QWidget parent) {
        super(parent);
        
        QGroupBox orgGroup = new QGroupBox(tr("Phi Lambda Phi Sign-in"));
        QLabel orgLabel = new QLabel(tr("Organization: "));
        orgComboBox = new QComboBox();
        for(int i=0; i < orgCount; i++)
        {
        	orgComboBox.addItem(tr(orgIndex[i]));
        }
        soberCheckBox = new QCheckBox(tr("Sober?"));
        QLabel orgLabelName = new QLabel(tr("Name: "));
        QPushButton submit = new QPushButton(tr("Submit")); 
        orgLineEdit = new QLineEdit();
        
        QGroupBox outGroup = new QGroupBox(tr("Output:"));
        orgOutput = new QLineEdit();
        
        QGridLayout orgLayout = new QGridLayout();
        orgLayout.addWidget(orgLabel, 0, 0);
        orgLayout.addWidget(orgComboBox, 0, 1);
        orgLayout.addWidget(soberCheckBox, 4, 0);
        orgLayout.addWidget(orgLabelName, 2, 0);
        orgLayout.addWidget(submit, 5, 1);
        orgLayout.addWidget(orgLineEdit, 3, 0, 1, 2);
        orgGroup.setLayout(orgLayout);
        
        QGridLayout outLayout = new QGridLayout();
        outLayout.addWidget(orgOutput, 6, 0, 1, 2);
        outGroup.setLayout(outLayout);
        orgOutput.setReadOnly(true);

        QGridLayout layout = new QGridLayout();
        layout.addWidget(orgGroup, 0, 0);
        layout.addWidget(outGroup, 6, 0);
        
        setLayout(layout);
        
        submit.clicked.connect(this, "submit()");
        
        setWindowTitle(tr("PLP Sober Form"));
        setWindowIcon(new QIcon("images/soberShift.png"));
    }
    
    public void submit()
    {
    	writeFile();
    	orgLineEdit.setText("");
    	soberCheckBox.setChecked(false);
    }
    
    public static void setupIndexArray()
    {
    	BufferedReader br = null;
		FileReader fr = null;
		
		try {
			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileDir + "Organizations.txt");
			br = new BufferedReader(fr);
			String sCurrentLine;
			int i=0;
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				System.out.println(sCurrentLine + i);
				orgIndex[i]=sCurrentLine;	
				i++;
			}
			orgCount = i;
			br.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
		/*
    	orgIndex[0] = "PLP";
    	orgIndex[1] = "ASA";
    	orgIndex[2] = "DX";
    	orgIndex[3] = "N/A";
    	orgIndex[4] = "Unknown";
    	*/
    }
    
    public void writeFile()
    {
    	String fileName = fileDir + "FullLogs.txt";
    	String fileNameShort = fileDir + "Names.txt";
		
    	try
    	{
            //Specify the file name and path here
        	File file = new File(fileName);

        	/* This logic is to create the file if the
        	 * file is not already present
        	 */
        	if(!file.exists())
        	{
				file.createNewFile();
        	}

        	//Here true is to append the content to file
        	FileWriter fw = new FileWriter(file,true);
        	//BufferedWriter writer give better performance
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter pw = new PrintWriter(bw);
        	//This will add a new line to the file content
        	pw.println("");
        	/* Below three statements would add three 
        	 * mentioned Strings to the file in new lines.
        	 */
        	
        	if(soberCheckBox.isChecked())
    		{
        		pw.print(orgIndex[orgComboBox.currentIndex()] + " | " + orgLineEdit.text() + " (Sober)");
    		} else {
    			pw.print(orgIndex[orgComboBox.currentIndex()] + " | " + orgLineEdit.text());
    		}
        	
    		if(soberCheckBox.isChecked())
    		{
        		orgOutput.setText(orgIndex[orgComboBox.currentIndex()] + " | " + orgLineEdit.text() + " (Sober)");
    		} else {
        		orgOutput.setText(orgIndex[orgComboBox.currentIndex()] + " | " + orgLineEdit.text());
    		}
    		
        	pw.close();
        	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try
    	{
            //Specify the file name and path here
        	File file = new File(fileNameShort);

        	/* This logic is to create the file if the
        	 * file is not already present
        	 */
        	if(!file.exists())
        	{
				file.createNewFile();
        	}

        	//Here true is to append the content to file
        	FileWriter fw = new FileWriter(file,true);
        	//BufferedWriter writer give better performance
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter pw = new PrintWriter(bw);
        	//This will add a new line to the file content
        	pw.println("");
        	/* Below three statements would add three 
        	 * mentioned Strings to the file in new lines.
        	 */
        	if(soberCheckBox.isChecked())
    		{
        		pw.print(orgLineEdit.text() + " (Sober)");
    		} else {
    			pw.print(orgLineEdit.text());
    		}
        	
        	pw.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setUpDirectory()
    {
    	File theDir = new File(rootDir + "SoberShift");

    	// if the directory does not exist, create it
    	if (!theDir.exists()) {
    	    System.out.println("creating directory: " + theDir.getName());
    	    boolean result = false;

    	    try{
    	        theDir.mkdir();
    	        result = true;
    	    } 
    	    catch(SecurityException se){
    	        //handle it
    	    }        
    	    if(result) {    
    	        System.out.println("DIR created");  
    	    }
    	}
    	
//    	This will check for the org file and make a new one if there isn't one
    	
    	File file = new File(fileDir + "Organizations.txt");
		
		if(!file.exists())
    	{
			try {
				file.createNewFile();
				//Here true is to append the content to file
	        	FileWriter fw = new FileWriter(file,true);
	        	//BufferedWriter writer give better performance
	        	BufferedWriter bw = new BufferedWriter(fw);
	        	PrintWriter pw = new PrintWriter(bw);
	        	//This will add a new line to the file content
	        	pw.println("N/A");
	        	/* Below three statements would add three 
	        	 * mentioned Strings to the file in new lines.
	        	 */
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}