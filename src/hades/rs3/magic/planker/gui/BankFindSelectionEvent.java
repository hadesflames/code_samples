package hades.rs3.magic.planker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

public class BankFindSelectionEvent implements ActionListener{
	private PlankerGUI gui;
	
	public BankFindSelectionEvent(PlankerGUI gui){
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof AbstractButton){
			AbstractButton abstractButton = (AbstractButton)e.getSource();
	        if(abstractButton.getModel().isSelected())
	        	JOptionPane.showMessageDialog(gui, "Warning:\nThe Bank Finding method uses the web walker to try to walk to the nearest bank.\nThis may or may not work, depending on the web walker. I recommend you don't use this.", "Bank Find Warning", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}