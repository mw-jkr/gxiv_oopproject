package com.gxiv.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gxiv.game.Gxiv;
import com.gxiv.game.util.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = (int) Constants.SCREEN_WIDTH;
        config.height = (int) Constants.SCREEN_HEIGHT;;
        config.fullscreen = false;
        final int fps = 60;
        config.foregroundFPS = fps;
        config.backgroundFPS = fps;
        config.vSyncEnabled = true;

		new LwjglApplication(new Gxiv(), config);
	}
}
