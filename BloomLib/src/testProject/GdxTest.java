package testProject;

import testProjectShaders.ShaderLoader;

import bloom.Bloom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class GdxTest extends ApplicationAdapter {
	PerspectiveCamController camController;
	PerspectiveCamera cam;
	ShaderProgram shader;
	Mesh mesh;
	Texture texture;
	Bloom bloom;

	FPSLogger logger = new FPSLogger();

	public void render() {
		logger.log();
		camController.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl.glCullFace(GL10.GL_BACK);
		Gdx.gl.glDepthMask(true);

		bloom.capture();
		texture.bind(0);
		shader.begin();
		{
			shader.setUniformMatrix("u_projectionViewMatrix", cam.combined);
			mesh.render(shader, GL10.GL_TRIANGLES);
		}
		shader.end();

		bloom.render();

	}

	public void create() {

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(0, 0f, 3f);
		cam.lookAt(0, 0, 0);
		cam.update();
		cam.near = 0.1f;
		cam.far = 100;
		camController = new PerspectiveCamController(cam);
		Gdx.input.setInputProcessor(camController);
		mesh = Shapes.genCube();
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		shader = ShaderLoader.createShader("default", "default");

		bloom = new Bloom(256, 256, true, false, true);
	}

	public void dispose() {
		bloom.dispose();
		mesh.dispose();
		texture.dispose();
		shader.dispose();
	}

	public void resume() {
		bloom.resume();
	}

}
