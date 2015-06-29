package com.Harold_Heist.View;

import com.Harold_Heist.*;
import com.Harold_Heist.Model.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;


public class GameScreen implements Screen{
	
	private final HaroldHeist game;
	private CafeMac cafeMac;
	private CafeMacRenderer renderer;
	private OrthographicCamera cam;
	private Protagonist protag;
	private Antagonist antag;
    private Antagonist evilTwin;
    private Viewport viewport;


	public GameScreen(HaroldHeist game) {
        this.game = game;
        cafeMac = new CafeMac();
        protag = cafeMac.getProtagonist();
        antag = cafeMac.getAntagonist();
        evilTwin = cafeMac.getEvilTwin();
        renderer = new CafeMacRenderer(cafeMac, false);
        cam = new OrthographicCamera();
        viewport = new ScreenViewport(cam);
    }


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		protag.keyboardControls();
        update();
		renderer.render();
	}

    private void update() {
        if (cafeMac.getState() == CafeMac.State.STATE_GAMEOVER) {
            if (Settings.soundEnabled) {
                Assets.bustedSound.play();
            }
            game.setScreen(new GameOverScreen(game, renderer));
        }
        if(renderer.getGameScore() > 0) {
            moveAntag(antag);
        }
        if(renderer.getGameScore() > 9){
            moveAntag(evilTwin);
        }
    }


    private void moveAntag(Antagonist evilCharacter) {

        double deltaY = protag.getPosition().y - evilCharacter.getPosition().y;
        double deltaX = protag.getPosition().x - evilCharacter.getPosition().x;
        double magVec = Math.hypot(deltaX, deltaY);
        double unitCircleX = deltaX / magVec;
        double unitCircleY = deltaY / magVec;

        double northDotVector = unitCircleY;
        double northEastDotVector = (Math.sqrt(2)/2 * unitCircleX) + (Math.sqrt(2)/2 * unitCircleY);
        double eastDotVector = unitCircleX;
        double southEastDotVector = (Math.sqrt(2)/2 * unitCircleX) - (Math.sqrt(2)/2 * unitCircleY);
        double southDotVector = -unitCircleY;
        double southWestDotVector = -(Math.sqrt(2)/2 * unitCircleX) - (Math.sqrt(2)/2 * unitCircleY);
        double westDotVector = -unitCircleX;
        double northWestDotVector = -(Math.sqrt(2)/2 * unitCircleX) + (Math.sqrt(2)/2 * unitCircleY);

        ArrayList<Double> directionVectors = new ArrayList<Double>();
        directionVectors.add(northDotVector);
        directionVectors.add(northEastDotVector);
        directionVectors.add(eastDotVector);
        directionVectors.add(southEastDotVector);
        directionVectors.add(southDotVector);
        directionVectors.add(southWestDotVector);
        directionVectors.add(westDotVector);
        directionVectors.add(northWestDotVector);

        double max = Integer.MIN_VALUE;
        for (Double directionVector : directionVectors) {
            if (directionVector > max){
                max = directionVector;
            }
        }

        if (max == northDotVector) {
            evilCharacter.setState(GameCharacter.State.FACEUP);
            evilCharacter.go(GameCharacter.Direction.N);

        } else if (max == northEastDotVector) {
            evilCharacter.setState(GameCharacter.State.FACERIGHT);
            evilCharacter.go(GameCharacter.Direction.NE);

        } else if (max == eastDotVector) {
            evilCharacter.setState(GameCharacter.State.FACERIGHT);
            evilCharacter.go(GameCharacter.Direction.E);

        } else if (max == southEastDotVector) {
            evilCharacter.setState(GameCharacter.State.FACERIGHT);
            evilCharacter.go(GameCharacter.Direction.SE);

        } else if (max == southDotVector) {
            evilCharacter.setState(GameCharacter.State.FACEDOWN);
            evilCharacter.go(GameCharacter.Direction.S);

        } else if (max == southWestDotVector) {
            evilCharacter.setState(GameCharacter.State.FACELEFT);
            evilCharacter.go(GameCharacter.Direction.SW);

        } else if (max == westDotVector) {
            evilCharacter.setState(GameCharacter.State.FACELEFT);
            evilCharacter.go(GameCharacter.Direction.W);

        }
          else if (max == northWestDotVector) {
            evilCharacter.setState(GameCharacter.State.FACELEFT);
            evilCharacter.go(GameCharacter.Direction.NW);
        }

    }

    @Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
    }

	@Override
	public void show() {

	}
		
	@Override
	public void hide() {

	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
