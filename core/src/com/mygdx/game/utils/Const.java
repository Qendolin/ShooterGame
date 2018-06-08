package com.mygdx.game.utils;

/**
 * Diese Klasse ist da um konstanten zu deffinieren
 */
public class Const {

	
	public static class Collision {
		public static final short NONE = 0x0;
		public static final short NEVER = 0x0;
		public static final short PROJECTILE = 0x1; //0000 0001
		public static final short ENTITY = 0x2;     //0000 0010
		public static final short BIG_ENTITY = ENTITY | 0x4; //0000 0100
		public static final short ALWAYS = 0x8; //0000 1000
		public static final short DEFAULT = 0x10; //0001 0000
		public static final short PLAYER = ENTITY | 0x20; //0010 0100
		public static final short MAP_OBJECT_COLLIDER = BIG_ENTITY|PLAYER;
		public static final short ALL = 0xFFF; //1111 1111
		
		/*
		 * Player Mask (Const.PROJECTILE ^ Const.ENTITY ^ Const.ALL):
		 * 1111 1111 ^ 0000 0010
		 * 1111 1101 ^ 0000 0001
		 * 1111 1100
		 * 
		 * Border Type (Const.ALWAYS):
		 * 0000 1000
		 * 
		 *     1111 1100
		 * AND 0000 1000
		 *     ---------
		 *     0000 1000
		 *     
		 * Player Type (Const.PLAYER):
		 * 
		 * 0000 0010 | 0010 0000
		 * 0010 0010
		 * 
		 * Border Mask:
		 * 1111 1111
		 * 
		 *     0010 0010
		 * AND 1111 1111
		 *     ---------
		 *     0010 0010
		 */
	}
	
	public static class LogTags {
		public static final String PHYSIC = "Physic";
		public static final String RENDERING = "Rendering";
		public static final String MISC = "Misc";
	}
	
	public static class RenderLayer {
		
		//Layer werden von MIN nach MAX gerendert, also sind die mit einer höheren zahl am obersten
		
		public static final short MIN = 1;
		
		/**
		 * Hintergrund
		 */
		public static final short BACKGROUND = 1;
		/**
		 * Alles bei dem es nicht gesezt ist
		 */
		public static final short DEFAULT = 2; 
		/**
		 * Entites wie Enemy und Player
		 */
		public static final short ENTITIES = 2;
		/**
		 * Vordergund (Waffen und Projektile, ...)
		 */
		public static final short FOREGROUND = 3;
		/**
		 * UI Elemente in der welt (Health Bars, ...)
		 */
		public static final short WORLD_UI = 4; 
		/**
		 * UI Elemente die am Fenster Fixiert sind (Menüs, ...)
		 */
		public static final short WINDOW_UI = 5;
		
		public static final short MAX = 5;
		
	}
	
	public static final float METER_TO_PIXEL_RATIO = 225;
	public static final float PIXEL_TO_METER_RATIO = 1/METER_TO_PIXEL_RATIO;
	
}

final class Collison {
	
}