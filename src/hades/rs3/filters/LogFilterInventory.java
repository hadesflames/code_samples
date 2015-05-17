package hades.rs3.filters;

import hades.rs3.LogType.Log;

import org.tribot.api.rs3.types.BackpackItem;
import org.tribot.api.types.generic.Filter;

public class LogFilterInventory extends Filter<BackpackItem>{
	private Log plankType;
	
	public LogFilterInventory(Log log){
		plankType = log;
	}
	public boolean accept(BackpackItem item){
		if(item.id != 1076085)
			return false;
		int r = 0, g = 0, b = 0;
		switch(plankType){
			case NORMAL:
				r = 95;
				g = 72;
				b = 41;
				break;
			case OAK:
				r = 123;
				g = 93;
				b = 55;
				break;
			case TEAK:
				r = 157;
				g = 131;
				b = 86;
				break;
			case MAHOGANY:
				r = 145;
				g = 116;
				b = 74;
				break;
			default:
				return false;
		}
		return item.avg_r - 10 <= r && item.avg_r + 10 >= r && item.avg_g - 10 <= g && item.avg_g + 10 >= g && item.avg_b - 10 <= b && item.avg_b + 10 >= b;
	}
}