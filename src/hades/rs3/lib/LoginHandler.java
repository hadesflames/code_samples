package hades.rs3.lib;

import hades.rs3.HadesScript;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.tribot.api.DTMs;
import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.Text;
import org.tribot.api.rs3.Textures;
import org.tribot.api.rs3.types.TextChar;
import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.colour.DTM;
import org.tribot.api.types.generic.Condition;

public class LoginHandler{
	private boolean enableLogin;
	private char username[];
	private char password[];
	private Condition loggedOut;
	private Condition lobby;
	private Condition loggedIn;
	
	private static final long INPUT_TEXTURE_ID = 1606950524L; //4057141498L
	private static final char BACK_SPACE = (char)8;
	
	public enum LoginState{
		OUT, LOBBY, IN
	}
	
	public LoginHandler(boolean enableLogin){
		this(enableLogin, null, null);
	}
	
	public LoginHandler(boolean enableLogin, char username[], char password[]){
		this.enableLogin = enableLogin;
		this.username = username;
		this.password = password;
		loggedOut = new LoggedOutCondition();
		lobby = new LobbyCondition();
		loggedIn = new LoggedInCondition();
	}
	
	public boolean login(boolean fromLobby){
		if(fromLobby){
			DTM playNowButtonDTM = DTMHandler.getPlayNowButtonDTM();
			int failed = 0;
			while(!playNowButtonDTM.click((String[])null, new Point(166, 27), null)){
				General.sleep(300, 500);
				if(++failed == HadesScript.MAX_FAILURES)
					return false;
			}
			return Timing.waitCondition(new LoggedInCondition(), General.random(10000, 12000));
		}
		if(!enableLogin || username == null || password == null)
			return false;
		if(!clearFields())
			return false;
		DTM userNameField = DTMHandler.getUnField(true);
		Dimension screen = Screen.getDimension();
		if(DTMs.find_simple(userNameField, 0, 0, screen.width, screen.height).length == 0){
			userNameField = DTMHandler.getUnField(false);
			if(DTMs.find_simple(userNameField, 0, 0, screen.width, screen.height).length == 0)
				return false;
			userNameField.click((String[])null, new Point(244, 20), null);
		}
		Keyboard.typeKeys(username);
		General.sleep(50, 100);
		Keyboard.pressEnter();
		General.sleep(50, 100);
		Keyboard.typeKeys(password);
		General.sleep(50, 100);
		Keyboard.pressEnter();
		if(!Timing.waitCondition(new LobbyCondition(), General.random(6000, 7000)))
			return false;
		return login(true);
	}
	
	public boolean logout(boolean toLobby){
		DTM logoutDTM = DTMHandler.getLogoutDTM();
		int failCount = 0;
		while(!logoutDTM.click((String[])null, new Point(10, 10), new Point(0, -10))){
			General.sleep(200, 400);
			if(++failCount == HadesScript.MAX_FAILURES)
				return false;
		}
		failCount = 0;
		if(!Timing.waitCondition(new LogoutPopupCondition(), General.random(3000, 4000)))
			return false;
		DTM logoutButtonDTM = toLobby ? DTMHandler.getLobbyDTM() : DTMHandler.getLogoutButtonDTM();
		while(!logoutButtonDTM.click((String[])null, new Point(124, 23), null)){
			General.sleep(200, 400);
			if(++failCount == HadesScript.MAX_FAILURES)
				return false;
		}
		General.sleep(900, 1200);
		return true;
	}
	
	public LoginState getState(){
		if(loggedOut.active())
			return LoginState.OUT;
		else if(lobby.active())
			return LoginState.LOBBY;
		else if(loggedIn.active())
			return LoginState.IN;
		return null;
	}
	
	public boolean loginEnabled(){
		return enableLogin;
	}
	
	private boolean clearFields(){
		if(!clearPasswordField())
			return false;
		return clearUsernameField();
	}
	
	private boolean clearUsernameField(){
		Texture usernameField[] = Textures.find(INPUT_TEXTURE_ID);
		if(usernameField.length == 0)
			return false;
		Texture unField = usernameField[0];
		TextChar temp[] = Text.findCharsInArea(unField.x - 20, unField.y, unField.width + 40, unField.height, true);
		if(temp.length == 0)
			return true;
		TextChar unLine[] = null;
		ArrayList<TextChar[]> lines = Text.splitByHorizontalLines(temp);
		for(TextChar line[] : lines){
			if(line == null)
				continue;
			String lineStr = Text.lineToString(line, 3).toLowerCase();
			if(!lineStr.contains("/") && !lineStr.contains("?")){
				unLine = line;
				break;
			}
		}
		if(unLine == null)
			return false;
		TextChar unEnd = unLine[unLine.length - 1];
		Mouse.clickBox(new Rectangle(unEnd.x + unEnd.width, unEnd.y, ((unField.width + 20) - (unEnd.x - (unField.x - 20))), unEnd.height), 1);
		Condition empty = new TextEmptyCondition(new Rectangle(unField.x - 20, unField.y, unField.width + 20, unField.height));
		Keyboard.holdKey(BACK_SPACE, Keyboard.getKeyCode(BACK_SPACE), empty);
		return empty.active();
	}
	
	private boolean clearPasswordField(){
		Texture passwordField[] = Textures.find(INPUT_TEXTURE_ID);
		if(passwordField.length == 0)
			return false;
		Texture passField = passwordField[passwordField.length - 1];
		TextChar temp[] = Text.findCharsInArea(passField.x - 20, passField.y, passField.width + 40, passField.height, true);
		if(temp.length == 0)
			return true;
		TextChar passLine[] = null;
		ArrayList<TextChar[]> lines = Text.splitByHorizontalLines(temp);
		for(TextChar line[] : lines){
			if(line == null)
				continue;
			String lineStr = Text.lineToString(line, 3).toLowerCase();
			if(lineStr.contains("*")){
				passLine = line;
				break;
			}
		}
		if(passLine == null)
			return false;
		TextChar passEnd = passLine[passLine.length - 1];
		Mouse.clickBox(new Rectangle(passEnd.x + passEnd.width, passEnd.y, ((passField.width + 20) - (passEnd.x - (passField.x - 20))), passEnd.height), 1);
		Condition empty = new TextEmptyCondition(new Rectangle(passField.x - 20, passField.y, passField.width + 20, passField.height));
		Keyboard.holdKey(BACK_SPACE, Keyboard.getKeyCode(BACK_SPACE), empty);
		return empty.active();
	}
}
class LoggedOutCondition extends Condition{
	public boolean active(){
		General.sleep(100);
		Dimension screen = Screen.getDimension();
		DTM loginButtonDTM = DTMHandler.getLoginButtonDTM();
		return DTMs.find_simple(loginButtonDTM, 0, 0, screen.width, screen.height).length > 0;
	}
}
class LobbyCondition extends Condition{
	public boolean active(){
		General.sleep(100);
		Dimension screen = Screen.getDimension();
		DTM playNowDTM = DTMHandler.getPlayNowButtonDTM();
		return DTMs.find_simple(playNowDTM, 0, 0, screen.width, screen.height).length > 0;
	}
}
class LoggedInCondition extends Condition{
	public boolean active(){
		General.sleep(100);
		Dimension screen = Screen.getDimension();
		DTM compassDTM = DTMHandler.getCompassDTM();
		boolean compass = DTMs.find_simple(compassDTM, 0, 0, screen.width, screen.height).length > 0;
		return (compass || (DTMs.find_simple(DTMHandler.getLoginButtonDTM(), 0, 0, screen.width, screen.height).length == 0 && DTMs.find_simple(DTMHandler.getPlayNowButtonDTM(), 0, 0, screen.width, screen.height).length == 0));
	}
}
class LogoutPopupCondition extends Condition{
	public boolean active(){
		General.sleep(100);
		Dimension screen = Screen.getDimension();
		DTM logoutButton = DTMHandler.getLogoutButtonDTM();
		return DTMs.find_simple(logoutButton, 0, 0, screen.width, screen.height).length > 0;
	}
}
class TextEmptyCondition extends Condition{
	private Rectangle area;
	
	public TextEmptyCondition(Rectangle area){
		this.area = area;
	}
	
	public boolean active(){
		TextChar text[] = Text.findCharsInArea(area.x, area.y, area.width, area.height, true);
		return text.length == 0;
	}
}