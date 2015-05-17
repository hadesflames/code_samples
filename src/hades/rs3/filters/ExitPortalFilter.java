package hades.rs3.filters;

import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.types.generic.Filter;

public class ExitPortalFilter extends Filter<ScreenModel>{
	private static final long EXIT_PORTAL_ID = 2668275729L;
	
	public boolean accept(ScreenModel sm){
		return sm.id == EXIT_PORTAL_ID;
	}
}