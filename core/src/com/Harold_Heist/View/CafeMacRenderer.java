package com.Harold_Heist.View;

import com.Harold_Heist.Assets;
import com.Harold_Heist.Model.CafeMac;
import com.Harold_Heist.Model.Food;
import com.Harold_Heist.Model.Protagonist;
import com.Harold_Heist.Model.Antagonist;
import com.Harold_Heist.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


import java.util.ArrayList;

public class CafeMacRenderer {

    public static final int GAMEWIDTH = 704;
    public static final int GAMEHEIGHT = 480;

    private CafeMac cafeMac;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private boolean debug = false;
    private float screenWidth;
    private float screenHeight;
    private ShapeRenderer debugRenderer;
    private int gameScore;
    private String gameScoreName;
    BitmapFont scoreFont;
    private float widthRatio;
    private float heightRatio;


    public void setSize(float w, float h) {
        this.screenWidth = w;
        this.screenHeight = h;
    }

    public int getGameScore() {
        return this.gameScore;
    }

    private TiledMap tiledMap;
    private BatchTiledMapRenderer tiledMapRenderer;

    private Protagonist protag;
    private Antagonist antag;
    private Antagonist evilTwinAntag;
    private Food food;
    private Array<Food> foods;


    private ArrayList<Shape2D> collisionShapes;

    public CafeMacRenderer(CafeMac cafeMac, boolean debug) {
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        evilTwinAntag = cafeMac.getEvilTwin();
        foods = cafeMac.getFoodArray();
        this.cafeMac = cafeMac;
        tiledMap = cafeMac.getTiledMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        collisionShapes = cafeMac.getCollisionShapes();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        widthRatio = screenWidth / GAMEWIDTH;
        heightRatio = screenHeight / GAMEHEIGHT;

        //map is small, but the objects are at the right place

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, screenWidth, screenHeight);

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, 704, 480);

        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        gameScore = 0;
        gameScoreName = "SCORE: " + gameScore;
        scoreFont = new BitmapFont();

        debugRenderer = new ShapeRenderer();

    }

    public void render() {
        this.cam.update();
        tiledMapRenderer.setView(this.cam);
        tiledMapRenderer.render();
        if (debug) drawDebug();
        draw();
        collisionHandler();
    }

    /**
     * Draws objects to the screen
     */
    private void draw() {
        spriteBatch.begin();
        drawProtag();
        drawAntag();
        if (gameScore > 9) {
            drawEvilTwin();
        }
        drawFoods();
        scoreFont.setColor(0, 0, 0, .8f);
        scoreFont.draw(spriteBatch, gameScoreName, 620 / widthRatio, 460 / heightRatio);

        spriteBatch.end();
    }

    /**
     * Draws the protagonist
     */
    private void drawProtag() {
        float xCoordinate = protag.getPosition().x;
        float yCoordinate = protag.getPosition().y;
        float protagSize = Protagonist.getSize();

        if (protag.getState() == Protagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.protagRight, xCoordinate, yCoordinate, protagSize, protagSize);
        } else if (protag.getState() == Protagonist.State.FACELEFT) {
            spriteBatch.draw(Assets.protagLeft, xCoordinate, yCoordinate, protagSize, protagSize);
        } else if (protag.getState() == Protagonist.State.FACEUP) {
            spriteBatch.draw(Assets.protagUp, xCoordinate, yCoordinate, protagSize, protagSize);
        } else if (protag.getState() == Protagonist.State.FACEDOWN) {
            spriteBatch.draw(Assets.protagDown, xCoordinate, yCoordinate, protagSize, protagSize);
        }
    }

    /**
     * Draws the antagonist
     */
    private void drawAntag() {
        float xCoordinate = antag.getPosition().x;
        float yCoordinate = antag.getPosition().y;
        float antagSize = Antagonist.getSize();

        if (antag.getState() == Antagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.antagRight, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACELEFT) {
            spriteBatch.draw(Assets.antagLeft, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACEUP) {
            spriteBatch.draw(Assets.antagUp, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACEDOWN) {
            spriteBatch.draw(Assets.antagDown, xCoordinate, yCoordinate, antagSize, antagSize);
        }
    }

    /**
     * Draws the evil twin
     */
    private void drawEvilTwin() {
        float xCoordinate = evilTwinAntag.getPosition().x;
        float yCoordinate = evilTwinAntag.getPosition().y;
        float antagSize = Antagonist.getSize();

        if (evilTwinAntag.getPosition().y < 480 && evilTwinAntag.getPosition().y > 475) {
            Assets.evilTwinAppearingSound.play();
        }

        if (evilTwinAntag.getState() == Antagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.evilTwinRight, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (evilTwinAntag.getState() == Antagonist.State.FACELEFT) {
            spriteBatch.draw(Assets.evilTwinLeft, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (evilTwinAntag.getState() == Antagonist.State.FACEUP) {
            spriteBatch.draw(Assets.evilTwinUp, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (evilTwinAntag.getState() == Antagonist.State.FACEDOWN) {
            spriteBatch.draw(Assets.evilTwinDown, xCoordinate, yCoordinate, antagSize, antagSize);
        }
    }

    /**
     * Draws the foods
     */
    private void drawFoods() {
        for (Food food : foods) {
            float foodX = food.getPosition().x;
            float foodY = food.getPosition().y;
            float foodSize = food.getSize();
            int foodIndex = food.getFoodIndex();

            Food newFood = checkFoodCollisions(food, foodX, foodY, foodSize);
            float newFoodX = newFood.getPosition().x;
            float newFoodY = newFood.getPosition().y;

            if (foodIndex == 0)
                spriteBatch.draw(Assets.foodApple, newFoodX, newFoodY, foodSize, foodSize);
            else if (foodIndex == 1)
                spriteBatch.draw(Assets.foodBanana, newFoodX, newFoodY, foodSize, foodSize);
            else if (foodIndex == 2)
                spriteBatch.draw(Assets.foodBacon, newFoodX, newFoodY, foodSize, foodSize);
            else if (foodIndex == 3)
                spriteBatch.draw(Assets.foodCake, newFoodX, newFoodY, foodSize, foodSize);

        }
    }

    /**
     * Debug mode
     */
    private void drawDebug() {

        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(new Color(0, 1, 0, 1));

        //Outline the foods
        for (Food food : foods) {
            debugRenderer.rect(food.getPosition().x / widthRatio, food.getPosition().y / heightRatio, food.getSize() / widthRatio, food.getSize() / widthRatio);
        }

        // Outline the shapes
        for (Shape2D shape : collisionShapes) {
            if (shape.getClass() == Rectangle.class) {
                Rectangle rect = (Rectangle) shape;
                debugRenderer.rect(rect.getX(), rect.getY(), rect.width, rect.height);

            } else {
                Ellipse ellip = (Ellipse) shape;
                debugRenderer.rect(ellip.x, ellip.y, ellip.width, ellip.height);
            }
        }

        // Outline protagonist
        Rectangle protagBounds = protag.getBounds();
        debugRenderer.rect(protag.getPosition().x / widthRatio, protag.getPosition().y / heightRatio, protagBounds.width, protagBounds.height);

        // Outline antagonist
        Rectangle antagBounds = antag.getBounds();
        debugRenderer.rect(antag.getPosition().x / widthRatio, antag.getPosition().y / heightRatio, antagBounds.width, antagBounds.height);

        // Outline evil twin
        Rectangle evilTwinAntagBounds = evilTwinAntag.getBounds();
        debugRenderer.rect(evilTwinAntag.getPosition().x / widthRatio, evilTwinAntag.getPosition().y / heightRatio, evilTwinAntagBounds.width, evilTwinAntagBounds.height);
        debugRenderer.end();
    }

    /**
     * Big method that calls all the collision handling methods
     */
    private void collisionHandler() {
        for (Shape2D shape : collisionShapes) {
            obstacleCollisionHandler(protag.getPosition(), shape, true);
            obstacleCollisionHandler(antag.getPosition(), shape, false);
        }
        wallCollisionHandler(protag.getPosition(), true);
        wallCollisionHandler(antag.getPosition(), false);
        objectCollisionHandler(protag.getPosition(), antag.getPosition(), true);
        objectCollisionHandler(protag.getPosition(), evilTwinAntag.getPosition(), true);
        for (Food food : cafeMac.getFoodArray()) {
            objectCollisionHandler(protag.getPosition(), food.getPosition(), false);
        }
    }

    /**
     * Handles collision so that protagonist/antagonist can't walk through tables/food stations
     * @param characterPosition
     * @param shape
     * @param isProtag
     */
    private void obstacleCollisionHandler(Vector2 characterPosition, Shape2D shape, boolean isProtag) {
        float charX = characterPosition.x;
        float charY = characterPosition.y;
        float charSize;
        if (isProtag) {
            charSize = protag.getSize();
        } else {
            charSize = antag.getSize();
        }

        float shapeX;
        float shapeY;
        float shapeWidth;
        float shapeHeight;
        Rectangle rect;
        Ellipse ellip;

        if (shape.getClass() == Rectangle.class) {
            rect = (Rectangle) shape;
            shapeX = rect.getX();
            shapeY = rect.getY();
            shapeWidth = rect.getWidth();
            shapeHeight = rect.getHeight();

        } else {
            ellip = (Ellipse) shape;
            shapeX = ellip.x;
            shapeY = ellip.y;
            shapeWidth = ellip.width;
            shapeHeight = ellip.height;
        }

        // collision left of object
        if (charX + charSize > shapeX && charX + charSize < shapeX + 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            characterPosition.x = shapeX - charSize;
        }

        // collision right of object
        else if (charX < shapeX + shapeWidth && charX > shapeX + shapeWidth - 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            characterPosition.x = shapeX + shapeWidth;
        }

        // collision top of object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY < shapeY + shapeHeight && charY > shapeY + shapeHeight - 10) {
            characterPosition.y = shapeY + shapeHeight;
        }

        // collision below object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY + charSize > shapeY && charY + charSize < shapeY + 10) {
            characterPosition.y = shapeY - charSize;
        }
    }

    /**
     * If protagAndAntag is true, handle collision between protagonist and antagonist. If false, handle collision
     * between protagonist and food.
     */
    private void objectCollisionHandler(Vector2 object1Position, Vector2 object2Position, boolean protagAndAntag) {
        float object1X = object1Position.x;
        float object1Y = object1Position.y;
        float object1Size = protag.getSize();

        float object2X = object2Position.x;
        float object2Y = object2Position.y;
        float object2Size;
        if (protagAndAntag) object2Size = antag.getSize();
        else object2Size = food.getSize();

        // collision left of object
        if (object1X + object1Size > object2X && object1X + object1Size < object2X + 10 && object1Y + object1Size > object2Y && object1Y < object2Y + object2Size) {
            if (protagAndAntag) {
                gameOver();
            } else {
                eatFood(object2Position);
            }
        }

        // collision right of object
        else if (object1X < object2X + object2Size && object1X > object2X + object2Size - 10 && object1Y + object1Size > object2Y && object1Y < object2Y + object2Size) {
            if (protagAndAntag) {
                gameOver();
            } else {
                eatFood(object2Position);
            }
        }

        // collision top of object
        else if (object1X + object1Size > object2X && object1X < object2X + object2Size && object1Y < object2Y + object2Size && object1Y > object2Y + object2Size - 10) {
            if (protagAndAntag) {
                gameOver();
            } else {
                eatFood(object2Position);
            }
        }

        // collision below object
        else if (object1X + object1Size > object2X && object1X < object2X + object2Size && object1Y + object1Size > object2Y && object1Y + object1Size < object2Y + 10) {
            if (protagAndAntag) {
                gameOver();
            } else {
                eatFood(object2Position);
            }
        }
    }

    /**
     * Handles collision so that protagonist/antagonist can't walk walls
     * @param characterPosition
     * @param isProtag
     */
    private void wallCollisionHandler(Vector2 characterPosition, boolean isProtag) {
        float charX = characterPosition.x;
        float charY = characterPosition.y;
        float charSize;
        if (isProtag) {
            charSize = protag.getSize();
        } else {
            charSize = antag.getSize();
        }

        if (charX < 0) characterPosition.x = 0;
        if (charX + charSize > screenWidth) characterPosition.x = screenWidth - charSize;

        if (charY < 0) characterPosition.y = 0;
        if (charY + charSize > screenHeight) characterPosition.y = screenHeight - charSize;
    }

    /**
     * Takes in a food at the inputted location, checks if it is out of bounds or over a table.
     * If yes, return a new food at a valid position. If not, return the original food.
     * @param food
     * @param foodX
     * @param foodY
     * @param foodSize
     * @return
     */
    private Food checkFoodCollisions(Food food, float foodX, float foodY, float foodSize) {

        Food newFood = food;

        // Wall Collisions:

        // bottom wall
        while (foodY < 0) {
            newFood = removeThenAddNewFood(food.getPosition());
            food = newFood;
            foodX = newFood.getPosition().x;
            foodY = newFood.getPosition().y;
        }

        // top wall
        while (foodY + foodSize > screenHeight) {
            newFood = removeThenAddNewFood(food.getPosition());
            food = newFood;
            foodX = newFood.getPosition().x;
            foodY = newFood.getPosition().y;
        }

//        // left wall
        while (foodX < 0) {
            newFood = removeThenAddNewFood(food.getPosition());
            food = newFood;
            foodX = newFood.getPosition().x;
            foodY = newFood.getPosition().y;
        }

//        // right wall
        while (foodX + foodSize > screenWidth) {
            newFood = removeThenAddNewFood(food.getPosition());
            food = newFood;
            foodX = newFood.getPosition().x;
            foodY = newFood.getPosition().y;
        }

        // table collisions
        float shapeX;
        float shapeY;
        float shapeWidth;
        float shapeHeight;
        Rectangle rect;
        Ellipse ellip;

        for (Shape2D shape : collisionShapes) {

            if (shape.getClass() == Rectangle.class) {
                rect = (Rectangle) shape;
                shapeX = rect.getX();
                shapeY = rect.getY();
                shapeWidth = rect.getWidth();
                shapeHeight = rect.getHeight();
            } else {
                ellip = (Ellipse) shape;
                shapeX = ellip.x;
                shapeY = ellip.y;
                shapeWidth = ellip.width;
                shapeHeight = ellip.height;
            }

            // check bottom left of food
            while ((foodX >= shapeX && foodX <= shapeX + shapeWidth) && (foodY >= shapeY && foodY <= shapeY + shapeHeight)) {
                newFood = removeThenAddNewFood(food.getPosition());
                food = newFood;
                foodX = newFood.getPosition().x;
                foodY = newFood.getPosition().y;
            }

            // check bottom right of food
            while ((foodX + foodSize >= shapeX && foodX + foodSize <= shapeX + shapeWidth) && (foodY >= shapeY && foodY <= shapeY + shapeHeight)) {
                newFood = removeThenAddNewFood(food.getPosition());
                food = newFood;
                foodX = newFood.getPosition().x;
                foodY = newFood.getPosition().y;
            }

            // check upper left of food
            while ((foodX >= shapeX && foodX <= shapeX + shapeWidth) && (foodY + foodSize >= shapeY && foodY + foodSize <= shapeY + shapeHeight)) {
                newFood = removeThenAddNewFood(food.getPosition());
                food = newFood;
                foodX = newFood.getPosition().x;
                foodY = newFood.getPosition().y;
            }

            // check upper right of food
            while ((foodX + foodSize >= shapeX && foodX + foodSize <= shapeX + shapeWidth) && (foodY + foodSize >= shapeY && foodY + foodSize <= shapeY + shapeHeight)) {
                newFood = removeThenAddNewFood(food.getPosition());
                food = newFood;
                foodX = newFood.getPosition().x;
                foodY = newFood.getPosition().y;
            }
        }

        return food;
    }

    /**
     * Switches to the Game Over Screen
     */
    private void gameOver() {
        cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
    }

    /**
     * Removes the food, increases the score, and adds another food in a different position
     * @param foodPosition
     */
    private void eatFood(Vector2 foodPosition) {
        removeThenAddNewFood(foodPosition);
        gameScore++;
        gameScoreName = "SCORE: " + gameScore;
        if (Settings.soundEnabled) Assets.eatingSound.play();
    }

    /**
     * Removes the food at the specified the position, and returns a new food
     * @param foodPosition
     * @return
     */
    private Food removeThenAddNewFood(Vector2 foodPosition) {
        cafeMac.removeFood(foodPosition);
        return cafeMac.addFood();
    }
}
