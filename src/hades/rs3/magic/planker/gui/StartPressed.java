package hades.rs3.magic.planker.gui;

import hades.rs3.magic.planker.Planker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class StartPressed implements ActionListener{
	private PlankerGUI gui;
	Planker planker;
	
	public StartPressed(Planker planker, PlankerGUI gui){
		this.planker = planker;
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent e){
		if(gui.getKeyBind() == (char)13 || gui.getPlankType().isEmpty())
			return;
		WindowListener listeners[] = gui.getWindowListeners();
		for(WindowListener wl : listeners)
			gui.removeWindowListener(wl);
		planker.setOptions(gui);
		gui.dispose();
	}
}