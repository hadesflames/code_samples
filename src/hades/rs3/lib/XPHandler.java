package hades.rs3.lib;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.tribot.api.DTMs;
import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.Banking;
import org.tribot.api.rs3.Skills;
import org.tribot.api.rs3.Text;
import org.tribot.api.rs3.Textures;
import org.tribot.api.rs3.types.TextChar;
import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.colour.DTM;
import org.tribot.api.types.generic.Condition;

public class XPHandler{
	private boolean usable;
	private int startLevel;
	private int currentLevel;
	private int startXP;
	private int currentXP;
	private long startXPCount;
	private long currentXPCount;
	
	public static final long SKILLS_XP_COUNTER = 4194458620L;
	
	public XPHandler(Skills.SKILLS skill){
		startLevel = -1;
		currentLevel = -1;
		startXP = -1;
		currentXP = -1;
		startXPCount = -1;
		currentXPCount = -1;
		setStartVars(skill);
		setStartXPCount();
		setCurrentVars(true);
		usable = startXP > -1 && startLevel > -1 && currentXP > -1 && currentLevel > -1 && startXPCount > -1 && currentXPCount > -1;
	}
	
	public void setStartVars(Skills.SKILLS skill){
		if(!IdleActionsHandler.openSkillsMenu(skill))
			return;
		DTM skillDTM = DTMHandler.getSkillDTM(skill);
		Dimension d = Screen.getDimension();
		Point points[] = DTMs.find_simple(skillDTM, 0, 0, d.width, d.height);
		if(points.length == 0)
			return;
		skillDTM.hover(new Point(54, 21), null);
		Timing.waitCondition(new PrayerHoverCondition(skillDTM), General.random(1500, 2000));
		d = Screen.getDimension();
		points = DTMs.find_simple(DTMHandler.getXPMenuDTM(), 0, 0, d.width, d.height);
		if(points.length == 0)
			return;
		TextChar temp[] = Text.findCharsInArea(points[0].x, points[0].y, 150, 85, true);
		if(temp.length == 0)
			return;
		ArrayList<TextChar[]> lines = Text.splitByHorizontalLines(temp);
		for(TextChar line[] : lines){
			if(line == null)
				continue;
			String lineStr = Text.lineToString(line, 3);
			String split[] = lineStr.split(" ");
			boolean found = false;
			int xp = -1;
			for(String str : split){
				if(str.equalsIgnoreCase("current")){
					found = true;
					continue;
				}
				if(str.contains(",") && found){
					try{
						xp = Integer.parseInt(str.replace(",", ""));
					}catch(Exception e){
						e.printStackTrace();
						return;
					}
					break;
				}
			}
			if(found && xp > -1){
				startXP = xp;
				startLevel = Skills.getLevelByXP(xp);
				break;
			}
		}
	}
	
	public void setCurrentVars(boolean copyStart){
		if(copyStart){
			currentLevel = startLevel;
			currentXP = startXP;
			return;
		}
		if(Banking.isBankScreenOpen() || Banking.isPinScreenOpen())
			return;
		setCurrentXPCount();
		int xpGained = (int)(currentXPCount - startXPCount);
		currentXP = startXP + xpGained;
		currentLevel = Skills.getLevelByXP(currentXP);
	}
	
	public int getStartLevel(){
		return startLevel;
	}
	
	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public int getStartXP(){
		return startXP;
	}
	
	public int getCurrentXP(){
		return currentXP;
	}
	
	public boolean isUsable(){
		return usable;
	}
	
	private void setStartXPCount(){
		Texture search[] = Textures.find(SKILLS_XP_COUNTER);
		if(search.length == 0)
			return;
		TextChar temp[] = Text.findCharsInArea(search[0].x - 100, search[0].y, 100, 30, true);
		if(temp.length == 0)
			return;
		ArrayList<TextChar[]> lines = Text.splitByHorizontalLines(temp);
		for(TextChar line[] : lines){
			if(line == null)
				continue;
			String lineStr = Text.lineToString(line, 3);
			String split[] = lineStr.split(" ");
			long xp = -1;
			for(String str : split){
				if(str.contains(",")){
					try{
						xp = Long.parseLong(str.replace(",", ""));
					}catch(Exception e){
						e.printStackTrace();
						return;
					}
					break;
				}
			}
			if(xp > -1){
				currentXPCount = startXPCount = xp;
				break;
			}
		}
	}
	
	private void setCurrentXPCount(){
		Texture search[] = Textures.find(SKILLS_XP_COUNTER);
		if(search.length == 0)
			return;
		TextChar temp[] = Text.findCharsInArea(search[0].x - 100, search[0].y, 100, 30, true);
		if(temp.length == 0)
			return;
		ArrayList<TextChar[]> lines = Text.splitByHorizontalLines(temp);
		for(TextChar line[] : lines){
			if(line == null)
				continue;
			String lineStr = Text.lineToString(line, 3);
			String split[] = lineStr.split(" ");
			long xp = -1;
			for(String str : split){
				if(str.contains(",")){
					try{
						xp = Long.parseLong(str.replace(",", ""));
					}catch(Exception e){
						e.printStackTrace();
						return;
					}
					break;
				}
			}
			if(xp > -1){
				currentXPCount = xp;
				break;
			}
		}
	}
}
class PrayerHoverCondition extends Condition{
	private DTM dtm;
	
	public PrayerHoverCondition(DTM dtm){
		this.dtm = dtm;
	}
	
	public boolean active(){
		General.sleep(50);
		Dimension d = Screen.getDimension();
		Point points[] = DTMs.find_simple(dtm, 0, 0, d.width, d.height);
		if(points.length == 0)
			return false;
		Point mouse = Mouse.getPos();
		Rectangle r = new Rectangle(points[0], new Dimension(54, 21));
		return r.contains(mouse);
	}
}