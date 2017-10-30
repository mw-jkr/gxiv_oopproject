package com.gxiv.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gxiv.game.gxiv;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GXIV | The Game";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new gxiv(), config);
	}
}
