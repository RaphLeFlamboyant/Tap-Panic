package com.flamboyant.common.helpers;

import com.badlogic.gdx.math.Vector2;
import com.flamboyant.common.MovableObject;
import com.flamboyant.tappanic.tools.GameContants;

/**
 * Created by Reborn Portable on 28/01/2016.
 */
public class MovableObjectHelper {
    static public void changeTargetAndUpdateSpeed(MovableObject c, float x, float y)
    {
        c.setTargetPos(x, y);
        c.setSpeed((x - c.getPosition().x) / GameContants.TRANSITION_TIME_IN_SECOND, (y - c.getPosition().y) / GameContants.TRANSITION_TIME_IN_SECOND);
    }

    static public void changeTargetAndUpdateSpeed(MovableObject c, float x, float y, int transitionTimeInMS)
    {
        c.setTargetPos(x, y);
        c.setSpeed((x - c.getPosition().x) / transitionTimeInMS, (y - c.getPosition().y) / transitionTimeInMS);
    }

    static public boolean reachesTarget(MovableObject c, float deltaX, float deltaY)
    {
        /*if ((c.getPosition().x <= c.getTargetPositionition().x && c.getPosition().x + deltaX >= c.getTargetPosition().x
                || c.getPosition().x >= c.getTargetPosition().x && c.getPosition().x + deltaX <= c.getTargetPosition().x)
                && !((c.getPosition().y <= c.getTargetPosition().y && c.getPosition().y + deltaY >= c.getTargetPosition().y
                || c.getPosition().y >= c.getTargetPosition().y && c.getPosition().y + deltaY <= c.getTargetPosition().y))

        ||

                !((c.getPosition().x <= c.getTargetPosition().x && c.getPosition().x + deltaX >= c.getTargetPosition().x
                        || c.getPosition().x >= c.getTargetPosition().x && c.getPosition().x + deltaX <= c.getTargetPosition().x))
                        && (c.getPosition().y <= c.getTargetPosition().y && c.getPosition().y + deltaY >= c.getTargetPosition().y
                        || c.getPosition().y >= c.getTargetPosition().y && c.getPosition().y + deltaY <= c.getTargetPosition().y)
        )
            assert false;*/

        return (c.getPosition().equals(c.getTargetPosition()) ||
                (c.getPosition().x < c.getTargetPosition().x && c.getPosition().x + deltaX >= c.getTargetPosition().x
                || c.getPosition().x > c.getTargetPosition().x && c.getPosition().x + deltaX <= c.getTargetPosition().x)
                || (c.getPosition().y < c.getTargetPosition().y && c.getPosition().y + deltaY >= c.getTargetPosition().y
                || c.getPosition().y > c.getTargetPosition().y && c.getPosition().y + deltaY <= c.getTargetPosition().y));
    }

    static public void changeTargetSizeAndUpdateSpeed(MovableObject c, int sx, int sy)
    {
        c.setTargetSize(sx, sy);
        c.setSpeed((sx - c.getSize().x) / GameContants.TRANSITION_TIME_IN_SECOND, (sy - c.getSize().y) / GameContants.TRANSITION_TIME_IN_SECOND);
    }

    static public boolean reachesTargetSize(MovableObject c, float deltasX, float deltasY)
    {
        return ((c.getSize().x <= c.getTargetSize().x && c.getSize().x + deltasX >= c.getTargetSize().x
                || c.getSize().x >= c.getTargetSize().x && c.getSize().x + deltasX <= c.getTargetSize().x)
                && (c.getSize().y <= c.getTargetSize().y && c.getSize().y + deltasY >= c.getTargetSize().y
                || c.getSize().y >= c.getTargetSize().y && c.getSize().y + deltasY <= c.getTargetSize().y));
    }

}
