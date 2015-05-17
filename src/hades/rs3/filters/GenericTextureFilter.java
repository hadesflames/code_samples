package hades.rs3.filters;

import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.generic.Filter;

public class GenericTextureFilter extends Filter<Texture>{
	private long textureId;
	
	public GenericTextureFilter(long textureId){
		this.textureId = textureId;
	}
	
	public boolean accept(Texture t){
		return t.crc32 == textureId;
	}
}