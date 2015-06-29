package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tonybach on 6/5/15.
 */
public class GameCharacter {

    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.FACERIGHT;

    static float size;
    static float speed;

    public enum State {FACELEFT, FACERIGHT, FACEUP, FACEDOWN; }

    public enum Direction {
        N (0, 1),
        NE (1, 1),
        E (1, 0),
        SE (1, -1),
        S (0, -1),
        SW (-1, -1),
        W(-1, 0),
        NW(-1, 1);

        private final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int dx() {
            return dx;
        }

        public int dy() {
            return dy;
        }

    }

    public GameCharacter(Vector2 position, float size) {
        this.position = position;
        this.size = size;
        this.bounds.height = size;
        this.bounds.width = size;
    }

    public Vector2 getPosition(){return position; }

    public Rectangle getBounds(){return bounds; }

//    public static float getSpeed(){ return speed; }

    public State getState(){return state; }

    public void setState(State state){this.state = state; }

    public static float getSize(){return size; }
}
