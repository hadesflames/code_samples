package hades.rs3.filters;

import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.types.generic.Filter;

public class ChapelFilter extends Filter<ScreenModel>{
	private static final long CHAPEL_ID = 3417848175L;
	
	public boolean accept(ScreenModel sm){
		if(sm.id != CHAPEL_ID)
			return false;
		return sm.isVisible();
	}
}