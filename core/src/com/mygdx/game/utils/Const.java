package com.mygdx.game.utils;

/**
 * Diese Klasse ist da um konstanten zu deffinieren
 */
public class Const {

	public static final short NONE = 0x0;
	public static final short NEVER = 0x0;
	public static final short PROJECTILE = 0x1;
	public static final short ENTITY = 0x2;
	public static final short BIG_ENTITY = ENTITY | 0x4;
	public static final short ALWAYS = 0x8;
	public static final short DEFAULT = 0x10;
	public static final short PLAYER = ENTITY | 0x20;
	public static final short ALL = 0xFFF;
	
	public static final float METER_TO_PIXEL_RATIO = 1000;
	public static final float PIXEL_TO_METER_RATIO = 1/METER_TO_PIXEL_RATIO;
	
}