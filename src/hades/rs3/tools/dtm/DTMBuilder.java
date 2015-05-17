package hades.rs3.tools.dtm;

import java.awt.EventQueue;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

@ScriptManifest(authors = { "hadesflames" }, category = "Tools", name = "hades DTM Builder", version = 1.01, description = "Tool to help build DTMs.", gameMode = 2)
public class DTMBuilder extends Script{
	private DTMBuilderGUI gui = null;
	
	public void run(){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					gui = new DTMBuilderGUI();
					gui.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		while(gui == null || gui.isVisible())
			General.sleep(1000);
	}
}