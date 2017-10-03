package com.flamboyant.tappanic.render;

import com.badlogic.gdx.audio.Music;
import com.flamboyant.tappanic.tools.HighLevelAssetManager;
import com.flamboyant.tappanic.tools.assetpack.AssetPack;

/**
 * Created by Reborn Portable on 08/03/2016.
 */
public class MusicLoop {
    private HighLevelAssetManager assetsManager;

    private String musicIntro;
    private String musicLoop;

    private boolean loops;

    public MusicLoop(String musicName, HighLevelAssetManager assetsManager)
    {
        this.assetsManager = assetsManager;

        musicIntro = musicName + " - intro";
        musicLoop = musicName + " - loop";
    }


    public void completeAssetPacksForLoading(String fileFolder, AssetPack musicPack)
    {
        musicPack.add(musicIntro, fileFolder + musicIntro + ".mp3");
        musicPack.add(musicLoop, fileFolder + musicLoop + ".mp3");
    }

    /*public void update()
    {
        Music toto = assetsManager.getMusic(musicLoop);

        if (toto.getPosition() > 25)
            toto.setPosition(20);
    }*/

    public void afterloading(){
        Music intro = assetsManager.getMusic(musicIntro);
        intro.setOnCompletionListener(new IntroCompletionListener());

        assetsManager.getMusic(musicLoop).setLooping(true);
    }

    public void setVolume(float volume)
    {
        assetsManager.getMusic(musicIntro).setVolume(volume);
        assetsManager.getMusic(musicLoop).setVolume(volume);
    }

    public void stop()
    {
        assetsManager.getMusic(musicIntro).stop();
        assetsManager.getMusic(musicLoop).stop();
        loops = false;
    }

    public void play()
    {
        if (loops)
            assetsManager.getMusic(musicLoop).play();
        else
            assetsManager.getMusic(musicIntro).play();
    }

    public void pause()
    {
        if (loops)
            assetsManager.getMusic(musicLoop).pause();
        else
            assetsManager.getMusic(musicIntro).pause();
    }

    private class IntroCompletionListener implements Music.OnCompletionListener{
        @Override
        public void onCompletion(Music music) {
            assetsManager.getMusic(musicLoop).play();

            loops = true;
        }
    }
}
