package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.HashMap;

/**
 * Created by Reborn Portable on 14/10/2015.
 */
public class OldAssetsLoader {
    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
    private static HashMap<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
    private static HashMap<String, Animation> animations = new HashMap<String, Animation>();
    private static HashMap<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
    private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    private static HashMap<String, Music> musics = new HashMap<String, Music>();


    public static Texture loadTexture(String filePath, String textureName)
    {
        if (textures.containsKey(textureName))
            return textures.get(textureName);

        FileHandle handle = Gdx.files.internal(filePath);

        if (handle.exists()){
            Texture texture = new Texture(handle);
            textures.put(textureName, texture);

            return texture;
        }

        return null;
    }

    public static Texture getTexture(String textureName)
    {
        if (textures.containsKey(textureName))
            return textures.get(textureName);

        return null;
    }

    public static Sound loadSound(String filePath, String soundName)
    {
        if (sounds.containsKey(soundName))
            return sounds.get(soundName);

        FileHandle handle = Gdx.files.internal(filePath);

        if (handle.exists()){
            Sound snd = Gdx.audio.newSound(handle);
            sounds.put(soundName, snd);

            return snd;
        }

        return null;
    }

    public static Sound getSound(String soundName)
    {
        if (sounds.containsKey(soundName))
            return sounds.get(soundName);

        return null;
    }

    public static Music loadMusic(String filePath, String musicName)
    {
        if (musics.containsKey(musicName))
            return musics.get(musicName);

        FileHandle handle = Gdx.files.internal(filePath);

        if (handle.exists()){
            Music msc = Gdx.audio.newMusic(handle);
            musics.put(musicName, msc);

            return msc;
        }

        return null;
    }

    public static Music getMusic(String musicName)
    {
        if (musics.containsKey(musicName))
            return musics.get(musicName);

        return null;
    }

    public static TextureRegion loadTextureRegion(String filePath, String textureName, int x, int y, int width, int length, boolean flipY)
    {
        if (textureRegions.containsKey(textureName))
            return textureRegions.get(textureName);

        Texture texture;

        if (textures.containsKey(textureName))
            texture = textures.get(textureName);
        else
        {
            FileHandle handle = Gdx.files.internal(filePath);

            if (handle.exists())
                texture = new Texture(handle);
            else
                return null;
        }

        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureRegion item;
        if (x != -1)
            item = new TextureRegion(texture, x, y, width, length);
        else
            item = new TextureRegion(texture);

        item.flip(false, flipY);
        textureRegions.put(textureName, item);

        return item;
    }

    public static TextureRegion loadTextureRegion(String filePath, String textureName, int x, int y, int width, int length) {
        return loadTextureRegion(filePath, textureName, x, y, width, length, true);
    }


    public static TextureRegion loadTextureRegion(String filePath, String textureName)
    {
        return loadTextureRegion(filePath, textureName, -1, -1, -1, -1, true);
    }

    public static TextureRegion getTextureRegion(String textureRegionName)
    {
        if (textureRegions.containsKey(textureRegionName))
            return textureRegions.get(textureRegionName);

        return null;
    }

    public static Drawable loadDrawable(String filePath, String textureName, int x, int y, int width, int length)
    {
        TextureRegion item = loadTextureRegion(filePath, textureName, x, y, width, length, true);

        if (item == null)
            return null;

        return new TextureRegionDrawable(item);
    }

    public static Drawable loadDrawable(String filePath, String textureName)
    {
        return loadDrawable(filePath, textureName, -1, -1, -1, -1);
    }

    public static Drawable getDrawable(String textureRegionName)
    {
        if (textureRegions.containsKey(textureRegionName))
            return new TextureRegionDrawable(textureRegions.get(textureRegionName));

        return null;
    }

    public static Animation loadAnimation(String filePath, String textureName, String animName, int width, int heigth, Animation.PlayMode playMode)
    {
        if (animations.containsKey(animName))
            return animations.get(animName);

        Texture texture = loadTexture(filePath, textureName);
        if (texture == null)
            return null;

        int maxWidth = texture.getWidth();
        int maxHeigth = texture.getHeight();
        int x = 0;
        int y = 0;
        Array<TextureRegion> itemsAnim = new Array<TextureRegion>(100);

        while (y + heigth <= maxHeigth){
            x = 0;
            while (x + width <= maxWidth) {
                texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

                TextureRegion item = new TextureRegion(texture, x, y, width, heigth);
                item.flip(false, true);
                itemsAnim.add(item);

                x += width;
            }
            y += heigth;
        }

        Animation anim = new Animation(0.06f, itemsAnim);
        anim.setPlayMode(playMode);
        animations.put(animName, anim);

        return anim;
    }

    public static Animation getAnimation(String animName)
    {
        if (animations.containsKey(animName))
            return animations.get(animName);

        return null;
    }

    public static BitmapFont loadDefaultFont()
    {
        if (fonts.containsKey("default"))
            return fonts.get("default");

        BitmapFont res = new BitmapFont();
        fonts.put("default", res);

        return res;
    }

    public static BitmapFont loadFont(String filePath, String fontName)
    {
        if (fonts.containsKey(fontName))
            return fonts.get(fontName);

        BitmapFont res = new BitmapFont(Gdx.files.internal(filePath), true);
        fonts.put(fontName, res);

        return res;
    }

    public static BitmapFont getFont(String name)
    {
        if (fonts.containsKey(name))
            return fonts.get(name);

        return null;
    }

    public static BitmapFont loadFontFromGenerator(String ttfPath, String fontName, int size, Color color)
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

        return res;
    }

    public static void disposeAssets()
    {
        for (Texture t : textures.values())
        {
            t.dispose();
        }

        for (BitmapFont f : fonts.values())
        {
            f.dispose();
        }
    }
}
