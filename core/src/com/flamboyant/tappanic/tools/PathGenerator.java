package com.flamboyant.tappanic.tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.flamboyant.tappanic.world.SmartSymbols.PathPoint;

import java.util.Random;

/**
 * Created by Reborn Portable on 15/02/2016.
 */
public class PathGenerator {
    public static void generateLinearPath(Array<PathPoint> path, float startX, float startY, float destX, float destY, float size, float reachSec, Random rnd)
    {
        float speed = (float) Math.sqrt((destX - startX) / reachSec * (destX - startX) / reachSec + (destY - startY) / reachSec * (destY - startY) / reachSec);
        PathPoint pt = new PathPoint(destX, destY, size, size, speed, 0, 0, 0, GameContants.SCREEN_HEIGTH / 100f);
        path.add(pt);
    }

    public static void generateSinusoidalPath(Array<PathPoint> path, float size, Random rnd)
    {
        //PathPoint(float x, float y, float width, float heigth, float speed, float sizeSpeed, float rotation, float rotationSpeed, float validationDistance)
        PathPoint pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 7 / 2, GameContants.SCREEN_HEIGTH * 2f / 8f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
            0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 6 / 2, GameContants.SCREEN_HEIGTH * 2f / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
            0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 5 / 2, GameContants.SCREEN_HEIGTH * 5f / 8f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
            0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 4 / 2, GameContants.SCREEN_HEIGTH * 3f / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 5 / 2, GameContants.SCREEN_HEIGTH * 7f / 8f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 4 / 2, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 10f);
        path.add(pt);
    }

    public static void generateFourFlowersPath(Array<PathPoint> path1, Array<PathPoint> path2, Array<PathPoint> path3, Array<PathPoint> path4, float size, Random rnd) {
        PathPoint pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 7 / 2, GameContants.SCREEN_HEIGTH / 10f - size / 2f, size, size, GameContants.SCREEN_HEIGTH * 8f / 9f,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 7 / 2, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH / 2f,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);

        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3 / 2, GameContants.SCREEN_HEIGTH / 10f - size / 2f, size, size, GameContants.SCREEN_HEIGTH * 8f / 9f,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3 / 2, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH / 2f,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);

        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size / 2, GameContants.SCREEN_HEIGTH / 10f - size / 2f, size, size, GameContants.SCREEN_HEIGTH * 8f / 9f,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size / 2, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH / 2f,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 20f);
        path3.add(pt);

        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path4.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 5 / 2, GameContants.SCREEN_HEIGTH / 10f - size / 2f, size, size, GameContants.SCREEN_HEIGTH * 8f / 9f,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path4.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 5 / 2, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH / 2f,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 20f);
        path4.add(pt);
    }

    public static void generateThreeLinearMokingPath(Array<PathPoint> path1, Array<PathPoint> path2, Array<PathPoint> path3, float size, Random rnd) {
        float speed = GameContants.SCREEN_HEIGTH / 3.5f;
        float speedTwoX = (float) Math.sqrt((0.7f * size / 2f) * (0.7f * size / 2f) + (size * 2.1f) * (size * 2.1f)) * speed / (0.7f * size / 2f);
        float speedOneX = (float) Math.sqrt((0.7f * size / 2f) * (0.7f * size / 2f) + (size * 1.05f) * (size * 1.05f)) * speed / (0.7f * size / 2f);

        PathPoint pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  3 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  3.7f * size / 2f, size, size, speedTwoX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  6 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  6.7f * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  9 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  9.7f * size / 2f, size, size, speedOneX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  12 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  12.7f * size / 2f, size, size, speedOneX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path1.add(pt);

        float timeToStart = (GameContants.SCREEN_HEIGTH / 4f + size / 2f) / GameContants.SCREEN_HEIGTH + (size * 2.1f / 2) / speed;
        float realSpeed = (GameContants.SCREEN_HEIGTH / 4f + size / 2f) / timeToStart;
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, realSpeed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  3 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  3.7f * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  6 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  6.7f * size / 2f, size, size, speedOneX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  9 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  9.7f * size / 2f, size, size, speedTwoX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  12 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  12.7f * size / 2f, size, size, speedOneX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path2.add(pt);

        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 20f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  3 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  3.7f * size / 2f, size, size, speedTwoX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  6 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  6.7f * size / 2f, size, size, speedOneX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f +  9 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  9.7f * size / 2f, size, size, speedOneX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size * 3.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  12 * size / 2f, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH / 4f +  12.7f * size / 2f, size, size, speedTwoX,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 + size * 1.1f / 2, GameContants.SCREEN_HEIGTH, size, size, speed,
                0, 0, 0, GameContants.SCREEN_HEIGTH / 30f);
        path3.add(pt);

    }

    public static void generateTwoStarsPath(Array<PathPoint> path1, Array<PathPoint> path2, float size, Random rnd) {
        PathPoint pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);
        pt = new PathPoint(0 - size, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH * 1f / 10f,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH - size, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH / 2f,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 20f);
        path1.add(pt);

        pt = new PathPoint(GameContants.SCREEN_WIDTH / 2 - size / 2, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);
        pt = new PathPoint(GameContants.SCREEN_WIDTH, GameContants.SCREEN_HEIGTH / 4f - size / 2f, size, size, GameContants.SCREEN_HEIGTH * 1f / 10f,
                0, 720, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);
        pt = new PathPoint(0, GameContants.SCREEN_HEIGTH - size / 2f, size, size, GameContants.SCREEN_HEIGTH / 2f,
                0, 180, 0, GameContants.SCREEN_HEIGTH / 20f);
        path2.add(pt);
    }
}
