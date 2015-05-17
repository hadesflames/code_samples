package hades.rs3;

import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

public abstract class HadesScript extends Script implements Painting{
	public static final short MAX_FAILURES = 15;
	
	public String status = "";
	
	public abstract void killScript(String err);
	
	public abstract void resetTimeOld();
}