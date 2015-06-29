package com.Harold_Heist.Model;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class CafeMac {

    Protagonist protag;
	Antagonist antag;
    Antagonist evilTwin;
    Array<Food> foodArray = new Array<Food>();


    TiledMap tiledMap = new TmxMapLoader().load("graphics/cafeMacMap.tmx");

    MapLayer objectLayer;
    MapObjects collisionObjects;
    ArrayList<Shape2D> collisionShapes;

    public enum State {STATE_RUNNING, STATE_GAMEOVER; }

    State state = State.STATE_RUNNING;

	// Getters -----------

	public Protagonist getProtagonist() {return protag; }

	public Antagonist getAntagonist(){return antag; }

    public Antagonist getEvilTwin(){return evilTwin; }

    public Array<Food> getFoodArray(){return foodArray; }

    public State getState(){return state; }

    public TiledMap getTiledMap(){return tiledMap; }

    public ArrayList<Shape2D> getCollisionShapes() {return collisionShapes; }

    // Setters -----------

    public void setState(State state) {this.state = state;}

    // --------------------

	public CafeMac(){
        protag = new Protagonist(Protagonist.getStartPosition(), Protagonist.SIZE);
        antag = new Antagonist(Antagonist.getStartPosition(), Antagonist.SIZE);
        evilTwin = new Antagonist(Antagonist.getEvilTwinStartPosition(), Antagonist.SIZE);
        addCollisionShapes();

        for (int foodIndex = 0; foodIndex < Food.NUM_TYPES; foodIndex++) {
            for (int i = 0; i < Food.NUM_FOOD_PER_TYPE; i++) {
                Vector2 position = getFreePosition();
                Food food = new Food(position, foodIndex);
                foodArray.add(food);
            }
        }
    }

    /**
     * Creates a food at a valid position, adds it to the food array, and returns the food
     * @return
     */
    public Food addFood(){
        Random rand = new Random();
        Vector2 position = getFreePosition();
        Food food = new Food(position, rand.nextInt(4));
        foodArray.add(food);
        return food;
    }

    /**
     * Removes the food at the inputted position
     * @param foodPosition
     */
    public void removeFood(Vector2 foodPosition) {
        for (int i = 0; i < foodArray.size; i++) {
            if (foodArray.get(i).getPosition().equals(foodPosition)) {
                foodArray.removeIndex(i);
            }
        }
    }

    /**
     * Returns a valid position for a food
     * (not over other foods or over the starting positions of protagonist and antagonist)
     * @return
     */

    private Vector2 getFreePosition(){
        Random rand = new Random();
        Vector2 position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
        float foodX = position.x;
        float foodY = position.y;
        float foodSize = Food.SIZE;

        /* Checks to see if the current position overlaps with any food. If yes, get a new random position. */

        for (int i = 0; i < foodArray.size; i++) {
            Food otherFood = foodArray.get(i);
            float otherFoodX = otherFood.getPosition().x;
            float otherFoodY = otherFood.getPosition().y;
            float otherFoodSize = otherFood.getSize();

            // check bottom left of food
            while ((foodX >= otherFoodX && foodX <= otherFoodX + otherFoodSize) && (foodY >= otherFoodY && foodY <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }

            // check bottom right of food
            while ((foodX + foodSize >= otherFoodX && foodX + foodSize <= otherFoodX + otherFoodSize) && (foodY >= otherFoodY && foodY <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }

            // check upper left of food

            while ((foodX >= otherFoodX && foodX <= otherFoodX + otherFoodSize) && (foodY + foodSize >= otherFoodY && foodY + foodSize <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }

            // check upper right of food
            while ((foodX + foodSize >= otherFoodX && foodX + foodSize <= otherFoodX + otherFoodSize) && (foodY + foodSize >= otherFoodY && foodY + foodSize <= otherFoodY + otherFoodSize)) {
                position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
                foodX = position.x;
                foodY = position.y;
            }
        }

        /* Checks to see if the current position overlaps with starting position of protag and antag.
        If yes, get a new random position. */

        float protagStartX = protag.getStartPosition().x;
        float protagStartY = protag.getStartPosition().y;
        float protagSize = protag.getSize();
        float antagStartX = antag.getStartPosition().x;
        float antagStartY = antag.getStartPosition().y;
        float antagSize = antag.getSize();

        // Protag collisions

        // check bottom left of food
        while ((foodX >= protagStartX && foodX <= protagStartX + protagSize) && (foodY >= protagStartY && foodY <= protagStartY + protagSize)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // check bottom right of food
        while ((foodX + foodSize >= protagStartX && foodX + foodSize <= protagStartX + protagSize) && (foodY >= protagStartY && foodY <= protagStartY + protagSize)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // check upper left of food
        while ((foodX >= protagStartX && foodX <= protagStartX + protagSize) && (foodY + foodSize >= protagStartY && foodY + foodSize <= protagStartY + protagSize)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // check upper right of food

        while ((foodX + foodSize >= protagStartX && foodX + foodSize <= protagStartX + protagSize) && (foodY + foodSize >= protagStartY && foodY + foodSize <= protagStartY + protagSize)) {
            // can do a while loop to ensure the final fruit position is not on a table
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // Antag collisions

        // check bottom left of food
        while ((foodX >= antagStartX && foodX <= antagStartX + antagSize) && (foodY >= antagStartY && foodY <= antagStartY + antagSize)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // check bottom right of food
        while ((foodX + foodSize >= antagStartX && foodX + foodSize <= antagStartX + antagSize) && (foodY >= antagStartY && foodY <= antagStartY + antagSize)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // check upper left of food
        while ((foodX >= antagStartX && foodX <= antagStartX + antagSize) && (foodY + foodSize >= antagStartY && foodY + foodSize <= antagStartY + antagSize)) {
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        // check upper right of food
        while ((foodX + foodSize >= antagStartX && foodX + foodSize <= antagStartX + antagSize) && (foodY + foodSize >= antagStartY && foodY + foodSize <= antagStartY + antagSize)) {
            // can do a while loop to ensure the final fruit position is not on a table
            position = new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight()));
            foodX = position.x;
            foodY = position.y;
        }

        return position;
    }

    /**
     * Gets shapes from the tiled map and adds it to the collision shapes array
     */
    private void addCollisionShapes(){
        objectLayer = tiledMap.getLayers().get("Collision");
        collisionObjects = objectLayer.getObjects();
        collisionShapes = new ArrayList<Shape2D>();

        for (MapObject obj : collisionObjects){
            if (obj.getClass() == RectangleMapObject.class){
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                Rectangle rect = rectObj.getRectangle();
                collisionShapes.add(rect);
            }
            else{
                EllipseMapObject ellipseObj = (EllipseMapObject) obj;
                Ellipse ellip = ellipseObj.getEllipse();
                collisionShapes.add(ellip);
            }
        }
    }
}
