package hades.rs3.money.dancer;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;

public class DancerChat{	
	public static void sendMessage(String msg){
		Keyboard.pressEnter();
		General.sleep(General.random(25, 95));
		Keyboard.typeKeys(msg.toCharArray());
		Keyboard.pressEnter();
		General.sleep(General.random(275, 350));
	}
}