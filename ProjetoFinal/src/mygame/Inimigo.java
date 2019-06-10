/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.sun.prism.paint.Color;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author delga
 */
class Inimigo {

    public int velocidade;
    Sphere s;
    Geometry esfera;
    int cor;
    Material esferaM;

    public Inimigo(int nivel, AssetManager manager) {
        velocidade = 10 * nivel;
        s = new Sphere(20, 20, 1);
        cor = ThreadLocalRandom.current().nextInt(1, 4); //sorteia um numero de 1 a 3
        esfera = new Geometry("S1", s);
        esfera.rotate(0, FastMath.PI, 0);
        esfera.move(-5f, -3f, 1f);
        esferaM = new Material(manager, "Common/MatDefs/Misc/Unshaded.j3md");

        randomColor(cor, esferaM);

        esfera.setMaterial(esferaM);
        esfera.scale(0.4f, 0.4f, 0.4f);
    }
    
    public void randomColor (int x, Material esferaM){
        switch (x) {
            case 1:
                esferaM.setColor("Color", ColorRGBA.Blue); //random color aqui
                break;
            case 2:
                esferaM.setColor("Color", ColorRGBA.Red); //random color aqui
                break;
            default:
                esferaM.setColor("Color", ColorRGBA.Green); //random color aqui
                break;
        }
    
    
    }


}
