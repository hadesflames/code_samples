package hades.rs3.filters;

import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.generic.Filter;

public class LogoutButtonFilter extends Filter<Texture>{
	private static final long LOGOUT_BUTTON_TEXTURE = 3307997741L;
	
	public boolean accept(Texture t){
		return t.crc32 == LOGOUT_BUTTON_TEXTURE;
	}
}