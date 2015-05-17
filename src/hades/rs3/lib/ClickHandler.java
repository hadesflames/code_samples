package hades.rs3.lib;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.DynamicMouse;
import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.ChooseOption;
import org.tribot.api.rs3.Game;
import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.CustomRet_0P;

public class ClickHandler{
	public static boolean rightClickScreenModel(ScreenModel sm, String... options){
		if(options == null || options.length == 0)
			return false;
		DynamicMouse.click(new ScreenModelPoint(sm.getCentrePoint()), 3);
		Timing.waitCondition(new OptionOpenCondition(), General.random(900, 1500));
		return ChooseOption.select(options[0]);
	}
	
	public static boolean clickScreenModel(final ScreenModel sm, final String... options){
		Polygon p = sm.getEnclosedArea();
		Rectangle r = p.getBounds();
		Point p1 = new Point(General.random(r.x, r.x + r.width), General.random(r.y, r.y + r.height));
		DynamicMouse.move(new ScreenModelPoint(p1));
		boolean b = Timing.waitCondition(new Condition(){
			public boolean active(){
				if(options != null && options.length > 0){
					String tooltip = Game.getTooltipText();
					if(tooltip == null)
						return false;
					return tooltip.toLowerCase().contains(options[0].toLowerCase()) && sm.getEnclosedArea().contains(Mouse.getPos());
				}
				return sm.getEnclosedArea().contains(Mouse.getPos());
			}
		}, General.random(1300, 1500));
		if(!b)
			return false;
		return DynamicClicking.clickScreenModel(new ScreenModelRet(sm), 1);
	}
}
class ScreenModelRet extends CustomRet_0P<ScreenModel>{
	private ScreenModel sm;
	
	public ScreenModelRet(ScreenModel sm){
		this.sm = sm;
	}
	
	public ScreenModel ret(){
		return sm;
	}
}
class ScreenModelPoint extends CustomRet_0P<Point>{
	private Point p;
	
	public ScreenModelPoint(Point p){
		this.p = p;
	}
	
	public Point ret(){
		return p;
	}
}
class OptionOpenCondition extends Condition{
	public boolean active(){
		return ChooseOption.isOpen();
	}
}