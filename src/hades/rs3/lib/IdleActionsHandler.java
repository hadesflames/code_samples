package hades.rs3.lib;

import hades.rs3.filters.GenericTextureFilter;
import hades.rs3.filters.OnScreenObjectFilter;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import org.tribot.api.DTMs;
import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.Timing;
import org.tribot.api.rs3.ScreenModels;
import org.tribot.api.rs3.Skills;
import org.tribot.api.rs3.Textures;
import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.colour.DTM;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;
import org.tribot.api.util.ABCUtil;

public class IdleActionsHandler{
	public static final long BACKPACK_SLOT_TEXTURE_ID = 1543452233L;
	
	private static final String EXAMINE_OPTION = "Examine";
	
	private ABCUtil abcUtil;
	
	public IdleActionsHandler(){
		abcUtil = new ABCUtil();
	}
	
	public boolean checkXP(Skills.SKILLS skill){
		long time = abcUtil.TIME_TRACKER.CHECK_XP.next();
		if(Timing.currentTimeMillis() < time || time <= 0)
			return false;
		abcUtil.TIME_TRACKER.CHECK_XP.reset();
		General.println("IDLE ACTION: Checking XP.");
		if(!openSkillsMenu(skill))
			return false;
		return performXPCheck(skill);
	}
	
	public boolean isOpenBackpack(){
		Filter<Texture> backpackSlotFilter = new GenericTextureFilter(BACKPACK_SLOT_TEXTURE_ID);
		Texture backpackSlots[] = Textures.find(backpackSlotFilter);
		return backpackSlots.length > 0;
	}
	
	public boolean openBackpack(){
		if(isOpenBackpack())
			return true;
		final DTM backPackMenuDTM = DTMHandler.getBackPackMenuDTM();
		final DTM gearMenuButtonDTM = DTMHandler.getGearMenuButtonDTM();
		final DTM backPackMenuButtonDTM = DTMHandler.getBackPackMenuButtonDTM();
		final DTM backPackMenuButtonOffDTM = DTMHandler.getBackPackMenuButtonOffDTM();
		
		Dimension d = Screen.getDimension();
		if(DTMs.find_simple(backPackMenuDTM, 0, 0, d.width, d.height).length > 0){
			backPackMenuDTM.click((String[])null, new Point(26, 20), null);
			Timing.waitCondition(new Condition(){
				public boolean active(){
					General.sleep(100, 200);
					return isOpenBackpack();
				}
			}, General.random(2000, 3000));
			return isOpenBackpack();
		}
		d = Screen.getDimension();
		if(DTMs.find_simple(gearMenuButtonDTM, 0, 0, d.width, d.height).length == 0)
			return false;
		gearMenuButtonDTM.hover(new Point(30, 30), null);
		Timing.waitCondition(new Condition(){
			public boolean active(){
				General.sleep(100, 200);
				Dimension d = Screen.getDimension();
				return DTMs.find(backPackMenuButtonDTM, 0, 0, d.width, d.height).length > 0 || DTMs.find(backPackMenuButtonOffDTM, 0, 0, d.width, d.height).length > 0;
			}
		}, General.random(2000, 3000));
		d = Screen.getDimension();
		if(DTMs.find(backPackMenuButtonOffDTM, 0, 0, d.width, d.height).length > 0){
			backPackMenuButtonOffDTM.click((String[])null, new Point(30, 30), null);
			Timing.waitCondition(new Condition(){
				public boolean active(){
					General.sleep(100, 200);
					Dimension d = Screen.getDimension();
					return DTMs.find_simple(backPackMenuButtonDTM, 0, 0, d.width, d.height).length > 0;
				}
			}, General.random(2000, 3000));
			d = Screen.getDimension();
		}
		if(DTMs.find(backPackMenuButtonDTM, 0, 0, d.width, d.height).length == 0)
			return false;
		backPackMenuButtonDTM.click((String[])null, new Point(30, 30), null);
		return Timing.waitCondition(new Condition(){
			public boolean active(){
				General.sleep(100, 200);
				return isOpenBackpack();
			}
		}, General.random(2000, 3000));
	}
	
	public boolean checkRandomObject(){
		long time = abcUtil.TIME_TRACKER.EXAMINE_OBJECT.next();
		if(Timing.currentTimeMillis() < abcUtil.TIME_TRACKER.EXAMINE_OBJECT.next() || time <= 0)
			return false;
		abcUtil.TIME_TRACKER.EXAMINE_OBJECT.reset();
		General.println("IDLE ACTION: Checking random object.");
		Filter<ScreenModel> objectFilter = new OnScreenObjectFilter();
		ScreenModel objects[] = ScreenModels.find(objectFilter);
		if(objects.length < 1)
			return false;
		int random = General.random(0, objects.length - 1);
		return ClickHandler.rightClickScreenModel(objects[random], EXAMINE_OPTION);
	}
	
	public boolean rotateCam(boolean newThread){
		long time = abcUtil.TIME_TRACKER.ROTATE_CAMERA.next();
		if(Timing.currentTimeMillis() < abcUtil.TIME_TRACKER.ROTATE_CAMERA.next() || time <= 0)
			return false;
		abcUtil.TIME_TRACKER.ROTATE_CAMERA.reset();
		General.println("IDLE ACTION: Rotating the camera.");
		if(newThread){
			EventQueue.invokeLater(new Runnable(){
				public void run(){
					try{
						CameraHandler.randomRotate();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}else
			CameraHandler.randomRotate();
		return true;
	}
	
	public boolean leaveGame(){
		long time = abcUtil.TIME_TRACKER.LEAVE_GAME.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.LEAVE_GAME.next() && time > 0)
			General.println("IDLE ACTION: Leaving game.");
		return abcUtil.performLeaveGame();
	}
	
	public boolean pickUpMouse(){
		long time = abcUtil.TIME_TRACKER.PICKUP_MOUSE.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.PICKUP_MOUSE.next() && time > 0)
			General.println("IDLE ACTION: Picking up the mouse.");
		return abcUtil.performPickupMouse();
	}
	
	public boolean randomMouseMovement(){
		long time = abcUtil.TIME_TRACKER.RANDOM_MOUSE_MOVEMENT.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.RANDOM_MOUSE_MOVEMENT.next() && time > 0)
			General.println("IDLE ACTION: Moving mouse randomly.");
		return abcUtil.performRandomMouseMovement();
	}
	
	public boolean randomRightClick(){
		long time = abcUtil.TIME_TRACKER.RANDOM_RIGHT_CLICK.next();
		if(Timing.currentTimeMillis() >= abcUtil.TIME_TRACKER.RANDOM_RIGHT_CLICK.next() && time > 0)
			General.println("IDLE ACTION: Right clicking randomly.");
		return abcUtil.performRandomRightClick();
	}
	
	public void sleep(long add){
		General.sleep(abcUtil.DELAY_TRACKER.ITEM_INTERACTION.next() + add);
		abcUtil.DELAY_TRACKER.ITEM_INTERACTION.reset();
	}
	
	public static boolean openSkillsMenu(Skills.SKILLS skill){
		final DTM heroMenuButtonDTM = DTMHandler.getHeroMenuButtonDTM();
		final DTM skillsMenuButtonDTM = DTMHandler.getSkillsMenuButtonDTM();
		final DTM skillsMenuButtonOffDTM = DTMHandler.getSkillsMenuButtonOffDTM();
		final DTM skillDTM = DTMHandler.getSkillDTM(skill);
		final DTM skillMenuDTM = DTMHandler.getSkillsMenuDTM();
		Dimension d = Screen.getDimension();

		if(DTMs.find_simple(skillDTM, 0, 0, d.width, d.height).length > 0)
			return true;
		if(DTMs.find_simple(skillMenuDTM, 0, 0, d.width, d.height).length > 0){
			skillMenuDTM.click((String[])null, new Point(26, 20), null);
			Timing.waitCondition(new Condition(){
				public boolean active(){
					General.sleep(100, 200);
					Dimension d = Screen.getDimension();
					return DTMs.find_simple(skillDTM, 0, 0, d.width, d.height).length > 0;
				}
			}, General.random(2000, 3000));
			d = Screen.getDimension();
			return DTMs.find_simple(skillDTM, 0, 0, d.width, d.height).length > 0;
		}
		d = Screen.getDimension();
		if(DTMs.find_simple(heroMenuButtonDTM, 0, 0, d.width, d.height).length == 0)
			return false;
		heroMenuButtonDTM.hover(new Point(30, 30), null);
		Timing.waitCondition(new Condition(){
			public boolean active(){
				General.sleep(100, 200);
				Dimension d = Screen.getDimension();
				return DTMs.find_simple(skillsMenuButtonDTM, 0, 0, d.width, d.height).length > 0 || DTMs.find_simple(skillsMenuButtonOffDTM, 0, 0, d.width, d.height).length > 0;
			}
		}, General.random(2000, 3000));
		d = Screen.getDimension();
		if(DTMs.find_simple(skillsMenuButtonOffDTM, 0, 0, d.width, d.height).length > 0){
			skillsMenuButtonOffDTM.click((String[])null, new Point(30, 30), null);
			Timing.waitCondition(new Condition(){
				public boolean active(){
					General.sleep(100, 200);
					Dimension d = Screen.getDimension();
					return DTMs.find_simple(skillsMenuButtonDTM, 0, 0, d.width, d.height).length > 0;
				}
			}, General.random(2000, 3000));
			d = Screen.getDimension();
		}
		if(DTMs.find_simple(skillsMenuButtonDTM, 0, 0, d.width, d.height).length == 0)
			return false;
		skillsMenuButtonDTM.click((String[])null, new Point(30, 30), null);
		Timing.waitCondition(new Condition(){
			public boolean active(){
				General.sleep(100, 200);
				Dimension d = Screen.getDimension();
				return DTMs.find_simple(skillDTM, 0, 0, d.width, d.height).length > 0;
			}
		}, General.random(2000, 3000));
		d = Screen.getDimension();
		return DTMs.find_simple(skillDTM, 0, 0, d.width, d.height).length > 0;
	}
	
	private boolean performXPCheck(Skills.SKILLS skill){
		Dimension d = Screen.getDimension();
		DTM skillDTM = DTMHandler.getSkillDTM(skill);
		if(DTMs.find_simple(skillDTM, 0, 0, d.width, d.height).length == 0)
			return false;
		skillDTM.hover(new Point(54, 21), null);
		General.sleep(1250, 2350);
		return true;
	}
}