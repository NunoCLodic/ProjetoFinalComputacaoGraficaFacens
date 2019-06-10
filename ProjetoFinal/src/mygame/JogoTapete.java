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

public class JogoTapete extends SimpleApplication {

    private Pontuacao pontuacao;
    private boolean dir = true;
    private Material esfera;
    private Material colisao;
    private BitmapText hudText;
   

    public static void main(String[] args) {
        JogoTapete app = new JogoTapete();
        Pontuacao pontuacao = new Pontuacao();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {

        cam.setLocation(new Vector3f(0, 0, 20));
        flyCam.setEnabled(false);

        criarTapete();
        criarPortaEntrada();
        criarPortaSaida();
        esfera = CriarEsfera();
        colisao = criarAreaDescisao();
        pontuacao = new Pontuacao ();
        hudText = new BitmapText(guiFont, false);

        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Pause");

        inputManager.addMapping("Colisao", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Colisao");
        
        
        

    }

    @Override
    public void simpleUpdate(float tpf) {
        
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);    
        hudText.setText("Score:" + pontuacao.score);// font color
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        guiNode.attachChild(hudText);
        
        
        
        Spatial s = rootNode.getChild("S1");
        s.move(tpf, 0, 0);
        System.out.println(s.getLocalTranslation().x);

        if (dir == true) {

            s.move(tpf, 0, 0);
            if (s.getLocalTranslation().x > 8) {
                dir = false;

            } else {
                System.out.println(s.getLocalTranslation().x);

                if (dir == true) {
                    s.move(tpf, 0, 0);
                    if (s.getLocalTranslation().x > 8) {
                        dir = false;
                    }

                } else {
                    s.move(-tpf, 0, 0);
                }

            }
            s.move(-tpf, 0, 0);

        }
        
       

    }
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                //isEnabled(!isEnabled()).

            }
            if (name.equals("Colisao") && !keyPressed) {
                if (esfera.getName()==colisao.getName() && verificaArea() ){
                    pontuacao.controlePontos(true);
                } else {
                    pontuacao.controlePontos(false);
                }
            }

        }

        private boolean verificaArea() {
                    
            return true;
            }
            
        
    };

//Criação da Tapete rolante
    public void criarTapete() {
        //tapete
        Box floor = new Box(1f, 1f, 1f);
        floor.updateGeometry(new Vector3f(-5f, -1.5f, -5f), new Vector3f(4f, -1.45f, 4f));
        Geometry tapete = new Geometry("Floor", floor);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tap = assetManager.loadTexture("Textures/Tapete.jpg");
        mat.setTexture("ColorMap", tap);
        tapete.setMaterial(mat);
        tapete.scale(2f, 2.2f, 0.5f);
        tapete.move(0f, 0f, 0f);
        rootNode.attachChild(tapete);

        //Resto do Chao
        Box floor1 = new Box(1f, 1f, 1f);
        floor1.updateGeometry(new Vector3f(-5f, -1.5f, -5f), new Vector3f(4f, -1.5f, 1.5f));
        Geometry tapete1 = new Geometry("Floor", floor1);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tap1 = assetManager.loadTexture("Textures/Barreira.jpg");
        mat1.setTexture("ColorMap", tap1);
        tapete1.setMaterial(mat1);
        tapete1.scale(2.2f, 2f, 1f);
        tapete1.move(0f, -0.2f, 1.5f);
        rootNode.attachChild(tapete1);

    }
//Criação da porta de entrada dos objetos

    public void criarPortaEntrada() {
        Box portaEntrada = new Box(1f, 1f, 1f);
        portaEntrada.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 4.5f));
        Geometry geom = new Geometry("Box", portaEntrada);
        geom.move(-10f, -3.2f, -2.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Porta.png");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
    }
//Criação da porta de saida dos objetos

    public void criarPortaSaida() {
        Box portaSaida = new Box(1f, 1f, 1f);
        portaSaida.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 4.5f));
        Geometry geom = new Geometry("Box", portaSaida);
        geom.move(7f, -3.2f, -2.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Fogo.jpg");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);

        Box esq = new Box(1f, 1f, 1f);
        esq.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 1f));
        Geometry geom1 = new Geometry("Box", esq);
        geom1.move(7f, -3.2f, -3.5f);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex1 = assetManager.loadTexture("Textures/Pedra.jpg");
        mat1.setTexture("ColorMap", tex1);
        geom1.setMaterial(mat1);
        rootNode.attachChild(geom1);

        Box dir = new Box(1f, 1f, 1f);
        dir.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 1f));
        Geometry geom2 = new Geometry("Box", dir);
        geom2.move(7f, -3.2f, 2f);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex2 = assetManager.loadTexture("Textures/Pedra.jpg");
        mat2.setTexture("ColorMap", tex2);
        geom2.setMaterial(mat2);
        rootNode.attachChild(geom2);

        Box cima = new Box(1f, 1f, 1f);
        cima.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 0.6f, 6.5f));
        Geometry geom3 = new Geometry("Box", cima);
        geom3.move(7f, -0.2f, -3.5f);
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex3 = assetManager.loadTexture("Textures/Pedra.jpg");
        mat3.setTexture("ColorMap", tex3);
        geom3.setMaterial(mat3);
        rootNode.attachChild(geom3);

    }

    public Material CriarEsfera() {
        Sphere s = new Sphere(20, 20, 1);
        Geometry esfera = new Geometry("S1", s);
        esfera.rotate(0, FastMath.PI, 0);
        esfera.move(-5f, -3f, 1f);
        Material esferaM = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        esferaM.setColor("Color", ColorRGBA.Red);
        esfera.setMaterial(esferaM);
        esfera.scale(0.4f, 0.4f, 0.4f);

        rootNode.attachChild(esfera);

        return esferaM;

    }

    private Material criarAreaDescisao() {
        //Area de decisão 1
        Box floorArea = new Box(1f, 1f, 1f);
        floorArea.updateGeometry(new Vector3f(-5f, -1.5f, -5f), new Vector3f(4f, -1.45f, 4f));
        Geometry tapeteArea = new Geometry("Floor", floorArea);
        Material matArea = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //Texture tapArea = assetManager.loadTexture("Textures/Area.jpg");
        matArea.setColor("Color", ColorRGBA.Red);
        tapeteArea.setMaterial(matArea);
        tapeteArea.scale(0.4f, 2.2f, 0.5f);
        tapeteArea.move(0f, 0.01f, 0f);
        rootNode.attachChild(tapeteArea);

        
        return matArea;
    }

}
