package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Gun extends Item<GunAttribs> {
	
	protected float reloadTime;
	protected int magAmmo;
	protected int maxAmmo;
	protected int magSize;
	protected float fireRate;
	protected boolean auto;
	protected float bulletSpeed;
	protected float spread;
	
	private double miscTimer;
	private boolean reloading;
	private boolean cooldown;
	protected int ammo;
	
	/**
	 * 
	 * @param name
	 * @param reloadTime
	 * @param ammo
	 * @param maxAmmo
	 * @param magSize
	 * @param fireRate
	 * @param bulletSpeed
	 * @param auto
	 * @param spread in grad, nach links und rechts, nicht von links nach rechts
	 */
	public Gun(String name, float reloadTime, int ammo, int maxAmmo, int magSize, float fireRate, float bulletSpeed, boolean auto, float spread) {
		super(name);
		this.reloadTime = reloadTime;
		this.ammo = Math.min(maxAmmo, ammo);
		this.maxAmmo = maxAmmo;
		this.magSize = magSize;
		this.fireRate = fireRate;
		this.auto = auto;
		this.bulletSpeed = bulletSpeed;
		this.spread = spread;
		magAmmo = Math.min(magSize, ammo);
	}
	
	/*public public Gun() {
		super(new GunAttribs)
	}*/
	
	@Override
	public void update(World world, Engine engine, Camera cam, AssetManager assets) {
		
		if(isEmpty())
			return;
		
		double currTime = System.currentTimeMillis()/1000d;
		
		if(reloading && currTime-miscTimer >= reloadTime) {
			magAmmo = Math.min(ammo, magSize);
			ammo -= magAmmo;
			reloading = false;
		}
		
		if(cooldown && currTime-miscTimer >= 1./fireRate)
			cooldown = false;
		
		if(!reloading && !cooldown) {
			if(tryShoot(world, engine, cam, assets)) {
				magAmmo--;
				if(magAmmo == 0) {
					reloading = true;
				} else {
					cooldown = true;
				}
				miscTimer = currTime;
			}
		}
//		printStatus();
	}
	
	protected void printStatus() {
		String status = "";
		double currTime = System.currentTimeMillis()/1000d;
		if(isReloading()) {
			status = "Reloading... ("+Math.round((reloadTime-(currTime-miscTimer))*100)/100f+")";
		} else if (isOnCooldown()) {
			status = "Chambering... ("+Math.round((1./fireRate-(currTime-miscTimer))*100)/100f+")";
		}
		System.out.println(name + ": " + magAmmo+"/"+magSize + " ("+ammo+"/"+maxAmmo+") "+status);
	}
	
	protected abstract boolean tryShoot(World world, Engine engine, Camera cam, AssetManager assets);
	
	protected Vector2 getOwnerCenter() {
		return new Vector2(owner.getTransformComp().pos);
	}
	
	protected Vector2 getProjectileVelocityTowards(Vector2 target, boolean applySpread) {
		Vector2 vel = new Vector2(target).sub(getOwnerCenter());
		vel.nor();
		if(applySpread) {
			//Zufällige zahl von -1 - 1 mal dem spread in beide reichtungen plus die originale richtung gibt die neue richtung
			vel.setAngle((float) (spread * (Math.random() * 2 - 1)) + vel.angle());
		}
		vel.scl(bulletSpeed);
		vel.add(owner.getVelocityComp().vel);
		return vel;
	}
	
	public boolean isReloading() {
		return reloading;
	}
	
	public boolean isOnCooldown() {
		return cooldown;
	}
	
	public boolean isEmpty() {
		return ammo+magAmmo == 0 ? true : false;
	}
	
	public void setAmmo(int ammo) {
		this.ammo = Math.min(maxAmmo, ammo);
	}
	
	public void addAmmo(int ammo) {
		setAmmo(this.ammo + ammo);
	}
}
