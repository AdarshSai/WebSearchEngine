package SpiderPackage;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;

public class webCrawlerUI {
	public static String[] returnListOfString = new String[3];
	JFrame frame;
	public static JTextField textField_2;
	public static boolean FlagProceed;
	public static JTextField textField_4;
	public static JTextArea textArea, textArea_1, textArea_2;
	public static JProgressBar progressBar, progressBar_1, progressBar_2;
	public static JTextField textField_3;
	public static JTextField textField_5;
	public static JTextField textField_6;
	public static JScrollPane scrollPane, scrollPane1, scrollPane2;
public static JTextField textField_7;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					webCrawlerUI window = new webCrawlerUI();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public webCrawlerUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static String[] returnTheList() {
		return returnListOfString;
	}

	public static void setTextArea(String s) {

	}

	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 700, 560);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		
//		
//		JLabel lblEnterTheUrl = new JLabel("Url");
//		
//		lblEnterTheUrl.setBounds(65, 68, 46, 14);
//	    frame.getContentPane().add(lblEnterTheUrl);
//	     
//	    JTextField textField_1 = new JTextField();
//	    textField_1.setBounds(128, 65, 86, 20);
//	    frame.getContentPane().add(textField_1);
//	    textField_1.setColumns(10);
//	    
//	    JLabel lblEmailId = new JLabel("Search String");
//	    lblEmailId.setBounds(65, 115, 46, 14);
//	    frame.getContentPane().add(lblEmailId);
//	     
//	    JTextField textField_2 = new JTextField();
//	    textField_2.setBounds(128, 112, 247, 17);
//	    frame.getContentPane().add(textField_2);
//	    textField_2.setColumns(10);
//	    JButton btnClear = new JButton("Clear");
//	     
//	    btnClear.setBounds(312, 387, 89, 23);
//	    frame.getContentPane().add(btnClear);
//	  
//	    JButton btnSubmit = new JButton("Submit");
//	     
//	  
//	    btnSubmit.setBounds(65, 387, 89, 23);
//	    frame.getContentPane().add(btnSubmit);
//	    btnClear.addActionListener(new SelectionAdapter()
//	    {
//	      @Override
//	      public void widgetSelected(SelectionEvent e)
//	      {
//	        textField_1.setText("");
//	        textField_2.setText("");
//	       }
//	    });

		frame = new JFrame();
		frame.setBounds(100, 100, 730, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTextField textField = new JTextField();
		textField.setBounds(128, 28, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblName = new JLabel("Crawl");
		lblName.setBounds(65, 31, 46, 14);
		frame.getContentPane().add(lblName);

		JLabel lblPhone = new JLabel("URL:");
		lblPhone.setBounds(65, 68, 46, 14);
		frame.getContentPane().add(lblPhone);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(128, 65, 172, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblSex = new JLabel("SearchString:");
		lblSex.setBounds(34, 108, 99, 20);
		frame.getContentPane().add(lblSex);

		textField_2 = new JTextField();
		textField_2.setBounds(128, 112, 172, 17);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);



		JButton btnClear = new JButton("Clear");

		btnClear.setBounds(419, 406, 89, 23);
		frame.getContentPane().add(btnClear);

	

		JButton btnSubmit = new JButton("submit");

		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(211, 406, 89, 23);
		frame.getContentPane().add(btnSubmit);

		// JScrollPane scrollPane1 = new JScrollPane(textField_3);
		// frame.getContentPane().add(scrollPane1);
		JLabel label = new JLabel("Result:");
		label.setBounds(34, 138, 46, 14);
		frame.getContentPane().add(label);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if(textField.getText().equalsIgnoreCase("yes")||textField.getText().equalsIgnoreCase("Y")) {
				if ((textField.getText().isEmpty()) || (textField_2.getText().isEmpty())) {

					JOptionPane.showMessageDialog(null, "Data Missing");
					System.exit(1);
				} else
					System.out.println(textField.getText());
				returnListOfString[0] = textField.getText();
				returnListOfString[1] = textField_1.getText();
				returnListOfString[2] = textField_2.getText();
				FlagProceed = true;
				JOptionPane.showMessageDialog(null, "Data Submitted");
			}
		});
		// textField_3.setText("VAndhu kandipudra");

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(463, 94, 237, 36);
		frame.getContentPane().add(textField_4);

		JLabel label_1 = new JLabel("Synonyms:");
		label_1.setBounds(333, 113, 99, 14);
		frame.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("Phone Number:");
		label_2.setBounds(254, 138, 46, 14);
		frame.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("Email ID:");
		label_3.setBounds(489, 137, 46, 14);
		frame.getContentPane().add(label_3);

		textArea = new JTextArea();
		textArea.setBounds(12, 196, 202, 197);
		frame.getContentPane().add(textArea);

		textArea_1 = new JTextArea();
		textArea_1.setBounds(248, 196, 184, 197);
		frame.getContentPane().add(textArea_1);

		textArea_2 = new JTextArea();
		textArea_2.setBounds(478, 196, 202, 197);
		frame.getContentPane().add(textArea_2);

		progressBar = new JProgressBar();
		progressBar.setBounds(12, 165, 146, 14);
		frame.getContentPane().add(progressBar);

		progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(238, 169, 164, 14);
		frame.getContentPane().add(progressBar_1);

		progressBar_2 = new JProgressBar();
		progressBar_2.setBounds(478, 169, 164, 14);
		frame.getContentPane().add(progressBar_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(170, 160, 35, 23);
		frame.getContentPane().add(textField_3);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(414, 165, 35, 23);
		frame.getContentPane().add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(654, 165, 35, 23);
		frame.getContentPane().add(textField_6);

		JLabel label_4 = new JLabel("Time:");
		label_4.setBounds(168, 137, 46, 14);
		frame.getContentPane().add(label_4);

		JLabel label_5 = new JLabel("Time:");
		label_5.setBounds(409, 137, 46, 14);
		frame.getContentPane().add(label_5);

		JLabel label_6 = new JLabel("Time:");
		label_6.setBounds(654, 137, 46, 14);
		frame.getContentPane().add(label_6);

		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(12, 196, 202, 197);
		frame.getContentPane().add(scrollPane_1);

		JScrollPane scrollPane_2 = new JScrollPane(textArea_1);
		scrollPane_2.setBounds(248, 196, 184, 197);
		frame.getContentPane().add(scrollPane_2);

		JScrollPane scrollPane_3 = new JScrollPane(textArea_2);
		scrollPane_3.setBounds(478, 196, 202, 197);
		frame.getContentPane().add(scrollPane_3);
		
		JLabel label_7 = new JLabel("Suggestions based ED:");
		label_7.setBounds(333, 49, 99, 14);
		frame.getContentPane().add(label_7);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(463, 28, 237, 57);
		frame.getContentPane().add(textField_7);
		

		textArea.setVisible(true);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText(null);
				// textField_2.setText(null);
				textField.setText(null);
				// textArea_1.setText(null);
//				radioButton.setSelected(false);
//				radioButton_1.setSelected(false);
//				comboBox.setSelectedItem("Select");

			}
		});

	}
}
