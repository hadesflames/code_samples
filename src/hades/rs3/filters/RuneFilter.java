package hades.rs3.filters;

import org.tribot.api.rs3.types.BackpackItem;
import org.tribot.api.types.generic.Filter;

public class RuneFilter extends Filter<BackpackItem>{
	private boolean nat;
	
	public RuneFilter(boolean nat){
		this.nat = nat;
	}
	
	public boolean accept(BackpackItem item){
		if(item.id != 1026915)
			return false;
		int r = 124, g = 115, b = 120;
		if(nat){
			r = 93;
			g = 99;
			b = 89;
		}
		return item.avg_r - 10 <= r && item.avg_r + 10 >= r && item.avg_g - 10 <= g && item.avg_g + 10 >= g && item.avg_b - 10 <= b && item.avg_b + 10 >= b;
	}
}