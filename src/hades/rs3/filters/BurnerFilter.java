package hades.rs3.filters;

import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.types.generic.Filter;

public class BurnerFilter extends Filter<ScreenModel>{
	private static final long BURNER_ID = 3812561033L;
	
	public boolean accept(ScreenModel sm){
		if(sm.id != BURNER_ID)
			return false;
		return sm.isVisible() && sm.isClickable(ThreadSettings.get().getScreenModelClickingMethod());
	}
}