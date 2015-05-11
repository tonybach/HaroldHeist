package com.Harold_Heist.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.Harold_Heist.View.GameScreen;
import com.Harold_Heist.HaroldHeist;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(704, 480);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new HaroldHeist();
        }
}