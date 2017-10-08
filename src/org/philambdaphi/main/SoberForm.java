package org.philambdaphi.main;

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
    	System.out.println("Submitted!\n");
    	System.out.println(orgLineEdit.text());
    	System.out.println("Sober: " + soberCheckBox.isChecked());
    	System.out.println(orgIndex[orgComboBox.currentIndex()]);
    }
    
    public static void setupIndexArray()
    {
    	orgIndex[0] = "PLP";
    	orgIndex[1] = "ASA";
    	orgIndex[2] = "DX";
    	orgIndex[3] = "N/A";
    	orgIndex[4] = "Unknown";
    }
}