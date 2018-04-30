package com.mygdx.game.items;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

public abstract class Gun extends Item {
	
	protected float reloadTime;
	protected int ammo;
	protected int magAmmo;
	protected int maxAmmo;
	protected int magSize;
	protected float fireRate;
	protected boolean auto;
	protected float bulletSpeed;
	private double miscTimer;
	private boolean reloading;
	private boolean cooldown;
	
	public Gun(String name, float reloadTime, int ammo, int maxAmmo, int magSize, float fireRate, float bulletSpeed, boolean auto) {
		super(name);
		this.reloadTime = reloadTime;
		this.ammo = Math.min(maxAmmo, ammo);
		this.maxAmmo = maxAmmo;
		this.magSize = magSize;
		this.fireRate = fireRate;
		this.auto = auto;
		this.bulletSpeed = bulletSpeed;
		magAmmo = Math.min(magSize, ammo);
	}
	
	@Override
	public void update(Engine engine, Camera cam) {
		
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
			if(tryShoot(engine, cam)) {
				magAmmo--;
				if(magAmmo == 0) {
					reloading = true;
				} else {
					cooldown = true;
				}
				miscTimer = currTime;
			}
		}
	}
	
	protected abstract boolean tryShoot(Engine engine, Camera cam);
	
	protected Vector2 getOwnerCenter() {
		return new Vector2(owner.positionComp.pos).add(new Vector2(owner.visualComp.getWidth()/2, owner.visualComp.getHeight()/2));
	}
	
	protected Vector2 getProjectileVelocityTowards(Vector2 target) {
		Vector2 vel = new Vector2(target).sub(getOwnerCenter());
		vel.nor();
		vel.scl(bulletSpeed);
		vel.add(owner.velocityComp.vel);
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
