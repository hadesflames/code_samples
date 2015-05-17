package hades.rs3.filters;

import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.types.generic.Filter;

public class DoorFilter extends Filter<ScreenModel>{
	private static final long DOOR_ID_1 = 622310563L;
	private static final long DOOR_ID_2 = 152420097L;
	
	public boolean accept(ScreenModel sm){
		if(sm.id != DOOR_ID_1 && sm.id != DOOR_ID_2)
			return false;
		return sm.isVisible();
	}
}