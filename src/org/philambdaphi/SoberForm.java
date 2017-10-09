package org.philambdaphi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.trolltech.qt.gui.*;

public class SoberForm extends QWidget {

    private QLineEdit orgLineEdit;
    private QComboBox orgComboBox;
    private static String orgIndex[] = new String[5];
    private int orgCount = 5;
    private QCheckBox soberCheckBox;

    public static void main(String args[]) 
    {
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
        
        QGridLayout orgLayout = new QGridLayout();
        orgLayout.addWidget(orgLabel, 0, 0);
        orgLayout.addWidget(orgComboBox, 0, 1);
        orgLayout.addWidget(soberCheckBox, 4, 0);
        orgLayout.addWidget(orgLabelName, 2, 0);
        orgLayout.addWidget(submit, 5, 1);
        orgLayout.addWidget(orgLineEdit, 3, 0, 1, 2);
        orgGroup.setLayout(orgLayout);

        QGridLayout layout = new QGridLayout();
        layout.addWidget(orgGroup, 0, 0);
        setLayout(layout);
        submit.clicked.connect(this, "submit()");
        
        setWindowTitle(tr("PLP Sober Form"));
    }
    
    public void submit()
    {
    	writeFile();
    	orgLineEdit.setText("");
    	soberCheckBox.setChecked(false);
    }
    
    public static void setupIndexArray()
    {
    	orgIndex[0] = "PLP";
    	orgIndex[1] = "ASA";
    	orgIndex[2] = "DX";
    	orgIndex[3] = "N/A";
    	orgIndex[4] = "Unknown";
    }
    
    public void writeFile()
    {
    	String userName = System.getProperty("user.name");
    	String fileName = "C:\\Users\\" + userName + "\\Desktop\\SoberShift.txt";
    	String fileNameShort = "C:\\Users\\" + userName + "\\Desktop\\SoberShiftNames.txt";
		
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
        	
        	pw.print(orgLineEdit.text() + " | ");
    		pw.print("Sober: " + soberCheckBox.isChecked() + " | ");
    		pw.print(orgIndex[orgComboBox.currentIndex()]);
        	
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
        	
        	pw.print(orgLineEdit.text());
        	
        	pw.close();
        	
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
}