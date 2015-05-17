package hades.rs3.filters;

import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.types.generic.Filter;

public class OnScreenObjectFilter extends Filter<ScreenModel>{
	public boolean accept(ScreenModel sm){
		return sm.isClickable(ThreadSettings.get().getScreenModelClickingMethod());
	}
}