package hades.rs3.lib;

import hades.rs3.filters.BurnerFilter;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.rs3.Camera;
import org.tribot.api.rs3.EGW;
import org.tribot.api.rs3.Minimap;
import org.tribot.api.rs3.ScreenModels;
import org.tribot.api.rs3.types.EGWPosition;
import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.types.generic.Filter;

final public class CameraHandler{
	private static double memoryFace = -1;
	protected static final int NORTH = 0;
	protected static final int NORTH_MIN = 350;
	protected static final int NORTH_MAX = 10;
	
	protected static final int NORTH_WEST = 350;
	protected static final int NORTH_WEST_MIN = 335;
	protected static final int NORTH_WEST_MAX = 355;
	
	protected static final int EAST = 90;
	protected static final int EAST_MIN = 80;
	protected static final int EAST_MAX = 100;

	protected static final int SOUTH = 180;
	protected static final int SOUTH_MIN = 170;
	protected static final int SOUTH_MAX = 190;
	
	protected static final int SOUTH_WEST = 225;
	protected static final int SOUTH_WEST_MIN = 215;
	protected static final int SOUTH_WEST_MAX = 235;
	
	protected static final int WEST = 270;
	protected static final int WEST_MIN = 260;
	protected static final int WEST_MAX = 280;
	
	private static final short FACE_OFFSET = 20;
	
	public enum Face{
		NORTH, EAST, SOUTH, WEST
	}
	
	public static Face getFace(String face){
		if(face.equalsIgnoreCase("north"))
			return Face.NORTH;
		else if(face.equalsIgnoreCase("east"))
			return Face.EAST;
		else if(face.equalsIgnoreCase("south"))
			return Face.SOUTH;
		else if(face.equalsIgnoreCase("west"))
			return Face.WEST;
		return null;
	}
	
	public static void face(Face face){
		switch(face){
			case NORTH:
				faceNorth();
				break;
			case EAST:
				faceEast();
				break;
			case SOUTH:
				faceSouth();
				break;
			case WEST:
				faceWest();
				break;
		}
	}
	
	public static void faceNorth(){
		boolean low = General.random(0, 100) < 50;
		Camera.setRotation(low ? General.random(0, NORTH_MAX) : General.random(NORTH_MIN, 360));
	}
	
	public static void faceNorthWest(){
		Camera.setRotation(General.random(NORTH_WEST_MIN, NORTH_WEST_MAX));
	}
	
	public static void faceEast(){
		Camera.setRotation(General.random(EAST_MIN, EAST_MAX));
	}
	
	public static void faceSouth(){
		Camera.setRotation(General.random(SOUTH_MIN, SOUTH_MAX));
	}
	
	public static void faceSouthWest(){
		Camera.setRotation(General.random(SOUTH_WEST_MIN, SOUTH_WEST_MAX));
	}
	
	public static void faceWest(){
		Camera.setRotation(General.random(WEST_MIN, WEST_MAX));
	}
	
	public static void facePoint(Point p){
		double angle = getFaceAngle(p);
		int degreeMin = (int)Math.round(angle - FACE_OFFSET);
		int degreeMax = (int)Math.round(angle + FACE_OFFSET);
		if(degreeMin < 0)
			degreeMin += 360;
		if(degreeMax > 360)
			degreeMax -= 360;
		if(degreeMax < degreeMin){
			int min = 360 - degreeMin;
			int check = (int)Math.round(100 * (min / (min + degreeMax)));
			boolean low = General.random(0, 100) < check;
			Camera.setRotation(low ? General.random(degreeMin, 360) : General.random(0, degreeMax));
		}else
			Camera.setRotation(General.random(degreeMin, degreeMax));
	}
	
	public static void faceAngle(double angle){
		int degreeMin = (int)Math.round(angle - FACE_OFFSET);
		int degreeMax = (int)Math.round(angle + FACE_OFFSET);
		if(degreeMin < 0)
			degreeMin += 360;
		if(degreeMax > 360)
			degreeMax -= 360;
		if(degreeMax < degreeMin){
			int min = 360 - degreeMin;
			int check = (int)Math.round(100 * (min / (min + degreeMax)));
			boolean low = General.random(0, 100) < check;
			Camera.setRotation(low ? General.random(degreeMin, 360) : General.random(0, degreeMax));
		}else
			Camera.setRotation(General.random(degreeMin, degreeMax));
	}
	
	public static boolean isFacing(Point p){
		double angle = getFaceAngle(p);
		double mapAngle = Minimap.getRotationAngle();
		double minAngle = mapAngle - FACE_OFFSET;
		double maxAngle = mapAngle + FACE_OFFSET;
		if(minAngle < 0)
			minAngle += 360;
		if(maxAngle > 360)
			maxAngle -= 360;
		return angle <= maxAngle && angle >= minAngle;
	}
	
	public static boolean isFacing(Face face){
		if(face == null)
			return false;
		double angle = Minimap.getRotationAngle();
		switch(face){
			case NORTH:
				return ((angle >= NORTH_MIN && angle <= 360) || (angle >= 0 && angle <= NORTH_MAX));
			case EAST:
				return (angle >= CameraHandler.EAST_MIN && angle <= CameraHandler.EAST_MAX);
			case SOUTH:
				return (angle >= CameraHandler.SOUTH_MIN && angle <= CameraHandler.SOUTH_MAX);
			case WEST:
				return (angle >= CameraHandler.WEST_MIN && angle <= CameraHandler.WEST_MAX);
		}
		return false;
	}
	
	public static void rotateToChapel(){
		if(memoryFace > -1)
			faceAngle(memoryFace);
		Camera.rotateUntil(General.random(0, 100) < 80, new ChapelCondition(), General.random(2400, 3050));
		if(isFacingChapel())
			memoryFace = Minimap.getRotationAngle();
	}
	
	public static void randomRotate(){
		Camera.rotateUntil(General.random(0, 100) < 85, new Condition(){
			public boolean active(){
				return false;
			}
		}, General.random(500, 2000));
	}
	
	protected static boolean isFacingChapel(){
		Filter<ScreenModel> burnerFilter = new BurnerFilter();
		final ScreenModel burners[] = ScreenModels.findNearest(burnerFilter);
		if(burners.length < 1)
			return false;
		for(ScreenModel burner : burners)
			if(!burner.isVisible())
				return false;
		return true;
	}
	
	private static double getFaceAngle(Point p){
		EGWPosition pos = EGW.getPosition();
		double angle = Math.toDegrees(Math.atan2(p.x, p.y) - Math.atan2(pos.getX(), pos.getY()));
		return angle < 0 ? angle + 360 : angle;
	}
}
class ChapelCondition extends Condition{
	public boolean active(){
		return CameraHandler.isFacingChapel();
	}
}