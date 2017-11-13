package com.gxiv.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gxiv.game.Gxiv;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1920;
        config.height = 1080;
        config.fullscreen = false;
        int fps = 60;
        config.foregroundFPS = fps;
        config.backgroundFPS = fps;
        config.vSyncEnabled = true;
		new LwjglApplication(new Gxiv(), config);
	}
}
