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

    public static Texture evilTwinRight;
    public static Texture evilTwinLeft;
    public static Texture evilTwinUp;
    public static Texture evilTwinDown;

    public static Texture foodBanana;
    public static Texture foodApple;
    public static Texture foodBacon;
    public static Texture foodCake;

    public static Texture soundOnIcon;
	public static Texture soundOffIcon;
	
	public static Music catchyMusic;

    public static Sound blipSound;
    public static Sound eatingSound;
    public static Sound bustedSound;
    public static Sound evilTwinAppearingSound;
	
	public static void load() {
        foodBanana = new Texture(Gdx.files.internal("graphics/foodBanana.png"));
        foodApple = new Texture(Gdx.files.internal("graphics/foodApple.png"));
        foodBacon = new Texture(Gdx.files.internal("graphics/foodBacon.png"));
        foodCake = new Texture(Gdx.files.internal("graphics/foodCake.png"));

        protagRight = new Texture(Gdx.files.internal("graphics/protagRight2.png"));
        protagLeft = new Texture(Gdx.files.internal("graphics/protagLeft2.png"));
        protagUp = new Texture(Gdx.files.internal("graphics/protagUp.png"));
        protagDown = new Texture(Gdx.files.internal("graphics/protagDown3.png"));

        antagRight =  new Texture(Gdx.files.internal("graphics/antagRight.png"));
        antagLeft = new Texture(Gdx.files.internal("graphics/antagLeft.png"));
        antagUp = new Texture(Gdx.files.internal("graphics/antagUp.png"));
		antagDown = new Texture(Gdx.files.internal("graphics/antagDown.png"));

        evilTwinRight = new Texture(Gdx.files.internal("graphics/ghostAntagRight.png"));
        evilTwinLeft = new Texture(Gdx.files.internal("graphics/ghostAntagLeft.png"));
        evilTwinUp = new Texture(Gdx.files.internal("graphics/ghostAntagUp.png"));
        evilTwinDown = new Texture(Gdx.files.internal("graphics/ghostAntagDown.png"));

		soundOnIcon = new Texture(Gdx.files.internal("graphics/soundOn.png"));
        soundOffIcon = new Texture(Gdx.files.internal("graphics/soundOff.png"));

        catchyMusic = Gdx.audio.newMusic(Gdx.files.internal("music/catchyMusic.mp3"));

        blipSound = Gdx.audio.newSound(Gdx.files.internal("music/blipSound.wav"));
        eatingSound = Gdx.audio.newSound(Gdx.files.internal("music/eatingSound.wav"));
        bustedSound = Gdx.audio.newSound(Gdx.files.internal("music/bustedSound.wav"));
        evilTwinAppearingSound = Gdx.audio.newSound(Gdx.files.internal("music/evilTwinAppearingSound.wav"));

		catchyMusic.setLooping(true);
		catchyMusic.play();

	}

    public static void dispose() {

    }
}
