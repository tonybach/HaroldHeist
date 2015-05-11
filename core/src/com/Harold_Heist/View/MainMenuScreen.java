package com.Harold_Heist.View;

import com.Harold_Heist.Assets;
import com.Harold_Heist.HaroldHeist;
import com.Harold_Heist.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    final HaroldHeist game;
    OrthographicCamera camera;
    Rectangle soundBounds;
    Vector3 touchPoint;
    private Texture backgroundTexture;

    public MainMenuScreen(HaroldHeist game) {
        this.game = game;

        backgroundTexture = new Texture(Gdx.files.internal("graphics/menuScreen.png"));
        soundBounds = new Rectangle(0, 0, 20, 20);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 560, 320);

        touchPoint = new Vector3();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, 560, 320);
        //game.font.draw(game.batch, "Welcome to Harold Heist!!", 190, 200);
        //game.font.draw(game.batch, "Click anywhere to begin", 190, 150);
        game.batch.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 20, 20);
        //game.batch.draw(startButton);
        //game.batch.draw(instructionsButton);
        //game.batch.draw(highScoreButton);
        //game.batch.draw(exitButton);
        game.batch.end();

        update();
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                Settings.soundEnabled = !Settings.soundEnabled;
                if (Settings.soundEnabled) {
                    Assets.catchyMusic.play();
                } else {
                    Assets.catchyMusic.pause();
                }
            }
            //if(

            else {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }

        if (Gdx.input.isKeyJustPressed(66)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
