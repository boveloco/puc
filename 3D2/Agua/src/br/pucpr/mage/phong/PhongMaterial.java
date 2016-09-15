package br.pucpr.mage.phong;

import org.joml.Vector3f;

import br.pucpr.mage.Texture;

public class PhongMaterial extends SimpleMaterial {
    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;
    private float specularPower;
    
    public PhongMaterial(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float specularPower) {
        super("/br/pucpr/mage/resource/phong/phong");
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularPower = specularPower;
    }
    
    public PhongMaterial(Vector3f ambient, Vector3f diffuse) {
        this(ambient, diffuse, new Vector3f(), 0.0f);
    }

    public PhongMaterial(Vector3f color) {
        this(color, color, new Vector3f(), 0.0f);
    }
    
    public PhongMaterial() {
        this(new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    public Vector3f getAmbientColor() {
        return ambientColor;
    }
    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }
    public Vector3f getSpecularColor() {
        return specularColor;
    }
    public float getSpecularPower() {
        return specularPower;
    }
    
    public void setSpecularPower(float specularPower) {
        this.specularPower = specularPower;
    }    
    
    public PhongMaterial setTexture(Texture texture) {
        return (PhongMaterial) setTexture("uTexture", texture);
    }
    
    @Override
    public void apply() {
        getShader().setUniform("uAmbientMaterial", ambientColor);
        getShader().setUniform("uDiffuseMaterial", diffuseColor);
        getShader().setUniform("uSpecularMaterial", specularColor);
        getShader().setUniform("uSpecularPower", specularPower);
        super.apply();
    }
}
