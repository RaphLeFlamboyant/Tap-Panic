package com.flamboyant.common.multiplatform.social;

/**
 * Created by Reborn Portable on 16/03/2016.
 */
public interface ISocialServices {
    void shareToSocial();
    void shareScore(int hits, int points);
    void shareScore(int hits, int points, float boss);
    void shareScore(float boss);
    void shareGameOver(boolean bossMode);
}
