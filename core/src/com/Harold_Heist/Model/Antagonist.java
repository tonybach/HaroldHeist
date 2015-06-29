package com.Harold_Heist.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Antagonist extends GameCharacter{

	static final float SIZE = 32;
    static final float SPEED = SIZE * 4;
    static final float START_X = 1;
    static final float START_Y = 1;
    static final float EVIL_TWIN_START_X = 330;
    static final float EVIL_TWIN_START_Y = 500;


    public Antagonist(Vector2 position, float size) {super(position, size);}

    public static Vector2 getStartPosition(){return new Vector2(START_X, START_Y); }

    public static Vector2 getEvilTwinStartPosition(){return new Vector2(EVIL_TWIN_START_X, EVIL_TWIN_START_Y); }

    public static float getSpeed() {return SPEED;}

    public void go(Direction direction) {
        this.getPosition().x += direction.dx() * Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        this.getPosition().y += direction.dy() * Antagonist.getSpeed() * Gdx.graphics.getDeltaTime();
    }
		
}
