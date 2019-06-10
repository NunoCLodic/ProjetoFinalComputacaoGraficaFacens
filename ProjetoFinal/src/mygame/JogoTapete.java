package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;

public class JogoTapete extends SimpleApplication {

    private Pontuacao pontuacao;
    private boolean dir = true;
    private Material esfera;
    private Material colisao;

    private Inimigo inimigo;
    private Area area;

    private BitmapText hudText;
    private BitmapText hudText1;
    private BitmapText hudText2;

    boolean RemoveEsfera = false;
    boolean Iniciar = true;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(900, 600);
        JogoTapete app = new JogoTapete();
        app.setShowSettings(false);
        app.setSettings(settings);
        app.start();
        settings.size();
        Pontuacao pontuacao = new Pontuacao();
    }

    @Override
    public void simpleInitApp() {
        cam.setLocation(new Vector3f(0, -1, 20));
        flyCam.setEnabled(false);

        criarTapete();
        criarPortaEntrada();
        criarPortaSaida();

        esfera = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        pontuacao = new Pontuacao();

        criarTapete();
        criarPortaEntrada();
        criarPortaSaida();
        CriaImagens();
        CriaAlerta();
        pontuacao = new Pontuacao();

        hudText = new BitmapText(guiFont, false);
        hudText1 = new BitmapText(guiFont, false);
        hudText2 = new BitmapText(guiFont, false);

        inputManager.addMapping("Iniciar", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addListener(actionListener, "Pause");
        inputManager.addMapping("Colisao", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Colisao");
    }

    @Override
    public void simpleUpdate(float tpf) {
    if (Iniciar == true){
        inimigo = new Inimigo(pontuacao.nivel, assetManager);
        rootNode.attachChild(inimigo.esfera);

        area = new Area(pontuacao.nivel, assetManager);
        rootNode.attachChild(area.tapeteArea);

        hudText.setSize(guiFont.getCharSet().getRenderedSize());
        hudText.setColor(ColorRGBA.Green);
        hudText.setText("Score: " + pontuacao.score);// font color
        hudText.setLocalTranslation(630, hudText.getLineHeight() / 2 + 550, 20);
        guiNode.attachChild(hudText);

        hudText1.setSize(guiFont.getCharSet().getRenderedSize());
        hudText1.setColor(ColorRGBA.Green);
        hudText1.setText("" + pontuacao.nivel);// font color
        hudText1.setLocalTranslation(570, hudText1.getLineHeight() / 2 + 550, 20);
        guiNode.attachChild(hudText1);

        hudText2.setSize(guiFont.getCharSet().getRenderedSize());
        hudText2.setColor(ColorRGBA.Green);
        hudText2.setText("" + pontuacao.vida);// font color
        hudText2.setLocalTranslation(480, hudText2.getLineHeight() / 2 + 550, 20);
        guiNode.attachChild(hudText2);

        Spatial s = rootNode.getChild("S1");
        s.move(tpf, 0, 0);
        System.out.println(s.getLocalTranslation().x);

        if (dir == true) {

            s.move(tpf, 0, 0);

            if (s.getLocalTranslation().x > 7) {
                s.removeFromParent();
            }
            if (RemoveEsfera == true) {
                s.removeFromParent();
                RemoveEsfera = false;
            }
            if (pontuacao.nivel < 30) {
                s.move(tpf + pontuacao.nivel * 0.06f, 0, 0);
            } else {
                s.move(tpf + 20.0f, 0, 0);
            }
        }
       }
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Iniciar") && !keyPressed) {
                Iniciar = true;
            }
            if (name.equals("Colisao") && !keyPressed) {
                    ///Iniciar = true;
                if (inimigo.cor == area.cor) {
                    pontuacao.controlePontos(true);
                    RemoveEsfera = true;
                } else {
                    pontuacao.controlePontos(false);
                }
            }

        }

//        private boolean verificaArea() {
//
//            return true;
//        }
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

//Criaçao das imagens
    public void CriaImagens() {
        Picture vida = new Picture("HUD Picture");
        vida.setImage(assetManager, "Imagens/Coracao.png", true);
        vida.setWidth(settings.getWidth() / 35);
        vida.setHeight(settings.getHeight() / 25);
        vida.setPosition(settings.getWidth() / 2, (settings.getHeight() / 2) + 240);
        guiNode.attachChild(vida);
        Picture pontuacao = new Picture("HUD Picture");
        pontuacao.setImage(assetManager, "Imagens/Pontuacao.png", true);
        pontuacao.setWidth(settings.getWidth() / 35);
        pontuacao.setHeight(settings.getHeight() / 25);
        pontuacao.setPosition((settings.getWidth() / 2) + 90, (settings.getHeight() / 2) + 240);
        guiNode.attachChild(pontuacao);
    }

//Criação de alerta
    public void CriaAlerta() {
        BitmapText Alerta = new BitmapText(guiFont, false);
        Alerta.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta.setColor(ColorRGBA.Orange);
        Alerta.setText("Para Iniciar o Jogo, Clique em start...!");
        Alerta.setLocalTranslation(300, (Alerta.getLineWidth() / 5), 2);
        guiNode.attachChild(Alerta);
    }

    public void GameOver() {

    }

}
