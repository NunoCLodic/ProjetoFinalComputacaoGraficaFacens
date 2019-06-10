
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Asus
 */
public class Area {

    Box floorArea;
    Geometry tapeteArea;
    int cor;
    Material matArea;

    public Area(int nivel, AssetManager manager) {
        Box floorArea = new Box(1f, 1f, 1f);
        cor = ThreadLocalRandom.current().nextInt(1, 4); //sorteia um numero de 1 a 3
        tapeteArea = new Geometry("Floor", floorArea);
        tapeteArea.rotate(0, FastMath.PI, 0);
        tapeteArea.move(-0.6f, -3.3f, -0.25f);
        matArea = new Material(manager, "Common/MatDefs/Misc/Unshaded.j3md");
//Provisorio
        matArea.setColor("Color", ColorRGBA.Blue);
//        randomColor(cor, matArea);
        tapeteArea.setMaterial(matArea);
        tapeteArea.scale(1f, 0.2f, 2.2f);
    }

//    public void randomColor (int x, Material matArea){
//        switch (x) {
//            case 1:
//                matArea.setColor("Color", ColorRGBA.Blue); 
//                break;
//            case 2:
//                matArea.setColor("Color", ColorRGBA.Red); 
//                break;
//            default:
//                matArea.setColor("Color", ColorRGBA.Green); 
//                break;
//        }
//    }
}
