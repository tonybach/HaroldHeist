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
            Assets.bustedSound.play();
            game.setScreen(new GameOverScreen(game, renderer));
        }
        if(renderer.getGameScore() > 0) {
            moveAntag();
        }
        if(renderer.getGameScore() > 9){
            moveEvilTwin();
        }
    }


    private void moveAntag() {

        double deltaY = protag.getPosition().y - antag.getPosition().y;
        double deltaX = protag.getPosition().x - antag.getPosition().x;
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
            antag.goNorth();

        } else if (max == northEastDotVector) {
            antag.goNorthEast();

        } else if (max == eastDotVector) {
            antag.goEast();

        } else if (max == southEastDotVector) {
            antag.goSouthEast();

        } else if (max == southDotVector) {
            antag.goSouth();

        } else if (max == southWestDotVector) {
            antag.goSouthWest();

        } else if (max == westDotVector) {
            antag.goWest();

        }
          else if (max == northWestDotVector) {
            antag.goNorthWest();
        }

    }

    private void moveEvilTwin() {

        double deltaY = protag.getPosition().y - evilTwin.getPosition().y;
        double deltaX = protag.getPosition().x - evilTwin.getPosition().x;
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
            evilTwin.goNorth();

        } else if (max == northEastDotVector) {
            evilTwin.goNorthEast();

        } else if (max == eastDotVector) {
            evilTwin.goEast();

        } else if (max == southEastDotVector) {
            evilTwin.goSouthEast();

        } else if (max == southDotVector) {
            evilTwin.goSouth();

        } else if (max == southWestDotVector) {
            evilTwin.goSouthWest();

        } else if (max == westDotVector) {
            evilTwin.goWest();

        }
        else if (max == northWestDotVector) {
            evilTwin.goNorthWest();
        }
    }


    @Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		renderer.setSize(width, height);
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
		
	@Override
	public void hide() {
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
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
