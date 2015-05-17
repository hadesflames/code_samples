package hades.rs3.money.dancer.gui;

import hades.rs3.money.dancer.Dancer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class StartPressed implements ActionListener{
	private DancerGUI gui;
	Dancer dancer;
	
	public StartPressed(Dancer dancer, DancerGUI gui){
		this.dancer = dancer;
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent e){
		if(gui.getMessage().isEmpty())
			return;
		WindowListener listeners[] = gui.getWindowListeners();
		for(WindowListener wl : listeners)
			gui.removeWindowListener(wl);
		dancer.setOptions(gui);
		gui.dispose();
	}
}