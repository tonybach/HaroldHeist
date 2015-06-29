package com.Harold_Heist.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Food {

    static final float SIZE = 32;
    static final float NUM_TYPES = 4;
    static final float NUM_FOOD_PER_TYPE = 3;

    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();
    int foodIndex;

    public Food(Vector2 position, int foodIndex) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
        this.foodIndex = foodIndex;
    }

    public Vector2 getPosition() {return position; }

    public Rectangle getBounds() {return bounds; }

    public int getFoodIndex() {return foodIndex;}

    public static float getSize() {return SIZE; }
}
