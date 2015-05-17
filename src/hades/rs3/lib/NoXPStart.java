package hades.rs3.lib;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.rs3.Text;
import org.tribot.api.rs3.Textures;
import org.tribot.api.rs3.types.TextChar;
import org.tribot.api.rs3.types.Texture;
import org.tribot.api.types.generic.Condition;

public class NoXPStart extends Condition{	
	public boolean active(){
		General.sleep(1500, 2300);
		Texture search[] = Textures.find(XPHandler.SKILLS_XP_COUNTER);
		if(search.length == 0)
			return false;
		TextChar temp[] = Text.findCharsInArea(search[0].x - 200, search[0].y, 200, 30, true);
		if(temp.length == 0)
			return false;
		ArrayList<TextChar[]> lines = Text.splitByHorizontalLines(temp);
		for(TextChar line[] : lines){
			if(line == null)
				continue;
			String lineStr = Text.lineToString(line, 3);
			String split[] = lineStr.split(" ");
			for(String str : split)
				if(str.contains("+"))
					return false;
		}
		return true;
	}
}