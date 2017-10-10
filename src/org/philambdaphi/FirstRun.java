package org.philambdaphi;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;

public class FirstRun extends QWidget
{

private static String userName = System.getProperty("user.name");
private static String rootDir = "C:\\Users\\" + userName + "\\Desktop\\";
static String fileDir = rootDir + "SoberShift\\";
private QTextEdit introText;

	public static void run(String [] args)
	{
    	AddOnlineResources.addImages();
    	
    	QApplication.initialize(args);

        FirstRun firstRun = new FirstRun();
        firstRun.show();

        QApplication.instance().exec();
	}
	
	public FirstRun()
	{
		this(null);
	}
	
	public FirstRun(QWidget parent) {
        super(parent);
        
        QGroupBox firstRun = new QGroupBox(tr("Your first time with SoberShift"));
        introText = new QTextEdit();
        introText.setReadOnly(true);
        introText.setText("This appears to be your first time running my program! "
        		+ "I'm glad you've decided to test out SoberShift " + SoberForm.version
        		+ " and I hope you're enjoy it very much!");
        introText.append("");
        introText.append("The goal of this program is to make taking attendance at"
        		+ "any IFC party a little easier and give fraternities a better way "
        		+ "to collect data about who's going to their parties and when.");
        introText.append("");
        introText.append("To see the files that are created with SoberShift you can "
        		+ "check the file that should be created on your desktop folder after "
        		+ "you close this prompt. ");
        introText.append("");
        introText.append("To populate the list of organizations that you want to add "
        		+ "to the combo wheel all you have to do is open SoberShift -> bin -> "
        		+ "organizations.txt and fill that with whichever orgs you would like to "
        		+ "allow at your party. By default the file should contain both N/A (Non-Affiliated) "
        		+ "and UnK (membership unknown), but you can add more if you wish!");
        introText.append("");
        introText.append("I hope you enjoy SoberShift " + SoberForm.version + "!");
        introText.append("");
        introText.append("   - Blaine Harper");
        QPushButton okay = new QPushButton(tr("Okay"));
        okay.clicked.connect(QApplication.instance(), "exit()");

        QGridLayout runLayout = new QGridLayout();
        runLayout.addWidget(introText, 0, 0);
        firstRun.setLayout(runLayout);

        QGridLayout layout = new QGridLayout();
        layout.addWidget(firstRun, 0, 0);
        layout.addWidget(okay, 1, 0);
        
        setLayout(layout);
        
        setWindowTitle(tr(SoberForm.windowTitle));
        setWindowIcon(new QIcon(fileDir+"images/soberShift.png"));
    }
}
