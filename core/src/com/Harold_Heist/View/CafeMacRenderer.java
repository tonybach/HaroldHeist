package com.Harold_Heist.View;

import com.Harold_Heist.Assets;
import com.Harold_Heist.Model.CafeMac;
import com.Harold_Heist.Model.Food;
import com.Harold_Heist.Model.Protagonist;
import com.Harold_Heist.Model.Antagonist;
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

    private static final float CAMERA_WIDTH = 25f;
    public static final int GAMEWIDTH = 800;
    public static final int GAMEHEIGHT = 480;

    private CafeMac cafeMac;
    private OrthographicCamera cam;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private boolean debug = false;
    private float screenWidth;
    private float screenHeight;
    private ShapeRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private int gameScore;
    private String gameScoreName;
    BitmapFont scoreFont;
    private float widthRatio;
    private float heightRatio;


    public void setSize(float w, float h) {
        this.screenWidth = w;
        this.screenHeight = h;
    }

    public int getGameScore(){return this.gameScore;}


    private TiledMap tiledMap;
    private BatchTiledMapRenderer tiledMapRenderer;

    private Protagonist protag;
    private Antagonist antag;
    private Food food;
    private Array<Food> foods;

    private ArrayList<Shape2D> collisionShapes;

    public CafeMacRenderer(CafeMac cafeMac, boolean debug) {
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        foods = cafeMac.getFoodArray();
        this.cafeMac = cafeMac;
        tiledMap = cafeMac.getTiledMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        collisionShapes = cafeMac.getCollisonShapes();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        widthRatio = screenWidth / GAMEWIDTH;
        heightRatio = screenHeight / GAMEHEIGHT;

        //map is small, but the objects are at the right place

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, screenWidth, screenHeight);

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, 800, 480);

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

    private void collisionHandler() {
        for (Shape2D shape : collisionShapes) {
            obstacleCollisionHandler(protag.getPosition(), shape, true);
            obstacleCollisionHandler(antag.getPosition(), shape, false);
        }
        wallCollisionHandler(protag.getPosition(), true);
        wallCollisionHandler(antag.getPosition(), false);
        objectCollisionHandler(protag.getPosition(), antag.getPosition(), true);
        for (Food food : cafeMac.getFoodArray()) {
            objectCollisionHandler(protag.getPosition(), food.getPosition(), false);
        }
    }

    private void draw() {
        spriteBatch.begin();
        drawProtag();
        drawAntag();
        drawFoods();
        scoreFont.setColor(0, 0, 0, .8f);
        scoreFont.draw(spriteBatch, gameScoreName, 710/widthRatio, 460/heightRatio);
//        scoreFont.draw(spriteBatch, gameScoreName, 710, 460);

        spriteBatch.end();
    }

    private void obstacleCollisionHandler(Vector2 character, Shape2D shape, boolean isProtag) {
        float charX = character.x;
        float charY = character.y;
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
            character.x = shapeX - charSize;
        }

        // collision right of object
        else if (charX < shapeX + shapeWidth && charX > shapeX + shapeWidth - 10 && charY + charSize > shapeY && charY < shapeY + shapeHeight) {
            character.x = shapeX + shapeWidth;
        }

        // collision top of object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY < shapeY + shapeHeight && charY > shapeY + shapeHeight - 10) {
            character.y = shapeY + shapeHeight;
        }

        // collision below object
        else if (charX + charSize > shapeX && charX < shapeX + shapeWidth && charY + charSize > shapeY && charY + charSize < shapeY + 10) {
            character.y = shapeY - charSize;
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
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFood(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
                cafeMac.addFood();
//                Assets.eatingSound.play();
            }
        }

        // collision right of object
        else if (object1X < object2X + object2Size && object1X > object2X + object2Size - 10 && object1Y + object1Size > object2Y && object1Y < object2Y + object2Size) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFood(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
                cafeMac.addFood();
//                Assets.eatingSound.play();
            }
        }

        // collision top of object
        else if (object1X + object1Size > object2X && object1X < object2X + object2Size && object1Y < object2Y + object2Size && object1Y > object2Y + object2Size - 10) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFood(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
                cafeMac.addFood();
//                Assets.eatingSound.play();
            }
        }

        // collision below object
        else if (object1X + object1Size > object2X && object1X < object2X + object2Size && object1Y + object1Size > object2Y && object1Y + object1Size < object2Y + 10) {
            if (protagAndAntag) cafeMac.setState(CafeMac.State.STATE_GAMEOVER);
            else {
                cafeMac.removeFood(object2Position);
                gameScore++;
                gameScoreName = "SCORE: " + gameScore;
                cafeMac.addFood();
//                Assets.eatingSound.play();
            }
        }
    }


    private void wallCollisionHandler(Vector2 character, boolean isProtag) {
        float charX = character.x;
        float charY = character.y;
        float charSize;
        if (isProtag) {
            charSize = protag.getSize();
        } else {
            charSize = antag.getSize();
        }

        if (charX < 0) character.x = 0;
        if (charX + charSize > screenWidth) character.x = screenWidth - charSize;

        if (charY < 0) character.y = 0;
        if (charY + charSize > screenHeight) character.y = screenHeight - charSize;
    }

    private void drawProtag() {
        float xCoordinate = protag.getPosition().x;
        float yCoordinate = protag.getPosition().y;
        float protagSize = Protagonist.getSize();
        float protagWidth = Protagonist.getProtagWidth();

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

    private void drawAntag() {
        float xCoordinate = antag.getPosition().x;
        float yCoordinate = antag.getPosition().y;
        float antagSize = Antagonist.getSize();

        if (antag.getState() == Antagonist.State.FACERIGHT) {
            spriteBatch.draw(Assets.antagRight, xCoordinate, yCoordinate, antagSize, antagSize);
//            protag.getPosition().x += Protagonist.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (antag.getState() == Antagonist.State.FACELEFT) {
            spriteBatch.draw(Assets.antagLeft, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACEUP) {
            spriteBatch.draw(Assets.antagUp, xCoordinate, yCoordinate, antagSize, antagSize);
        } else if (antag.getState() == Antagonist.State.FACEDOWN) {
            spriteBatch.draw(Assets.antagDown, xCoordinate, yCoordinate, antagSize, antagSize);
        }
    }

    private void drawFoods() {
        for (Food food : foods) {
            float foodX = food.getPosition().x/widthRatio;
            float foodY = food.getPosition().y/heightRatio;
            float foodSize = food.getSize();
            int foodIndex = food.getFoodIndex();

//            checkFoodTableCollisions(food, foodX, foodY, foodSize);

            if (foodIndex == 0) spriteBatch.draw(Assets.foodApple, foodX, foodY, food.getSize() / widthRatio, food.getSize() / widthRatio);
            else if (foodIndex == 1) spriteBatch.draw(Assets.foodBanana, foodX, foodY, food.getSize() / widthRatio, food.getSize() / widthRatio);
            else if (foodIndex == 2) spriteBatch.draw(Assets.foodBacon, foodX, foodY, food.getSize() / widthRatio, food.getSize() / widthRatio);
            else if (foodIndex == 3) spriteBatch.draw(Assets.foodCake, foodX, foodY, food.getSize() / widthRatio, food.getSize() / widthRatio);

        }
    }

    private void checkFoodTableCollisions(Food food, float foodX, float foodY, float foodSize) {
        for (Shape2D shape : collisionShapes) {
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

            if ((foodX >= shapeX && foodX <= shapeX + shapeWidth) && (foodY >= shapeY && foodY <= shapeY + shapeHeight)) {
                cafeMac.removeFood(food.getPosition());
                cafeMac.addFood();
            }

            if ((foodX + foodSize >= shapeX && foodX + foodSize <= shapeX + shapeWidth) && (foodY >= shapeY && foodY <= shapeY + shapeHeight)) {
                cafeMac.removeFood(food.getPosition());
                cafeMac.addFood();
            }

            if ((foodX >= shapeX && foodX <= shapeX + shapeWidth) && (foodY + foodSize >= shapeY && foodY + foodSize <= shapeY + shapeHeight)) {
                cafeMac.removeFood(food.getPosition());
                cafeMac.addFood();
            }

            if ((foodX + foodSize >= shapeX && foodX + foodSize <= shapeX + shapeWidth) && (foodY + foodSize >= shapeY && foodY + foodSize <= shapeY + shapeHeight)) {
                // can do a while loop to ensure the final fruit position is not on a table
                cafeMac.removeFood(food.getPosition());
                cafeMac.addFood();

            }
        }
    }

    private void drawDebug() {
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(new Color(0, 1, 0, 1));

        //Outline the foods
        for (Food food : foods) {
            debugRenderer.rect(food.getPosition().x/widthRatio, food.getPosition().y/heightRatio, food.getSize()/widthRatio, food.getSize()/widthRatio);
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
        debugRenderer.rect(protag.getPosition().x/widthRatio, protag.getPosition().y/heightRatio, protagBounds.width, protagBounds.height);

        // Outline antagonist
        Rectangle antagBounds = antag.getBounds();
        debugRenderer.rect(antag.getPosition().x/widthRatio, antag.getPosition().y/heightRatio, antagBounds.width, antagBounds.height);

        debugRenderer.end();
    }

}