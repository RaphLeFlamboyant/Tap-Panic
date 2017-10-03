package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.flamboyant.tappanic.tools.assetpack.AnimationPack;
import com.flamboyant.tappanic.tools.assetpack.AnimationParameters;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionPack;
import com.flamboyant.tappanic.tools.assetpack.TextureRegionParameters;

import java.util.Iterator;

/**
 * Created by Reborn Portable on 10/02/2016.
 */
public class HighLevelAssetManager {
    private AssetManager manager = new AssetManager();

    private ObjectMap<String, String> textureKeys = new ObjectMap<String, String>();
    private ObjectMap<String, String> soundKeys = new ObjectMap<String, String>();
    private ObjectMap<String, String> musicKeys = new ObjectMap<String, String>();

    private ObjectMap<String, TextureRegion> textureRegions = new ObjectMap<String, TextureRegion>();
    private ObjectMap<String, Animation> animations = new ObjectMap<String, Animation>();
    private ObjectMap<String, BitmapFont> fonts = new ObjectMap<String, BitmapFont>();

    private ObjectMap<String, String> textureRegionsKeys = new ObjectMap<String, String>();
    private ObjectMap<String, String> animationsKeys = new ObjectMap<String, String>();
    private ObjectMap<String, String> fontsKeys = new ObjectMap<String, String>();

    public void freeMemory()
    {
        //Il se peut que tout ne soit pas load quand on free les textures donc on dumb catch les exceptions
        for (ObjectMap.Entries<String, String> iter = textureKeys.entries(); iter.hasNext(); ) {
            try {
                ObjectMap.Entry<String, String> item = iter.next();
                manager.get(item.value, Texture.class).dispose();
            }
            catch (Exception e)
            {}
        }
        for (ObjectMap.Entries<String, String> iter = soundKeys.entries(); iter.hasNext(); ) {
            try{
                ObjectMap.Entry<String, String> item = iter.next();
                manager.get(item.value, Sound.class).dispose();
            }
            catch (Exception e)
            {}
        }
        for (ObjectMap.Entries<String, String> iter = musicKeys.entries(); iter.hasNext(); ) {
            try{
                ObjectMap.Entry<String, String> item = iter.next();
                manager.get(item.value, Music.class).dispose();
            }
            catch (Exception e)
            {}
        }

        textureKeys.clear();
        animationsKeys.clear();;
        fontsKeys.clear();
        musicKeys.clear();
        soundKeys.clear();
        textureKeys.clear();
        textureRegionsKeys.clear();

        textureRegions.clear();
        fonts.clear();
        animations.clear();
    }

    public void loadPack(AssetPack texturePack, AssetPack soundPack, AssetPack musicPack) {
        if (texturePack != null) {
            for (ObjectMap.Entries<String, String> iter = texturePack.getAssetPathList().entries(); iter.hasNext(); ) {
                ObjectMap.Entry<String, String> item = iter.next();
                manager.load(item.value, Texture.class);
                textureKeys.put(item.key, item.value);
            }
        }


        if (soundPack != null){
            for (ObjectMap.Entries<String, String> iter = soundPack.getAssetPathList().entries(); iter.hasNext(); ) {
                ObjectMap.Entry<String, String> item = iter.next();
                manager.load(item.value, Sound.class);
                soundKeys.put(item.key, item.value);
            }
        }

        if (musicPack != null) {
            for (ObjectMap.Entries<String, String> iter = musicPack.getAssetPathList().entries(); iter.hasNext(); ) {
                ObjectMap.Entry<String, String> item = iter.next();
                manager.load(item.value, Music.class);
                musicKeys.put(item.key, item.value);
            }
        }
    }

    public boolean update()
    {
        return manager.update();
    }

    public float getProgress()
    {
        return manager.getProgress();
    }

    public void finishLoading()
    {
        manager.finishLoading();
    }

    public void postLoadingAssetsGeneration(TextureRegionPack trPack, AnimationPack animPack)
    {
        if (trPack != null) {
            for (int i = 0; i < trPack.getParametersList().size; i++) {
                TextureRegionParameters params = trPack.getParametersList().get(i);

                if (textureRegions.containsKey(params.getTextureRegionName()))
                    continue;

                if (!textureKeys.containsKey(params.getTextureName()))
                    continue;

                Texture texture = manager.get(textureKeys.get(params.getTextureName()), Texture.class);
                texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                TextureRegion item;

                if (params.getX() != -1)
                    item = new TextureRegion(texture, params.getX(), params.getY(), params.getWidth(), params.getHeigth());
                else
                    item = new TextureRegion(texture);

                item.flip(false, params.isFlipY());
                textureRegions.put(params.getTextureRegionName(), item);
                textureRegionsKeys.put(params.getTextureRegionName(), params.getTextureName());
            }
        }

        if (animPack != null) {
            for (int i = 0; i < animPack.getParametersList().size; i++) {
                AnimationParameters params = animPack.getParametersList().get(i);

                if (animations.containsKey(params.getAnimationName()))
                    continue;

                if (!textureKeys.containsKey(params.getTextureName()))
                    continue;

                Texture texture = manager.get(textureKeys.get(params.getTextureName()), Texture.class);

                int maxWidth = texture.getWidth();
                int maxHeigth = texture.getHeight();
                int x = 0;
                int y = 0;
                Array<TextureRegion> itemsAnim = new Array<TextureRegion>(100);

                while (y + params.getHeigth() <= maxHeigth) {
                    x = 0;
                    while (x + params.getWidth() <= maxWidth) {
                        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

                        TextureRegion item = new TextureRegion(texture, x, y, params.getWidth(), params.getHeigth());
                        item.flip(false, true);
                        itemsAnim.add(item);

                        x += params.getWidth();
                    }
                    y += params.getHeigth();
                }

                Animation anim = new Animation(0.06f, itemsAnim);
                anim.setPlayMode(params.getPlayMode());
                animations.put(params.getAnimationName(), anim);
                animationsKeys.put(params.getAnimationName(), params.getTextureName());
            }
        }
    }

    public Texture getTexture(String textureName)
    {
        return manager.get(textureKeys.get(textureName));
    }

    public Sound getSound(String soundName)
    {
        return manager.get(soundKeys.get(soundName));
    }

    public Music getMusic(String musicName)
    {
        return manager.get(musicKeys.get(musicName));
    }

    public TextureRegion getTextureRegion(String trName)
    {
        return textureRegions.get(trName);
    }

    public Animation getAnimation(String animName)
    {
        return animations.get(animName);
    }

    public BitmapFont getFont(String name)
    {
        if (fonts.containsKey(name))
            return fonts.get(name);

        return null;
    }

    public BitmapFont loadFontFromGenerator(String ttfPath, String fontName, int size, Color color)
    {
        if (fonts.containsKey(fontName))
            return fonts.get(fontName);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(ttfPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        parameter.flip = true;

        BitmapFont res = generator.generateFont(parameter);
        generator.dispose();
        fonts.put(fontName, res);
        fontsKeys.put(fontName, ttfPath);

        return res;
    }

}
