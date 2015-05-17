package hades.rs3.prayer.altar.gui;

import hades.rs3.prayer.altar.Prayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class StartPressed implements ActionListener{
	private PrayerGUI gui;
	Prayer prayer;
	
	public StartPressed(Prayer prayer, PrayerGUI gui){
		this.prayer = prayer;
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent e){
		WindowListener listeners[] = gui.getWindowListeners();
		for(WindowListener wl : listeners)
			gui.removeWindowListener(wl);
		prayer.setOptions(gui);
		gui.dispose();
	}
}