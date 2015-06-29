package com.Harold_Heist.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Protagonist extends GameCharacter{

	static final float SIZE = 32;
    static final float SPEED = SIZE * 5.5f;	// unit per second
    static final float START_X = 50;
    static final float START_Y = 50;

	public Protagonist(Vector2 position, float size) {
		super(position, size);
	}

    public static Vector2 getStartPosition(){return new Vector2(START_X, START_Y); }

    public static float getSpeed() {return SPEED;}

    public void keyboardControls(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.setState(GameCharacter.State.FACELEFT);
            this.getPosition().x -= Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.setState(GameCharacter.State.FACERIGHT);
            this.getPosition().x += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.setState(GameCharacter.State.FACEDOWN);
            this.getPosition().y -= Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.setState(GameCharacter.State.FACEUP);
            this.getPosition().y += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        }
    }
}
