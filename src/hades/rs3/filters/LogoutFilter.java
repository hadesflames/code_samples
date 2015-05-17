package hades.rs3.filters;

import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.generic.Filter;

public class LogoutFilter extends Filter<Texture>{
	private static final long LOGOUT_TEXTURE = 2912911290L;
	
	public boolean accept(Texture t){
		return t.crc32 == LOGOUT_TEXTURE;
	}
}