package testProject;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

	public static void main(String[] argv) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "curling";
		config.width = 800;
		config.height = 480;
		config.samples = 8;
		config.vSyncEnabled = false;
		config.useCPUSynch = false;
		config.useGL20 = true;

		config.fullscreen = false;
		new LwjglApplication(new GdxTest(), config);
	}

}
