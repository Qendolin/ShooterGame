package com.mygdx.game.map;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.utils.BodyFactory;
import com.mygdx.game.utils.Const;

public class MapPreparer {

	public static void prepare(TiledMap map, World world, Vector2 spawnOffset) {
		
		Iterator<MapLayer> layerIterator = map.getLayers().iterator();
		float spawnX = map.getProperties().get("spawnX", 0f, Float.class);
		float spawnY = map.getProperties().get("spawnY", 0f, Float.class);
		while(layerIterator.hasNext()) {
			MapLayer layer = layerIterator.next();
			if(layer.getParent() != null) continue;
			layer.setOffsetX(layer.getOffsetX()-spawnX-spawnOffset.x);
			layer.setOffsetY(layer.getOffsetY()+spawnY/2+spawnOffset.y);
		}
		
		//Collision Layers absichtilich vertauscht!!

		layerIterator = map.getLayers().iterator();
		while(layerIterator.hasNext()) {
			MapLayer layer = layerIterator.next();
			MapObjects objects = layer.getObjects();
			for(RectangleMapObject o : objects.getByType(RectangleMapObject.class)) {
				BodyFactory.createRectangle(world, BodyType.StaticBody, false, Const.Collision.ALL, Const.Collision.MAP_OBJECT_COLLIDER,
						o.getRectangle().getCenter(new Vector2()).add(layer.getOffsetX(), -layer.getOffsetY()), o.getRectangle().width, o.getRectangle().height, false);
			}
			for(PolygonMapObject o : objects.getByType(PolygonMapObject.class)) {
				float[] vertVals = o.getPolygon().getVertices();
				Vector2[] verts = new Vector2[vertVals.length/2];
				for(int i = 0; i < vertVals.length; i+=2)  {
					verts[i/2] = new Vector2(vertVals[i], vertVals[i+1]);
				}
				BodyFactory.createPolygon(world, BodyType.StaticBody, false, Const.Collision.ALL, Const.Collision.MAP_OBJECT_COLLIDER, 
						new Vector2(o.getPolygon().getX(), o.getPolygon().getY()).add(layer.getOffsetX(), -layer.getOffsetY()), verts, false);
			}
			for(CircleMapObject o : objects.getByType(CircleMapObject.class)) {
				BodyFactory.createCircle(world, BodyType.StaticBody, false, Const.Collision.ALL, Const.Collision.MAP_OBJECT_COLLIDER, 
						new Vector2(o.getCircle().x, o.getCircle().y).add(layer.getOffsetX(), -layer.getOffsetY()), o.getCircle().radius, false);		
			}
		}
	}
	
}
