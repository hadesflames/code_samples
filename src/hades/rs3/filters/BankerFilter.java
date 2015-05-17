package hades.rs3.filters;

import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.types.generic.Filter;

public class BankerFilter extends Filter<ScreenModel>{
	private static final long BANKER_ID = 3778955772L;
	
	public boolean accept(ScreenModel sm){
		if(sm.id != BANKER_ID)
			return false;
		return sm.isVisible() && sm.isClickable(ThreadSettings.get().getScreenModelClickingMethod());
	}
}