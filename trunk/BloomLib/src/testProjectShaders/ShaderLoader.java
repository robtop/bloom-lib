package testProjectShaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public final class ShaderLoader {

	static final public ShaderProgram createShader(String vertexName,
			String fragmentName) {
		String vertexShader = FileUtils.getContent("testProjectShaders/" + vertexName
				+ ".vertex");
		String fragmentShader = FileUtils.getContent("testProjectShaders/" + fragmentName
				+ ".fragment");
		ShaderProgram.pedantic = false;		
		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			System.out.println(shader.getLog());
			Gdx.app.exit();
		} else
			Gdx.app.log("shader compiled", shader.getLog());
		return shader;
	}
}
