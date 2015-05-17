package hades.rs3.magic.planker.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class IntegerFieldLimit extends PlainDocument{
	private int limit;
	
	public IntegerFieldLimit(){
		this(-1);
	}
	
	public IntegerFieldLimit(int limit){
		super();
		this.limit = limit;
	}
	
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
		if(str == null)
			return;
		if(isInt(str) && (limit < 0 || limit >= (getLength() + str.length())))
			super.insertString(offs, str, a);
	}
	
	private boolean isInt(String str){
		try{
			Integer.parseInt(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
}