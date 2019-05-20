package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class JogoTapete extends SimpleApplication {

    Node nodeNinja = new Node("Ninja");
    private boolean isRunning = true;

    public static void main(String[] args) {
        JogoTapete app = new JogoTapete();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        CriarTapete();
        CriarPortaEntrada();
        CriarPortaSaida();
        CriarObjetos();

    }
//Criação da Tapete rolante
    public void CriarTapete() {
        Box floor = new Box(1f, 1f, 1f);
        floor.updateGeometry(new Vector3f(-5f, -1.5f, -5f), new Vector3f(4f, -1.5f, 4f));
        Geometry tapete = new Geometry("Floor", floor);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tap = assetManager.loadTexture("Textures/Terrain/Rock/Rock_normal.png");
        mat.setTexture("ColorMap", tap);
        tapete.setMaterial(mat);

        tapete.scale(2f, 2f, 0.5f);
        tapete.move(0f, 0f, 0f);

        rootNode.attachChild(tapete);
    }
//Criação da porta de entrada dos objetos
    public void CriarPortaEntrada() {
        Box portaEntrada = new Box(1f, 1f, 1f);
        portaEntrada.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 4.5f));
        Geometry geom = new Geometry("Box", portaEntrada);
        geom.move(-10f, -3.2f, -2.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Sky/Lagoon/lagoon_north.jpg");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }
//Criação da porta de saida dos objetos
    public void CriarPortaSaida() {
        Box portaSaida = new Box(1f, 1f, 1f);
        portaSaida.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 4.5f));
        Geometry geom = new Geometry("Box", portaSaida);
        geom.move(7f, -3.2f, -2.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Sky/Lagoon/lagoon_north.jpg");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);

    }

    public void CriarObjetos() {
        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja.scale(0.02f);
        ninja.move(-5f, -1.5f, 0f);
        ninja.rotate(0f, 4.7f, 0f);

        nodeNinja.attachChild(ninja);
        rootNode.attachChild(nodeNinja);

    }
    
    public void drawNinja(){
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (isRunning) {
            for (Spatial s : nodeNinja.getChildren()) {
                s.move(tpf, 0, 0);

                if (s.getLocalTranslation().x >= 3) {
                    nodeNinja.detachChild(s);
                }
            }
        }

    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
    
}
