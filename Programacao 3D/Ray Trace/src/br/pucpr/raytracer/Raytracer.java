package br.pucpr.raytracer;

import org.joml.Vector3f;

import static br.pucpr.raytracer.Vector3fOps.*;

import br.pucpr.raytracer.primitives.Primitive;
import br.pucpr.raytracer.primitives.Result;

public class Raytracer {
	private static final float INFINITE = 1000000.0f;
	private static final int TRACEDEPTH = 6;
	
	private Canvas canvas;
	private Scene scene = new Scene();

	private float wx1;

	private float wx2;

	private float wy1;

	private float wy2;

	private float dx;

	private float dy;

	public Raytracer() {
		this(1.5f);
	}

	public Raytracer(float zoom) {
		this.canvas = new Canvas(1024 * 10, 768 * 10);
		float aspect = (canvas.getWidth() / canvas.getHeight()) * 10;

		wx1 = -(aspect / 2.0f) / zoom;
		wx2 = -wx1;

		wy1 = (10.0f / 2.0f) / zoom;
		wy2 = -wy1;

		dx = (wx2 - wx1) / canvas.getWidth();
		dy = (wy2 - wy1) / canvas.getHeight();
	}

	public void render() {
		Vector3f o = new Vector3f(0, 0, -5);
		float sy = wy1;
		for (int y = 0; y < canvas.getHeight(); y++) {
			float sx = wx1;
			for (int x = 0; x < canvas.getWidth(); x++) {
				float hdx = dx / 2.0f;
				float hdy = dy / 2.0f;
				Vector3f dir1 = new Vector3f(sx + hdx, sy+hdy, 0).sub(o).normalize();
				Vector3f dir2 = new Vector3f(sx - hdx, sy+hdy, 0).sub(o).normalize();
				Vector3f dir3 = new Vector3f(sx + hdx, sy-hdy, 0).sub(o).normalize();
				Vector3f dir4 = new Vector3f(sx - hdx, sy-hdy, 0).sub(o).normalize();
				Vector3f color1 = raytrace(new Ray(o, dir1), 1);
				Vector3f color2 = raytrace(new Ray(o, dir2), 1);
				Vector3f color3 = raytrace(new Ray(o, dir3), 1);
				Vector3f color4 = raytrace(new Ray(o, dir4), 1);
				Vector3f color = color1.add(color2).add(color3).add(color4).div(4);
				canvas.set(x, y, color);
				sx += dx;

			}
			sy += dy;
		}
		canvas.save();
	}

	public Vector3f raytrace(Ray ray, int depth) {
		if(depth > TRACEDEPTH)
			return new Vector3f();
		float distance = INFINITE;

		Primitive prim = null;

		for (Primitive obj : scene.getObjects()) {
			Result result = obj.intersect(ray, distance);
			if (result.isHit()) {
				distance = result.getDistance();
				prim = obj;
			}
		}
		if (prim == null)
			return scene.getBackground();

		Vector3f pi = add(ray.getOrigin(), mul(ray.getDirection(), distance));
		Vector3f color = new Vector3f();
		for (Light light : scene.getLights()) {
			Vector3f L = sub(light.getPosition(), pi);
			float ldist = L.length();
			L.div(ldist);
			
			Ray shadowRay = new Ray(pi, L);
			boolean lit = true;
			for (Primitive obj : scene.getObjects()){
				if(obj.intersect(shadowRay, ldist).isHit()){
					lit = false;
					break;
				}
			}
			if(!lit)
				continue;
			
			
			Vector3f N = prim.getNormalAt(pi);
			if (prim.getMaterial().getDiffuse() > 0) {
				float dot = N.dot(L);
				if (dot > 0) {
					float diff = dot * prim.getMaterial().getDiffuse();
					Vector3f diffuse = mul(diff, light.getColor()).mul(
							prim.getColor());
					color.add(diffuse);
				}
			}
			if (prim.getMaterial().getSpecular() > 0) {
				Vector3f V = ray.getDirection();
				Vector3f R = reflect(L, N);

				float dot = V.dot(R);

				if (dot > 0) {
					float specularIntensity = (float) Math.pow(dot, prim
							.getMaterial().getSpecular());
					color.add(mul(specularIntensity, light.getColor()));
				}
			}
		}
		
		float refl = prim.getMaterial().getReflection();
		if(refl > 0.0f){
			Vector3f N = prim.getNormalAt(pi);
			Vector3f R = reflect(ray.getDirection(), N);
			Vector3f rcol = raytrace(new Ray(pi, R), depth + 1);
			color.lerp(rcol, refl);
		}
		
		return color;
	}
}
