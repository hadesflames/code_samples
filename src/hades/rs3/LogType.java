package hades.rs3;

import hades.rs3.filters.LogFilterBank;
import hades.rs3.filters.LogFilterInventory;

import org.tribot.api.rs3.Backpack;
import org.tribot.api.rs3.Banking;
import org.tribot.api.rs3.types.BackpackItem;
import org.tribot.api.rs3.types.BankItem;

public class LogType{
	public enum Log{
		NORMAL, OAK, TEAK, MAHOGANY
	}
	
	public static Log getType(String type){
		if(type.equalsIgnoreCase("oak"))
			return Log.OAK;
		else if(type.equalsIgnoreCase("teak"))
			return Log.TEAK;
		else if(type.equalsIgnoreCase("mahogany"))
			return Log.MAHOGANY;
		return Log.NORMAL;
	}
	
	public static BankItem[] findBank(Log log){
		return Banking.find(new LogFilterBank(log));
	}
	
	public static BackpackItem[] findInventory(Log log){
		return Backpack.find(new LogFilterInventory(log));
	}
}