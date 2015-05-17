package hades.rs3.lib;

import hades.rs3.HadesScript;
import hades.rs3.lib.LoginHandler.LoginState;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;

public class BreakHandler{
	private HadesScript script;
	private int minBreakStart;
	private int maxBreakStart;
	private int minBreakLength;
	private int maxBreakLength;
	private int startOffset;
	private int lengthOffset;
	private long startTime = -1;
	private long breakStart = -1;
	private long breakLength = -1;
	private LoginHandler loginHandler;
	
	public BreakHandler(HadesScript script, int minBreakStart, int maxBreakStart, int minBreakLength, int maxBreakLength, int startOffset, int lengthOffset, LoginHandler loginHandler){
		this.script = script;
		this.minBreakStart = minBreakStart;
		this.maxBreakStart = maxBreakStart;
		this.minBreakLength = minBreakLength;
		this.maxBreakLength = maxBreakLength;
		this.startOffset = startOffset;
		this.lengthOffset = lengthOffset;
		this.loginHandler = loginHandler;
	}
	
	public void setBreakParams(){
		startTime = System.currentTimeMillis();
		breakStart = 60000L * ((long)(General.random(minBreakStart, maxBreakStart) + (General.random(0, startOffset) * (General.random(0, 100) < 50 ? 1 : -1))));
		breakLength = 60000L * ((long)(General.random(minBreakLength, maxBreakLength) + (General.random(0, lengthOffset) * (General.random(0, 100) < 50 ? 1 : -1))));
	}
	
	public void handleBreak(){
		if(System.currentTimeMillis() >= (startTime + breakStart))
			breakTime();
	}
	
	private void breakTime(){
		script.resetTimeOld();
		script.status = "Taking a Break";
		loginHandler.logout(!loginHandler.loginEnabled());
		int mins = (int)(breakLength / 60000);
		General.println("Taking a break for " + mins + " minute" + (mins != 1 ? "s" : "") + ".");
		Mouse.leaveGame();
		try{
			Thread.sleep(breakLength);
		}catch(Exception e){
			e.printStackTrace();
			script.killScript("Error: Something went terrible wrong, apparently. Killing script.");
			return;
		}
		General.println("Returning from break and logging in.");
		script.status = "Returning from Break";
		Mouse.pickupMouse();
		setBreakParams();
		boolean fromLobby = loginHandler.getState() == LoginState.LOBBY;
		loginHandler.login(fromLobby);
	}
}