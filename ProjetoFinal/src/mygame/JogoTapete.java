package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class JogoTapete extends SimpleApplication {

    public static void main(String[] args) {
        JogoTapete app = new JogoTapete();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
     
        Box floor = new Box(1f, 1f, 1f);
        floor.updateGeometry(new Vector3f(-5f, -1.5f, -5f), new Vector3f(4f, -1.5f, 4f));
        Geometry tapete = new Geometry("Floor", floor);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tap = assetManager.loadTexture("Textures/Terrain/Rock/Rock_normal.png");
        mat.setTexture("ColorMap", tap);
        tapete.setMaterial(mat);
        
        tapete.scale(2f, 2f, 0.5f);
       

        rootNode.attachChild(tapete);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}
