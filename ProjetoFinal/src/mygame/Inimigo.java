package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author delga
 */
final class Inimigo {

    Sphere s;
    Geometry esfera;
    int cor;
    Material esferaM;

    public Inimigo(int nivel, AssetManager manager, int cor) {
        s = new Sphere(20, 20, 1);
        esfera = new Geometry("S1", s);
        esferaM = new Material(manager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        esfera.move(-10f, -2.8f, -0.2f);
        esfera.rotate(0, 0, FastMath.PI);
        esfera.setMaterial(esferaM);
        esfera.scale(0.4f, 0.4f, 0.4f);

        randomColor(cor, esferaM);
       
    }
    

    public void randomColor(int x, Material esferaM) {
        switch (x) {
            case 1:
                esferaM.setColor("Color", ColorRGBA.Blue); 
                break;
            case 2:
                esferaM.setColor("Color", ColorRGBA.Red); 
                break;
            default:
                esferaM.setColor("Color", ColorRGBA.Green); 
                break;
        }

    }

}
