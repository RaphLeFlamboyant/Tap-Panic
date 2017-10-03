package com.flamboyant.tappanic.render.phases;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;

import com.flamboyant.common.TextureRegionMovableObject;
import com.flamboyant.tappanic.TapPanicGame;
import com.flamboyant.tappanic.render.AnimationPositionObject;
import com.flamboyant.tappanic.tools.GameContants;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.world.SymbolCapsule;
import com.flamboyant.tappanic.world.phases.GameplayPhaseModule;
import com.flamboyant.tappanic.world.phases.IceBlockPhase;

import java.util.Iterator;

/**
 * Created by Reborn Portable on 08/01/2016.
 */
public class IceBlockPhaseRenderer extends PhaseRenderer {
    private IceBlockPhase phase;
    private TapPanicGame game;
    private boolean justActivate;
    private float iceTmp;
    private ObjectMap<SymbolCapsule, Integer> previousResistance = new ObjectMap<SymbolCapsule, Integer>();
    //TODO : ajouter un visuel et effet sonore quand on tape sur les blocks de glace et quand ils cassent

    public IceBlockPhaseRenderer(GameplayPhaseModule gamePhase, TapPanicGame g)
    {
        super(gamePhase);
        game = g;
        assert gamePhase.getClass().equals(IceBlockPhase.class);

        phase = ((IceBlockPhase) gamePhase);
    }

    public void completeAssetPacksForLoading(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack)
    {
        texturePack.add("brokenIce", "Pictures/IceBlock/brokenIce.png");

        texturePack.add("ice", "Animations/ice.png");

        soundPack.add("iceblockHit", "Sounds/Effects/iceblockHit.mp3");
        soundPack.add("iceblockFreeze", "Sounds/Effects/iceblockFreeze.mp3");
        soundPack.add("iceblockBreak", "Sounds/Effects/iceblockBreak.mp3");
    }


    public void completePacksForPostLoading(TextureRegionPack trPack, AnimationPack animPack){
        trPack.add("brokenIce", "brokenIce1", 761, 0, 378, 378);
        trPack.add("brokenIce", "brokenIce2", 1142, 0, 378,378);

        animPack.add("ice", "ice", 378, 378, Animation.PlayMode.LOOP);
    }

    @Override
    public void reset()
    {
        super.reset();

        justActivate = false;
        iceTmp = 0;
        previousResistance.clear();
    }

    @Override
    public void activate()
    {
        previousResistance.clear();
        ObjectMap<SymbolCapsule, Integer> iceRes = phase.getIceResistances();

        for(ObjectMap.Entries<SymbolCapsule, Integer> i = iceRes.iterator(); i.hasNext(); )
        {
            ObjectMap.Entry<SymbolCapsule, Integer> item = i.next();
            previousResistance.put(item.key, item.value);
        }

        justActivate = true;
        iceTmp = 0;
    }

    private void playSound(String soundName)
    {
        if (TapPanicGame.soundEnabled)
            game.assetManager.getSound(soundName).play();
    }

    @Override
    public void draw(SpriteBatch batcher, float delta, ShapeRenderer shapeRenderer) {
        if (!phase.isActive() || phase.isPaused())
            return;

        if (justActivate)
        {
            playSound("iceblockFreeze");
            justActivate = false;
        }

        //TODO : gérer les PV tout ça
        for(ObjectMap.Entries<SymbolCapsule, Integer> i = phase.getIceResistances().iterator(); i.hasNext(); ) {
            ObjectMap.Entry<SymbolCapsule, Integer> item = i.next();
            SymbolCapsule capsule = item.key;

            if (previousResistance.get(item.key) > item.value) {
                ;//effet sonore
                if (item.value == 0)
                    playSound("iceblockBreak");
                else
                    playSound("iceblockHit");
            }

            previousResistance.put(item.key, item.value);

            iceTmp += (delta / 3);
            if (item.value == GameContants.ICE_RESISTANCE) {
                batcher.draw(game.assetManager.getAnimation("ice").getKeyFrame(iceTmp),
                        capsule.getPosition().x - (capsule.getSize().x / 5), capsule.getPosition().y - (capsule.getSize().y / 5),
                        capsule.getSize().x / 2 , capsule.getSize().y / 2,
                        capsule.getSize().x + (capsule.getSize().x / 5 * 2), capsule.getSize().y + (capsule.getSize().y / 5 * 2),
                        1, 1, capsule.getRotation());
            }
            else if (item.value > 0) {
                String textureName = "";
                if (item.value == 2)
                    textureName = "brokenIce1";
                else
                    textureName = "brokenIce2";

                batcher.draw(game.assetManager.getTextureRegion(textureName),
                        capsule.getPosition().x - (capsule.getSize().x / 5), capsule.getPosition().y - (capsule.getSize().y / 5),
                        capsule.getSize().x / 2 , capsule.getSize().y / 2,
                        capsule.getSize().x + (capsule.getSize().x / 5 * 2), capsule.getSize().y + (capsule.getSize().y / 5 * 2),
                        1, 1, capsule.getRotation());
            }
        }
    }
}
