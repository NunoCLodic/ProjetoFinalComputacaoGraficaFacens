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
import java.util.concurrent.ThreadLocalRandom;

public class JogoTapete extends SimpleApplication {

    private Pontuacao pontuacao;
    private boolean dir = true;
    private Material esfera;
    private Material colisao;

    private Inimigo inimigo;
    private Area area;
    ;

    private BitmapText hudText;
    private BitmapText hudText1;
    private BitmapText hudText2;

    boolean RemoveEsfera = false;
    boolean Iniciar = false;
    boolean verificaArea = false;
    boolean Alertas = true;
    int cor = 1;
    int corArea = 1;

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
        CriaImagens();
        CriaAlerta();

        esfera = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        pontuacao = new Pontuacao();

        pontuacao = new Pontuacao();

        hudText = new BitmapText(guiFont, false);
        hudText1 = new BitmapText(guiFont, false);
        hudText2 = new BitmapText(guiFont, false);

        inputManager.addMapping("Iniciar", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addListener(actionListener, "Iniciar");
        inputManager.addMapping("Colisao", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Colisao");
        inputManager.addMapping("GameOver", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addListener(actionListener, "GameOver");
        inputManager.addMapping("Reiniciar", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(actionListener, "Reiniciar");
        inputManager.addMapping("Pausa", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Pausa");
        inputManager.addMapping("Troca", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addListener(actionListener, "Troca");
        area = new Area(pontuacao.nivel, assetManager, corArea);

    }

    @Override
    public void simpleUpdate(float tpf) {
        if (Iniciar == true) {
            inimigo = new Inimigo(pontuacao.nivel, assetManager, cor);
            Spatial s = rootNode.getChild("S1");
            if (s == null) {
                rootNode.attachChild(inimigo.esfera);
            }

            rootNode.attachChild(area.tapeteArea);

            hudText.setSize(guiFont.getCharSet().getRenderedSize());
            hudText.setColor(ColorRGBA.Green);
            hudText.setText("Nivel: " + pontuacao.nivel);// font color
            hudText.setLocalTranslation(630, hudText.getLineHeight() / 2 + 550, 20);
            guiNode.attachChild(hudText);

            hudText1.setSize(guiFont.getCharSet().getRenderedSize());
            hudText1.setColor(ColorRGBA.Green);
            hudText1.setText("" + pontuacao.score);// font color
            hudText1.setLocalTranslation(570, hudText1.getLineHeight() / 2 + 550, 20);
            guiNode.attachChild(hudText1);

            hudText2.setSize(guiFont.getCharSet().getRenderedSize());
            hudText2.setColor(ColorRGBA.Green);
            hudText2.setText("" + pontuacao.vida);// font color
            hudText2.setLocalTranslation(480, hudText2.getLineHeight() / 2 + 550, 20);
            guiNode.attachChild(hudText2);

            s = rootNode.getChild("S1");
            if (dir == true) {

                if (s.getLocalTranslation().x > 7) {
                    s.removeFromParent();
                    rootNode.detachChild(s);
                    cor = ThreadLocalRandom.current().nextInt(1, 4);
                }

                if ((s.getLocalTranslation().x > -1.9) && (s.getLocalTranslation().x < 1.3)) {
                    verificaArea = true;
                } else {
                    verificaArea = false;

                }
                if (RemoveEsfera == true) {
                    s.removeFromParent();
                    RemoveEsfera = false;
             
                }
                if (pontuacao.nivel < 30) {
                    s.move(tpf + pontuacao.nivel * 0.001f, 0, 0);
                } else {
                    s.move(tpf + 20.0f, 0, 0);
                }
                if (pontuacao.vida == 0) {
                    GameOver();
                }
            }
        }

    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Iniciar") && !keyPressed) {
                Iniciar = true;
                Alertas = false;
            }
            if (name.equals("Colisao") && !keyPressed) {
                if (verificaArea) {
                    if (cor == corArea) {
                        corArea = ThreadLocalRandom.current().nextInt(1, 4);
                        area = new Area(pontuacao.nivel, assetManager, corArea);

                        pontuacao.controlePontos(true);
                        RemoveEsfera = true;
                    } else {
                        pontuacao.controlePontos(false);
                    }
                } else {
                    pontuacao.controlePontos(false);

                }
                RemoveEsfera = true;

                cor = ThreadLocalRandom.current().nextInt(1, 4);
            }
            if (name.equals("GameOver") && !keyPressed) {
                GameOver();
            }
            if (name.equals("Pausa") && !keyPressed) {
                Iniciar = false;
            }
            if (name.equals("Reiniciar") && !keyPressed) {
                Iniciar = true;
            }
            if (name.equals("Troca") && !keyPressed) {
                System.out.println("abertdo" + corArea);
                corArea += 1;
                area.cor = corArea;
                if (corArea == 4) {
                    corArea = 1;
                }
                area.trocaCor(corArea);
            }

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
        Alerta.setText("I-inicia o jogo"
                + "|| P-Pausa o jogo"
                + "|| K- Troca Cor Area"
                + "|| SPACE-combina cor");
        Alerta.setLocalTranslation(200, (Alerta.getLineWidth() / 10), 2);
        guiNode.attachChild(Alerta);
    }

    public void GameOver() {
        BitmapText GameOver = new BitmapText(guiFont, false);
        GameOver.setSize(guiFont.getCharSet().getRenderedSize());
        GameOver.setColor(ColorRGBA.Orange);
        GameOver.setText("Game Over");
        GameOver.setLocalTranslation(400, (GameOver.getLineWidth() / 5 + 300), 2);
        guiNode.attachChild(GameOver);
        Iniciar = false;
    }
}
