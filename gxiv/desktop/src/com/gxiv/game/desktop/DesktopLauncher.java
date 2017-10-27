package com.gxiv.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gxiv.game.Gxiv;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        int fps = 60;
        config.foregroundFPS = fps;
        config.backgroundFPS = fps;
        config.vSyncEnabled = true;
		new LwjglApplication(new Gxiv(), config);
	}
}
