package com.flamboyant.tappanic.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.phases.BossPhaseRenderer;
import com.flamboyant.tappanic.render.phases.GameOverPhaseRenderer;
import com.flamboyant.tappanic.render.phases.GameStarterPhaseRenderer;
import com.flamboyant.tappanic.render.phases.IceBlockPhaseRenderer;
import com.flamboyant.tappanic.render.phases.NormalPhaseRenderer;
import com.flamboyant.tappanic.render.phases.PhaseRenderer;

import com.flamboyant.tappanic.render.scripted.SkyRenderer;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.TapPanicWorld;

import java.util.Iterator;

/**
 * Created by Reborn Portable on 23/11/2015.
 */
public class TapPanicRenderer {
    private float lastTimerTick;
    private TapPanicGame game;
    private TapPanicWorld world;
    private OrthographicCamera camera;
    private ObjectMap<String, PhaseRenderer> phasesRenderer;
    private SkyRenderer skyRenderer;
    private boolean bossMode;

    public TapPanicRenderer(TapPanicWorld world, TapPanicGame g, OrthographicCamera camera)
    {
        game = g;
        this.camera = camera;
        this.world = world;
        lastTimerTick = TimeUtils.millis();

        skyRenderer = new SkyRenderer(g.assetManager, g);

        phasesRenderer = new ObjectMap<String, PhaseRenderer>();
        phasesRenderer.put("Starter", new GameStarterPhaseRenderer(world.getPhase("Starter"), game, camera));
        phasesRenderer.put("GameOver", new GameOverPhaseRenderer(world.getPhase("GameOver"), game, this));
        phasesRenderer.put("Normal", new NormalPhaseRenderer(world.getPhase("Normal"), game, skyRenderer));
        phasesRenderer.put("IceBlock", new IceBlockPhaseRenderer(world.getPhase("IceBlock"), game));
        phasesRenderer.put("Boss", new BossPhaseRenderer(world.getPhase("Boss"), this, game, skyRenderer, camera));

        initElements();
    }

    public void reset()
    {
        for(Iterator<PhaseRenderer> i = phasesRenderer.values(); i.hasNext(); ) {
            i.next().reset();
        }

        skyRenderer.reset();

        stopMusics();
    }

    public void loadAssets() {
        AssetPack texturesPack = new AssetPack();
        AssetPack musicsPack = new AssetPack();
        AssetPack soundsPack = new AssetPack();

        for(Iterator<PhaseRenderer> i = phasesRenderer.values(); i.hasNext(); ) {
            i.next().completeAssetPacksForLoading(texturesPack, soundsPack, musicsPack);
        }

        skyRenderer.completeAssetPacksForLoading(texturesPack, soundsPack, musicsPack);

        game.assetManager.loadPack(texturesPack, soundsPack, musicsPack);
    }

    public boolean updateLoading() {
        if (!game.assetManager.update())
            return false;

        TextureRegionPack trPack = new TextureRegionPack();
        AnimationPack animPack = new AnimationPack();

        for(Iterator<PhaseRenderer> i = phasesRenderer.values(); i.hasNext(); ) {
            i.next().completePacksForPostLoading(trPack, animPack);
        }

        skyRenderer.completePacksForPostLoading(trPack, animPack);

        game.assetManager.postLoadingAssetsGeneration(trPack, animPack);

        return true;
    }

    private void initElements()
    {
    }

    public void render(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer)
    {
        float timeNow = TimeUtils.millis();
        lastTimerTick = timeNow;


        phasesRenderer.get("Normal").draw(batcher, delta, shapeRenderer);
        phasesRenderer.get("IceBlock").draw(batcher, delta, shapeRenderer);
        phasesRenderer.get("Boss").draw(batcher, delta, shapeRenderer);
        phasesRenderer.get("Starter").draw(batcher, delta, shapeRenderer);
        phasesRenderer.get("GameOver").draw(batcher, delta, shapeRenderer);
        /*for(Iterator<PhaseRenderer> i = phasesRenderer.values(); i.hasNext(); ) {
            i.next().draw(batcher, delta, shapeRenderer);
        }*/

        if (GameContants.DEBUG_MODE)
            game.applicationDataDebug.draw(batcher);
    }

    public void stopMusics()
    {
        for (ObjectMap.Entries<String, PhaseRenderer> i = phasesRenderer.iterator(); i.hasNext(); )
        {
            ObjectMap.Entry<String, PhaseRenderer> item = i.next();
            item.value.stopMusics();
        }
    }

    public PhaseRenderer getRenderer(String key)
    {
        return this.phasesRenderer.get(key);
    }

    public boolean isBossMode() {
        return bossMode;
    }

    public void setBossMode(boolean bossMode) {
        this.bossMode = bossMode;
    }
}
