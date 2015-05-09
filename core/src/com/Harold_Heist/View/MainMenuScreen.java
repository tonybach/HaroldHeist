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
    Rectangle startGame;
    Rectangle highScores;
    Rectangle instructions;
    Vector3 touchPoint;
    private Texture backgroundTexture;

    public MainMenuScreen(HaroldHeist game) {
        this.game = game;

        backgroundTexture = new Texture(Gdx.files.internal("graphics/menuScreenForButtons.png"));
        soundBounds = new Rectangle(0, 0, 20, 20);
        startGame = new Rectangle(200, 145, 140, 70);
        instructions = new Rectangle(200, 75, 140, 70);
        highScores = new Rectangle(200, 5, 140, 70);
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
        game.batch.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 20, 20);
        game.batch.draw(Assets.startButton, 200, 145, 140, 70);
        game.batch.draw(Assets.instructionsButton, 200, 75, 140, 70);
        game.batch.draw(Assets.highScoreButton, 200, 5, 140, 70);
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

            if (startGame.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new GameScreen(game));
                //dispose();
            }
            if (instructions.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new InstructionsScreen(game));
                //dispose();
            }
            if (highScores.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new HighScoreScreen(game));
                //dispose();
            }
//            else {
//                game.setScreen(new GameScreen(game));
//                dispose();
//            }

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
