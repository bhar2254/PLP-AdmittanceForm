package org.philambdaphi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.trolltech.qt.gui.*;

public class SoberForm extends QWidget 
{
	public static final String version = "a1.0.2";
	public static final String windowTitle = "Blaine Harper's SoberShift (" + version +")";	
	public static boolean firstRun = true;
	
    private QLineEdit formLineEdit, orgOutput;
    
    private QComboBox formComboBox;
    private QCheckBox soberCheckBox;
    private static QTextEdit attendanceBox;
    
    private static String userName = System.getProperty("user.name");
    private static String rootDir = "C:\\Users\\" + userName + "\\Desktop\\";
    static String fileDir = rootDir + "SoberShift\\";
    
    private static String orgIndex[] = new String[64];
    private static int orgCount = 0;
    
//    Some strings for filepaths to make this easier
    public static String namesFile = fileDir + "names.txt",
    					attendanceFile = fileDir + "\\bin\\attendance.ssf",
    					orgsFile = fileDir + "\\bin\\organizations.txt",
    					fullLogsFile = fileDir + "fullLogs.txt";
    
    public static void main(String args[]) 
    {
    	setUpDirectory();
        
        if(firstRun)
        	FirstRun.run(args);
        
        setupIndexArray();
        
        QApplication.initialize(args);

        SoberForm soberForm = new SoberForm();
        soberForm.show();
        populateAttendance();

        QApplication.instance().exec();
    }

    public SoberForm() {
        this(null);
    }

    public SoberForm(QWidget parent) {
        super(parent);
        
        QGroupBox orgGroup = new QGroupBox(tr("Phi Lambda Phi Sign-in"));
        QLabel formLabel = new QLabel(tr("Organization: "));
        formComboBox = new QComboBox();
        for(int i=0; i < orgCount; i++)
        {
        	formComboBox.addItem(tr(orgIndex[i]));
        }
        soberCheckBox = new QCheckBox(tr("Sober?"));
        QLabel orgLabelName = new QLabel(tr("Name: "));
        QPushButton submit = new QPushButton(tr("Submit")); 
        formLineEdit = new QLineEdit();
        
        QGroupBox outGroup = new QGroupBox(tr("Output:"));
        orgOutput = new QLineEdit();

        QGroupBox resetGroup = new QGroupBox(tr("Reset:"));
        QPushButton resetButton = new QPushButton(tr("Reset"));

        QGroupBox attendeeGroup = new QGroupBox(tr("Attendance:"));
        attendanceBox = new QTextEdit();
        
        QGridLayout formLayout = new QGridLayout();
        formLayout.addWidget(formLabel, 0, 0);
        formLayout.addWidget(formComboBox, 0, 1);
        formLayout.addWidget(soberCheckBox, 4, 0);
        formLayout.addWidget(orgLabelName, 2, 0);
        formLayout.addWidget(submit, 5, 2);
        formLayout.addWidget(formLineEdit, 3, 0, 1, 3);
        orgGroup.setLayout(formLayout);
        
        QGridLayout outLayout = new QGridLayout();
        outLayout.addWidget(orgOutput, 0, 0, 1, 2);
        outGroup.setLayout(outLayout);
        orgOutput.setReadOnly(true);
        
        QGridLayout resetLayout = new QGridLayout();
        resetLayout.addWidget(resetButton, 0, 0, 1, 2);
        resetGroup.setLayout(resetLayout);
        
        QGridLayout attendeeLayout = new QGridLayout();
        attendeeGroup.setLayout(attendeeLayout);
        attendeeLayout.addWidget(attendanceBox, 0, 0);
        attendanceBox.setReadOnly(true);

        QGridLayout layout = new QGridLayout();
        layout.addWidget(orgGroup, 0, 0);
        layout.addWidget(outGroup, 1, 0);
        layout.addWidget(attendeeGroup, 0, 1);
        layout.addWidget(resetGroup, 1, 1);
        
        setLayout(layout);

        submit.clicked.connect(this, "submit()");
        resetButton.clicked.connect(this, "reset()");
        
        setWindowTitle(tr(windowTitle));
        setWindowIcon(new QIcon(fileDir+"images/soberShift.png"));
    }
    
    public void submit()
    {
    	writeFile();
    	formLineEdit.setText("");
    	soberCheckBox.setChecked(false);
    }
    
    public void reset()
    {
		try {
			PrintWriter writer = new PrintWriter(fullLogsFile, "UTF-8");
			writer.println("");
			writer.close();
			
			writer = new PrintWriter(namesFile, "UTF-8");
			writer.println("");
			writer.close();
			
			writer = new PrintWriter(attendanceFile, "UTF-8");
			writer.println("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		attendanceBox.setText("");
		formLineEdit.setText("");
		orgOutput.setText("All files reset to default!");
    }
    
    public static void setupIndexArray()
    {
    	BufferedReader br = null;
		FileReader fr = null;
		
		try {
			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(orgsFile);
			br = new BufferedReader(fr);
			String sCurrentLine;
			int i=0;
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				orgIndex[i]=sCurrentLine;	
				i++;
			}
			orgCount = i;
			br.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
    }
    
    public void writeFile()
    {		
    	try
    	{
            //Specify the file name and path here
        	File file = new File(fullLogsFile);

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
        	
        	String fullLogsText = "[" + Clock.getTime() + "] | " + orgIndex[formComboBox.currentIndex()] + " | " + formLineEdit.text();
        	
        	if(soberCheckBox.isChecked())
    		{
        		pw.print(fullLogsText + " (Sober)");
    		} else {
    			pw.print(fullLogsText);
    		}
        	
    		if(soberCheckBox.isChecked())
    		{
        		orgOutput.setText(orgIndex[formComboBox.currentIndex()] + " | " + formLineEdit.text() + " (Sober)");
    		} else {
        		orgOutput.setText(orgIndex[formComboBox.currentIndex()] + " | " + formLineEdit.text());
    		}
    		
        	pw.close();
        	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try
    	{
            //Specify the file name and path here
        	File file = new File(namesFile);

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
        		pw.print(formLineEdit.text() + " (Sober)");
    		} else {
    			pw.print(formLineEdit.text());
    		}
        	
        	pw.close();

//        	This will write everything for the attendance box 
//        	and work on refreshing the box every time content
//        	is submitted
        	
        	file = new File(attendanceFile);
        	if(!file.exists())
        	{
				file.createNewFile();
        	}
        	fw = new FileWriter(file,true);
        	bw = new BufferedWriter(fw);
        	pw = new PrintWriter(bw);
        	pw.println("");
        	if(soberCheckBox.isChecked())
    		{
        		attendanceBox.append("[" + Clock.getTime() + "] " + formLineEdit.text() + " (Sober)");
        		pw.print("[" + Clock.getTime() + "] " + formLineEdit.text() + " (Sober)");
    		} else {
    			attendanceBox.append("[" + Clock.getTime() + "] " + formLineEdit.text());
    			pw.print("[" + Clock.getTime() + "] " + formLineEdit.text());
    		}
        	
        	pw.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void makeNewDir(String dir)
    {
    	File theDir = new File(dir);

//    	If the directory does not exist, create it
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
    }
    
    public static void makeNewFile(String filePath, String defaultText)
    {
    	File file = new File(filePath);
		
		if(!file.exists())
    	{
			try {
				file.createNewFile();
//				Here true is to append the content to file
	        	FileWriter fw = new FileWriter(file,true);
//	        	BufferedWriter writer give better performance
	        	BufferedWriter bw = new BufferedWriter(fw);
	        	PrintWriter pw = new PrintWriter(bw);
//	        	This will add a new line to the file content
	        	pw.println(defaultText);
//	        	Below three statements would add three 
//	        	mentioned Strings to the file in new lines.

				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static void setUpDirectory()
    {
    	File file = new File(rootDir + "SoberShift");
    	if(!file.exists())
    		firstRun = true;
    	
    	if(firstRun)
    		System.out.println("First RUN!");
    	
    	makeNewDir(rootDir + "SoberShift");
    	makeNewDir(fileDir + "images");
    	makeNewDir(fileDir + "bin");
    	makeNewFile(orgsFile,"N/A\n"
    			+ "UnK");
    	makeNewFile(attendanceFile, "");
    }
    
    public static void populateAttendance()
    {
    	BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(attendanceFile);
			br = new BufferedReader(fr);
			
			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				attendanceBox.append(sCurrentLine);	
			}
			br.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
    }
}