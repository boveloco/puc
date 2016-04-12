package br.com.puc.andre.mage;

public interface Scene {
	void init();
	void update(float secs);
	void draw();
	void deinit();
	void keyPressed(long window, int key, int scancode, int action, int mods);
}
