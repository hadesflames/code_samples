package hades.rs3.lib;

import hades.rs3.filters.BankerFilter;
import hades.rs3.filters.ChapelFilter;
import hades.rs3.filters.DoorFilter;
import hades.rs3.filters.ExitPortalFilter;

import org.tribot.api.rs3.Banking;
import org.tribot.api.rs3.ScreenModels;
import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.types.generic.Filter;

public class LocationHandler{
	public static boolean isInBank(){
		if(Banking.isBankScreenOpen() || Banking.isPinScreenOpen())
			return true;
		Filter<ScreenModel> bankerFilter = new BankerFilter();
		final ScreenModel bankers[] = ScreenModels.findNearest(bankerFilter);
		return bankers.length > 0;
	}
	
	public static boolean isAtHouse(){
		Filter<ScreenModel> houseFilter = new ExitPortalFilter().or(new ChapelFilter().or(new DoorFilter(), true), true);
		final ScreenModel objects[] = ScreenModels.findNearest(houseFilter);
		return objects.length > 0;
	}
	
	public static boolean isAtChapel(){
		Filter<ScreenModel> chapelFilter = new ChapelFilter();
		final ScreenModel chapel[] = ScreenModels.findNearest(chapelFilter);
		return chapel.length > 0 && chapel[0].isVisible();
	}
}