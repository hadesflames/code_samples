package hades.rs3.prayer.altar.gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PinFieldFocusListener implements FocusListener{
	private JFrame frame;
	private JTextField field;
	
	public PinFieldFocusListener(JFrame frame, JTextField field){
		this.frame = frame;
		this.field = field;
	}
	
	public void focusGained(FocusEvent e){
		// Do Nothing.
	}
	
	public void focusLost(FocusEvent e){
		if(field.getText().length() != 4 || !isInt(field.getText())){
			field.setText("");
			JOptionPane.showMessageDialog(frame, "Your pin can only be a 4 digit integer!", "Pin Validation Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean isInt(String str){
		try{
			Integer.parseInt(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}