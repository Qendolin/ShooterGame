package com.mygdx.game.utils;

/**
 * Diese Klasse ist da um konstanten zu deffinieren
 */
public class Const {

	public static final short NONE = 0x0;
	public static final short NEVER = 0x0;
	public static final short PROJECTILE = 0x1; //0000 0001
	public static final short ENTITY = 0x2;     //0000 0010
	public static final short BIG_ENTITY = ENTITY | 0x4; //0000 0100
	public static final short ALWAYS = 0x8; //0000 1000
	public static final short DEFAULT = 0x10; //0001 0000
	public static final short PLAYER = ENTITY | 0x20; //0010 0100
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
	
	public static final float METER_TO_PIXEL_RATIO = 1000;
	public static final float PIXEL_TO_METER_RATIO = 1/METER_TO_PIXEL_RATIO;
	
}