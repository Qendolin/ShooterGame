package com.mygdx.game.screens;

import java.util.ArrayList;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ShooterGame;
import com.mygdx.game.engineSystems.MovementSystem;
import com.mygdx.game.engineSystems.PhysicSystem;
import com.mygdx.game.engineSystems.UpdateSystem;
import com.mygdx.game.entites.Enemy;
import com.mygdx.game.entites.EnemyType;
import com.mygdx.game.entites.Player;
import com.mygdx.game.entityComponents.BodyComp;
import com.mygdx.game.entityComponents.TrasformationComp;
import com.mygdx.game.entityComponents.VisualComp;
import com.mygdx.game.entityComponents.events.CollisionEvent;
import com.mygdx.game.entityComponents.misc.BodyDeleteFlag;
import com.mygdx.game.entityComponents.misc.CollisionEntityConnection;
import com.mygdx.game.entityComponents.visuals.SpriteSheetSpriteGroup;
import com.mygdx.game.entityComponents.visuals.SpriteSheetVis;
import com.mygdx.game.map.MapPreparer;
import com.mygdx.game.utils.Const;
import com.mygdx.game.utils.EnemyFactory;
import com.mygdx.game.utils.Const.Paths;
import com.mygdx.game.utils.Const.RenderLayer;

public class GameScreen implements Screen{
	
	private final ShooterGame game;
	private Player player;
	private OrthographicCamera cam;
	private World world;
	private Box2DDebugRenderer physicDebugRenderer;
	private MovementSystem moveSys = new MovementSystem();	
	private PhysicSystem physSys;
	private MapRenderer mapRenderer;
	private TiledMap map;
	private Stage stage;
	public AssetManager assets = new AssetManager();
	
	private boolean preInit = false;
	
	@SuppressWarnings("rawtypes")
	private ComponentMapper<VisualComp> rm = ComponentMapper.getFor(VisualComp.class);
	private ComponentMapper<TrasformationComp> pm = ComponentMapper.getFor(TrasformationComp.class);

	public GameScreen(final ShooterGame game) {
		this.game = game;
		preInit = true;
		loadAssets();
	}
	
	public void loadAssets() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets.setLoader(Texture.class, new TextureLoader(resolver));
		assets.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
		assets.setLoader(Skin.class, new SkinLoader(resolver));
		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.load(Paths.SPRITES+"george.png", Texture.class);
		assets.load(Paths.SPRITES+"bahamut.png", Texture.class);
		assets.load(Paths.SPRITES+"bullet.png", Texture.class);
		assets.load(Paths.SPRITES+"schrot.png", Texture.class);
		assets.load(Paths.SKINS+"default/uiskin.atlas", TextureAtlas.class);

		try {
			game.setScreen(new LoadingScreen(game, assets, GameScreen.class.getConstructor(ShooterGame.class, AssetManager.class)));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public GameScreen(ShooterGame game, AssetManager assets) {
		this.game = game;
		this.assets = assets;
		preInit = false;
		
		for(String name : assets.getAssetNames())
			System.out.println("Loaded Asset: "+name);
		
		initEssentials();
		initMap();
		initUI();
		
	}
	
	public void initEssentials() {
		cam = new OrthographicCamera(Gdx.graphics.getWidth()*1.25f, Gdx.graphics.getHeight()*1.25f);
		cam.position.x = Gdx.graphics.getWidth()/2; 
		cam.position.y = Gdx.graphics.getHeight()/2;
		
		world = new World(new Vector2(), false);
		world.setContactListener(new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold arg1) {
			}
			
			@Override
			public void postSolve(Contact arg0, ContactImpulse arg1) {		
			}
			
			@Override
			public void endContact(Contact contact) {
			}
			
			@Override
			public void beginContact(Contact contact) {
				Fixture fixA = contact.getFixtureA();
				Fixture fixB = contact.getFixtureB();
				if(fixA == null || fixB == null) return;
				if(fixA.getUserData() == null && ! (fixA.getUserData() instanceof CollisionEntityConnection)) return;
				if(fixB.getUserData() == null && ! (fixB.getUserData() instanceof CollisionEntityConnection)) return;
				CollisionEntityConnection connectionA = (CollisionEntityConnection) fixA.getUserData();
				CollisionEntityConnection connectionB = (CollisionEntityConnection) fixB.getUserData();
				if(connectionA.listener != null)
					connectionA.listener.handle(new CollisionEvent(contact, connectionA.entity, connectionB.entity));
				if(connectionB.listener != null)
					connectionB.listener.handle(new CollisionEvent(contact, connectionB.entity, connectionA.entity));
			}
		});
		
		game.engine = new Engine();
		game.engine.addSystem(moveSys);
		UpdateSystem updateSys = new UpdateSystem(world, game.engine, cam, assets);
		game.engine.addSystem(updateSys);
		physSys = new PhysicSystem(world);
		game.engine.addSystem(physSys);
		
		physicDebugRenderer = new Box2DDebugRenderer();
	}
	
	public void initMap() {
		//BodyFactory.createBorder(world, new Vector2(), 1000, 1000);
		Texture playerSpriteSheet = assets.get(Paths.SPRITES+"george.png", Texture.class);
		Texture enemySpriteSheet = assets.get(Paths.SPRITES+"bahamut.png", Texture.class);
		
		
		
		player = new Player(world, game.engine, new Vector2(0,0), new SpriteSheetVis(playerSpriteSheet, 4, 4, true, new SpriteSheetSpriteGroup(0, 3, 0.1f, Player.ANIM_WALK_DOWN), 
																			   new SpriteSheetSpriteGroup(4, 7, 0.1f, Player.ANIM_WALK_LEFT),
																			   new SpriteSheetSpriteGroup(8, 11, 0.1f, Player.ANIM_WALK_UP),
																			   new SpriteSheetSpriteGroup(12, 15, 0.1f, Player.ANIM_WALK_RIGHT),
																			   new SpriteSheetSpriteGroup(0, 3, 0.05f, Player.ANIM_RUN_DOWN), 
																			   new SpriteSheetSpriteGroup(4, 7, 0.05f, Player.ANIM_RUN_LEFT),
																			   new SpriteSheetSpriteGroup(8, 11, 0.05f, Player.ANIM_RUN_UP),
																			   new SpriteSheetSpriteGroup(12, 15, 0.05f, Player.ANIM_RUN_RIGHT)));
		game.engine.addEntity(player);
		
		EnemyFactory boss1Fact = EnemyFactory.create(world, game.engine, EnemyType.Boss1, new SpriteSheetVis(enemySpriteSheet, 4, 4, false, new SpriteSheetSpriteGroup(0, 3, 0.1f, Enemy.ANIM_WALK_DOWN), 
				   new SpriteSheetSpriteGroup(4, 7, 0.1f, Enemy.ANIM_WALK_LEFT),
				   new SpriteSheetSpriteGroup(8, 11, 0.1f, Enemy.ANIM_WALK_RIGHT),
				   new SpriteSheetSpriteGroup(12, 15, 0.1f, Enemy.ANIM_WALK_UP)));
		
		boss1Fact.spawn(new Vector2(), 500, 1);
		
		TmxMapLoader.Parameters mapParams = new TmxMapLoader.Parameters();
	    mapParams.textureMagFilter = TextureFilter.Nearest;
	    mapParams.textureMinFilter = TextureFilter.Nearest;
		map = new TmxMapLoader().load("./maps/test.tmx", mapParams);
		MapPreparer.prepare(map, world, new Vector2());
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1);
	}
	
	public void initUI() {
		Skin skin = new Skin(Gdx.files.internal(Paths.SKINS+"default/uiskin.json"), assets.get(Paths.SKINS+"default/uiskin.atlas", TextureAtlas.class));
		stage = new Stage(new ScreenViewport());
		stage.addActor(new Label("UI Text", skin));
		stage.addActor(new TextButton("UI Button", skin));
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		world.dispose();
		stage.dispose();
		assets.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		if(preInit) return;
		
		updateGame(delta);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderGame(delta);
		renderUI(delta);
	}
	
	public void updateGame(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) //Space drücken stoppt alle bewegeung
			moveSys.setProcessing(false);
		else if(!moveSys.checkProcessing())
			moveSys.setProcessing(true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) //Alt drücken macht noclip an
			player.getBodyComp().getBody().getFixtureList().get(0).setSensor(true);
		else if(player.getBodyComp().getBody().getFixtureList().get(0).isSensor())
			player.getBodyComp().getBody().getFixtureList().get(0).setSensor(false);

		//Alle systeme werden aufgerufen und ausgeführ
		game.engine.update(Gdx.graphics.getDeltaTime());
		
		//Bodyies mit BodyDeleteFlag als UserData löschen
		deleteBodies();
		
		//Camera bewegen
		//Die kontrolle der kamera gehört nicht in die spieler klasse!! (Weil man z.b. mehrer spieler haben kann)
		cam.position.x = player.getTransformComp().pos.x;
		cam.position.y = player.getTransformComp().pos.y;
		cam.update();
	}
	
	public void renderUI(float delta) {
		stage.act(delta);
		stage.draw();
	}
	
	public void renderGame(float delta) {
		
		//Map rendern
		mapRenderer.setView(cam);
		mapRenderer.render();
		
		//Sprites rendern
		Family renderableFamily = Family.all(VisualComp.class, TrasformationComp.class).get();
		ImmutableArray<Entity> renderables = game.engine.getEntitiesFor(renderableFamily);
		
		//Sprites sortiert nach layer
		@SuppressWarnings("unchecked")
		ArrayList<Sprite>[] sorted = (ArrayList<Sprite>[])new ArrayList[RenderLayer.MAX-RenderLayer.MIN + 1];
		for(int i = 0; i < sorted.length; i++) {
			sorted[i] = new ArrayList<>();
		}
		
		for(Entity renderable : renderables) {
			VisualComp<?> visualComp = rm.get(renderable);
			BodyComp bodyComp = renderable.getComponent(BodyComp.class);
			for(int i = 0; i < visualComp.visual.getNumberOfSprites() || i == 0; i++) {
				Sprite sprite = visualComp.visual.get(i);
				if(sprite == null)
					continue;
				Vector2 pos = pm.get(renderable).pos;
				//Zentrieren
				sprite.setPosition(pos.x-sprite.getWidth()/2f, pos.y-sprite.getHeight()/2f);
				if(bodyComp != null && bodyComp.getBody() != null)
					sprite.setRotation((float) Math.toDegrees(bodyComp.getBody().getAngle()));
//				sprite.draw(batch);
				sorted[visualComp.visual.renderLayer-RenderLayer.MIN].add(sprite);
			}
		}
		
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
		for(int i = 0; i <= RenderLayer.MAX - RenderLayer.MIN; i++) {
			ArrayList<Sprite> layerSprites = sorted[i];
			for(Sprite sprite : layerSprites)
				sprite.draw(game.batch);
		}
		game.batch.end();
		
		//Physic debug render
		Matrix4 physicMVMatrix = cam.combined.cpy();
		physicMVMatrix.scl(Const.METER_TO_PIXEL_RATIO);
		physicDebugRenderer.render(world, physicMVMatrix);
	}
	
	private void deleteBodies() {
		Array<Body> bodies = new Array<Body>(world.getBodyCount());
		world.getBodies(bodies);
		for(int i = 0; i < bodies.size; i++) {
			if(bodies.get(i).getUserData() != null && bodies.get(i).getUserData() instanceof BodyDeleteFlag)
				world.destroyBody(bodies.get(i));
			
		}
	}

	@Override
	public void resize(int width, int height) {
		if(preInit) return;
		
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
