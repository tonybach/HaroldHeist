package com.Harold_Heist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Texture protagRight;
	public static Texture protagLeft;
	public static Texture protagUp;
	public static Texture protagDown;


    public static Texture antagRight;
	public static Texture antagLeft;
	public static Texture antagUp;
	public static Texture antagDown;

    public static Texture foodBanana;
    public static Texture foodApple;
    public static Texture foodBacon;
    public static Texture foodCake;


    public static Texture soundOn;
	public static Texture soundOff;
	
	public static Music catchyMusic;
    public static Sound eatingSound;
	
	public static void load() {
        foodBanana = new Texture(Gdx.files.internal("graphics/foodBanana.png"));
        foodApple = new Texture(Gdx.files.internal("graphics/foodApple.png"));
        foodBacon = new Texture(Gdx.files.internal("graphics/foodBacon.png"));
        foodCake = new Texture(Gdx.files.internal("graphics/foodCake.png"));

        protagRight = new Texture(Gdx.files.internal("graphics/protagRight.png"));
        protagLeft = new Texture(Gdx.files.internal("graphics/protagLeft.png"));
        protagUp = new Texture(Gdx.files.internal("graphics/protagUp.png"));
        protagDown = new Texture(Gdx.files.internal("graphics/protagDown.png"));

        antagRight =  new Texture(Gdx.files.internal("graphics/antagRight.png"));
        antagLeft = new Texture(Gdx.files.internal("graphics/antagLeft.png"));
        antagUp = new Texture(Gdx.files.internal("graphics/antagUp.png"));
		antagDown = new Texture(Gdx.files.internal("graphics/antagDown.png"));
		
		soundOn = new Texture(Gdx.files.internal("graphics/soundOn.png"));
		soundOff = new Texture(Gdx.files.internal("graphics/soundOff.png"));
//        eatingSound = Gdx.audio.newSound(Gdx.files.internal("music/eatingSound.mp3"));
        catchyMusic = Gdx.audio.newMusic(Gdx.files.internal("music/catchyMusic.mp3"));

		catchyMusic.setLooping(true);
		catchyMusic.play();
	}

    public static void dispose() {

    }
}
