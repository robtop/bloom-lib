package testProject;

import testProjectShaders.ShaderLoader;

import bloom.Bloom;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class GdxTest implements ApplicationListener {

	private final static int FBO_W = 400;
	private final static int FBO_H = 240;

	PerspectiveCamController camController;
	PerspectiveCamera cam;

	ShaderProgram shader;

	Mesh mesh;
	Mesh mesh2;

	Texture texture;
	Texture texture2;

	FPSLogger logger = new FPSLogger();

	float time;
	Vector3 t = new Vector3();

	public void render() {
		logger.log();

		final float delta = Gdx.graphics.getDeltaTime();
		camController.update(delta);
		time += delta;

		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glActiveTexture(GL10.GL_TEXTURE0);
		Gdx.gl.glDepthMask(true);

		Gdx.gl.glEnable(GL10.GL_CULL_FACE);

		bloom.capture();

		texture.bind(0);
		shader.begin();
		{

			shader.setUniform3fv(
					"lights",
					new float[] { MathUtils.sin(time * 0.9f) * 3.151f,
							MathUtils.sin(time * -0.51f),
							MathUtils.cos(time * -0.2f) * 3.27f,
							MathUtils.sin(time) * -0.5f * 3.38f,
							MathUtils.sin(time * 0.53f),
							MathUtils.cos(time) * 0.52f * 3.15f,
							MathUtils.sin(time * 0.71f) * 3.24f,
							MathUtils.sin(time * 0.41f),
							MathUtils.cos(time * 0.11f) * 3.26f, }, 0, 9);

			shader.setUniform3fv("col", new float[] { 1.0f, 0.75f, 0.75f,
					0.75f, 1.00f, 0.75f,
					0.75f, 0.75f, 1.0f }, 0, 9);

			shader.setUniformMatrix("u_projectionViewMatrix", cam.combined);

			final float x = cam.position.x;
			final float y = cam.position.y;
			final float z = cam.position.z;
			final float len = (float) (1f / Math.sqrt(x * x + y * y + z * z));
			shader.setUniformf("camPos", x * len, y * len, z * len);
			shader.setUniformi("u_texture", 0);

			mesh2.render(shader, GL10.GL_TRIANGLES);
			mesh.render(shader, GL10.GL_TRIANGLES);

		}
		shader.end();

		bloom.render();

	}

	public void create() {

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.near = 0.1f;
		cam.far = 64f;
		cam.position.set(2, 1f, 3.5f);
		cam.lookAt(0, 0, 0);
		cam.update();

		camController = new PerspectiveCamController(cam);
		Gdx.input.setInputProcessor(camController);

		mesh = Shapes.genCube();
		mesh2 = Shapes.genCube();

		int size = mesh2.getVertexSize() * 6;
		float[] tmp = new float[size];
		float[] tmp2 = new float[size];
		mesh2.getVertices(tmp);
		System.arraycopy(tmp, 0, tmp2, 0, size);
		int i = 0;
		while (i < size) {
			tmp[i++] = tmp2[i + 1];
			tmp[i++] = tmp2[i - 1];
			tmp[i++] = tmp2[i - 3];
			tmp[i++] *= -1;
			tmp[i++] *= -1;
			tmp[i++] *= -1;
			i += 2;
		}
		i = 0;

		mesh2.setVertices(tmp, 0, mesh2.getVertexSize() * 6);
		mesh2.scale(8, 8, 8);
		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// texture2 = new Texture(Gdx.files.internal("data/metal.png"));
		// texture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		shader = ShaderLoader.createShader("light", "light");

		bloom = new Bloom(FBO_W, FBO_H, true, false, true);
		bloom.setTreshold(0.3f);
		bloom.setBloomIntesity(5.5f);
		bloom.setOriginalIntesity(0.6f);
	}

	Bloom bloom;

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void dispose() {
		bloom.dispose();
		mesh.dispose();
		mesh2.dispose();
		texture.dispose();
		shader.dispose();

	}

	public void resume() {
		bloom.resume();
	}

}
