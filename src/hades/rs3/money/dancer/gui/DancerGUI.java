package hades.rs3.money.dancer.gui;

import hades.rs3.money.dancer.Dancer;

import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class DancerGUI extends JFrame{
	private JPanel contentPane;
	private JCheckBox chckbxEnableCharacterLogin;
	private JTextField unField;
	private JPasswordField passwordField;
	private JCheckBox chckbxTakeBreaks;
	private JTextField minBreakStartField;
	private JTextField maxBreakStartField;
	private JTextField minBreakLengthField;
	private JTextField maxBreakLengthField;
	private JTextField startOffsetField;
	private JTextField lengthOffsetField;
	private JButton btnStart;
	private JTextField messageField;
	private JTextField pinField;
	private JCheckBox chckbxEnablePinSolving;

	/**
	 * Create the frame.
	 */
	public DancerGUI(Dancer dancer, WindowListener wl){
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(wl);
		setBounds(100, 100, 645, 200);
		contentPane = new JPanel();
		contentPane.setFocusable(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 13, 63, 16);
		contentPane.add(lblUsername);
		
		unField = new JTextField();
		unField.setBounds(80, 10, 191, 22);
		contentPane.add(unField);
		unField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(12, 48, 63, 16);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(80, 45, 191, 22);
		contentPane.add(passwordField);
		
		chckbxEnableCharacterLogin = new JCheckBox("Enable Character Login");
		chckbxEnableCharacterLogin.setBounds(12, 73, 161, 17);
		contentPane.add(chckbxEnableCharacterLogin);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(279, 7, 2, 146);
		contentPane.add(separator);
		
		chckbxTakeBreaks = new JCheckBox("Take Breaks");
		chckbxTakeBreaks.setBounds(289, 9, 99, 25);
		contentPane.add(chckbxTakeBreaks);
		
		JLabel lblMinBreakStart = new JLabel("Min Break Start:");
		lblMinBreakStart.setBounds(293, 42, 93, 16);
		contentPane.add(lblMinBreakStart);
		
		minBreakStartField = new JTextField();
		minBreakStartField.setDocument(new IntegerFieldLimit());
		minBreakStartField.setText("240");
		minBreakStartField.setBounds(398, 39, 50, 22);
		contentPane.add(minBreakStartField);
		minBreakStartField.setColumns(10);
		
		JLabel lblMaxBreakStart = new JLabel("Max Break Start:");
		lblMaxBreakStart.setBounds(466, 42, 99, 16);
		contentPane.add(lblMaxBreakStart);
		
		maxBreakStartField = new JTextField();
		maxBreakStartField.setDocument(new IntegerFieldLimit());
		maxBreakStartField.setText("300");
		maxBreakStartField.setBounds(575, 39, 50, 22);
		contentPane.add(maxBreakStartField);
		maxBreakStartField.setColumns(10);
		
		JLabel lblMinBreakLength = new JLabel("Min Break Length:");
		lblMinBreakLength.setBounds(293, 71, 103, 16);
		contentPane.add(lblMinBreakLength);
		
		minBreakLengthField = new JTextField();
		minBreakLengthField.setDocument(new IntegerFieldLimit());
		minBreakLengthField.setText("15");
		minBreakLengthField.setBounds(398, 68, 50, 22);
		contentPane.add(minBreakLengthField);
		minBreakLengthField.setColumns(10);
		
		JLabel lblMaxBreakLength = new JLabel("Max Break Length:");
		lblMaxBreakLength.setBounds(466, 71, 106, 16);
		contentPane.add(lblMaxBreakLength);
		
		maxBreakLengthField = new JTextField();
		maxBreakLengthField.setDocument(new IntegerFieldLimit());
		maxBreakLengthField.setText("45");
		maxBreakLengthField.setBounds(575, 68, 50, 22);
		contentPane.add(maxBreakLengthField);
		maxBreakLengthField.setColumns(10);
		
		JLabel lblStartOffset = new JLabel("Max Start Offset:");
		lblStartOffset.setBounds(293, 100, 99, 16);
		contentPane.add(lblStartOffset);
		
		startOffsetField = new JTextField();
		startOffsetField.setDocument(new IntegerFieldLimit());
		startOffsetField.setText("30");
		startOffsetField.setBounds(398, 97, 50, 22);
		contentPane.add(startOffsetField);
		startOffsetField.setColumns(10);
		
		JLabel lblMaxLengthOffset = new JLabel("Max Length Offset:");
		lblMaxLengthOffset.setBounds(466, 100, 108, 16);
		contentPane.add(lblMaxLengthOffset);
		
		lengthOffsetField = new JTextField();
		lengthOffsetField.setDocument(new IntegerFieldLimit());
		lengthOffsetField.setText("25");
		lengthOffsetField.setBounds(575, 97, 50, 22);
		contentPane.add(lengthOffsetField);
		lengthOffsetField.setColumns(10);
		
		JLabel lblAllTimesAre = new JLabel("All Times Are In Minutes");
		lblAllTimesAre.setBounds(398, 13, 140, 16);
		contentPane.add(lblAllTimesAre);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(528, 128, 97, 25);
		contentPane.add(btnStart);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(12, 100, 56, 16);
		contentPane.add(lblMessage);
		
		messageField = new JTextField();
		messageField.setBounds(80, 97, 191, 22);
		contentPane.add(messageField);
		messageField.setColumns(10);
		
		chckbxEnablePinSolving = new JCheckBox("Enable Pin Solving");
		chckbxEnablePinSolving.setBounds(12, 128, 133, 25);
		contentPane.add(chckbxEnablePinSolving);
		
		JLabel lblBankPin = new JLabel("Bank Pin:");
		lblBankPin.setBounds(153, 132, 56, 16);
		contentPane.add(lblBankPin);
		
		pinField = new JTextField();
		pinField.setBounds(221, 132, 50, 22);
		contentPane.add(pinField);
		pinField.setColumns(10);
		btnStart.addActionListener(new StartPressed(dancer, this));
		contentPane.requestFocus();
	}
	
	public boolean solvePin(){
		return chckbxEnablePinSolving.isSelected();
	}
	
	public String getPin(){
		return pinField.getText();
	}
	
	public String getMessage(){
		return messageField.getText();
	}
	
	public boolean enableLogin(){
		return chckbxEnableCharacterLogin.isSelected();
	}
	
	public String getUserName(){
		return unField.getText();
	}
	
	public char[] getPassword(){
		char pw[] = passwordField.getPassword();
		passwordField.setText("");
		return pw;
	}
	
	public boolean enableBreakHandling(){
		return chckbxTakeBreaks.isSelected();
	}
	
	public int getMinBreakStart(){
		String str = minBreakStartField.getText();
		int ret = -1;
		try{
			ret = Integer.parseInt(str);
		}catch(Exception e){}
		return ret;
	}
	
	public int getMaxBreakStart(){
		String str = maxBreakStartField.getText();
		int ret = -1;
		try{
			ret = Integer.parseInt(str);
		}catch(Exception e){}
		return ret;
	}
	
	public int getMinBreakLength(){
		String str = minBreakLengthField.getText();
		int ret = -1;
		try{
			ret = Integer.parseInt(str);
		}catch(Exception e){}
		return ret;
	}
	
	public int getMaxBreakLength(){
		String str = maxBreakLengthField.getText();
		int ret = -1;
		try{
			ret = Integer.parseInt(str);
		}catch(Exception e){}
		return ret;
	}
	
	public int getStartOffset(){
		String str = startOffsetField.getText();
		int ret = -1;
		try{
			ret = Integer.parseInt(str);
		}catch(Exception e){}
		return ret;
	}
	
	public int getLengthOffset(){
		String str = lengthOffsetField.getText();
		int ret = -1;
		try{
			ret = Integer.parseInt(str);
		}catch(Exception e){}
		return ret;
	}
}