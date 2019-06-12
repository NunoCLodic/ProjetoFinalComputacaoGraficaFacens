package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.ThreadLocalRandom;

public class JogoTapete extends SimpleApplication {

    private Pontuacao pontuacao;
    private final boolean dir = true;
    private Material esfera;
    private Material colisao;

    private Inimigo inimigo;
    private Area area;

    private BitmapText hudText;
    private BitmapText hudText1;
    private BitmapText hudText2;
    private BitmapText hudText3;
    private BitmapText hudText4;
    private BitmapText hudText5;

    private AudioNode audioFogo;
    private AudioNode audioPonto;
    private AudioNode audioMorte;
    private AudioNode audioPassos;
    private AudioNode audioInicio;

    boolean RemoveEsfera = false;
    boolean Iniciar = false;
    boolean verificaArea = false;
    boolean Aumentar = false;
    boolean Reiniciar = false;
    boolean jogoEmpausa = false;

    int cor = 1;
    int corArea = 1;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1200, 800);
        JogoTapete app = new JogoTapete();
        app.setSettings(settings);
        //app.setShowSettings(false);
        app.start();
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] modes = device.getDisplayModes();
        int i = 50;
        settings.setResolution(modes[i].getWidth(), modes[i].getHeight());
        settings.setFrequency(modes[i].getRefreshRate());
        settings.setBitsPerPixel(modes[i].getBitDepth());
        settings.setFullscreen(device.isFullScreenSupported());
        app.setSettings(settings);
        app.restart();
        Pontuacao pontuacao = new Pontuacao();
    }

    @Override
    public void simpleInitApp() {
        cam.setLocation(new Vector3f(-1, -1, 22));
        flyCam.setEnabled(false);

        criarTapete();
        criarPortaEntrada();
        criarPortaSaida();
        CriaImagens();
        CriaAlerta();
//        MeusAudios();

        esfera = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        pontuacao = new Pontuacao();
        pontuacao = new Pontuacao();
        hudText = new BitmapText(guiFont, false);
        hudText1 = new BitmapText(guiFont, false);
        hudText2 = new BitmapText(guiFont, false);
        hudText3 = new BitmapText(guiFont, false);
        hudText4 = new BitmapText(guiFont, false);
        hudText5 = new BitmapText(guiFont, false);
        inputManager.addMapping("Iniciar", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addListener(actionListener, "Iniciar");
        inputManager.addMapping("Colisao", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Colisao");
        inputManager.addMapping("Retomar", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(actionListener, "Retomar");
        inputManager.addMapping("Pausa", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Pausa");
        inputManager.addMapping("Troca", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(actionListener, "Troca");
        inputManager.addMapping("AumentaVelocidade", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addListener(actionListener, "AumentaVelocidade");
        inputManager.addMapping("Reinicia", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addListener(actionListener, "Reinicia");
        inputManager.addMapping("Mouse", new MouseButtonTrigger(0));
        inputManager.addListener(actionListener, "Mouse");
        inputManager.addMapping("MouseT", new MouseButtonTrigger(1));
        inputManager.addListener(actionListener, "MouseT");
        inputManager.addMapping("MouseA", new MouseButtonTrigger(2));
        inputManager.addListener(actionListener, "MouseA");
        area = new Area(pontuacao.nivel, assetManager, corArea);

//        AppSettings settings = new AppSettings(true);
//        System.out.println(settings);
    }

    @Override
    public void simpleUpdate(float tpf) {

        hudText5.setSize(guiFont.getCharSet().getRenderedSize());
        hudText5.setColor(ColorRGBA.Pink);
        hudText5.setText("Facens -Computacao Grafica");
        hudText5.setLocalTranslation(100, hudText.getLineHeight() / 2 + 600, 20);
        guiNode.attachChild(hudText5);
        
        if (Iniciar == true) {
            inimigo = new Inimigo(pontuacao.nivel, assetManager, cor);
            Spatial s = rootNode.getChild("S1");
            if (s == null) {
                rootNode.attachChild(inimigo.esfera);
            }
            rootNode.attachChild(area.tapeteArea);

            hudText.setSize(guiFont.getCharSet().getRenderedSize());
            hudText.setColor(ColorRGBA.Green);
            if (pontuacao.vida == 0) {
                hudText.setColor(ColorRGBA.Red);
            }
            hudText.setText("Nivel: " + pontuacao.nivel);
            hudText.setLocalTranslation(775, hudText.getLineHeight() / 2 + 620, 20);
            guiNode.attachChild(hudText);

            hudText1.setSize(guiFont.getCharSet().getRenderedSize());
            hudText1.setColor(ColorRGBA.Green);
            if (pontuacao.vida == 0) {
                hudText1.setColor(ColorRGBA.Red);
            }
            hudText1.setText("" + pontuacao.score);
            hudText1.setLocalTranslation(705, hudText1.getLineHeight() / 2 + 620, 20);
            guiNode.attachChild(hudText1);

            hudText2.setSize(guiFont.getCharSet().getRenderedSize());
            hudText2.setColor(ColorRGBA.Green);
            if (pontuacao.vida == 0) {
                hudText2.setColor(ColorRGBA.Red);
            }
            hudText2.setText("" + pontuacao.vida);
            hudText2.setLocalTranslation(625, hudText2.getLineHeight() / 2 + 620, 20);
            guiNode.attachChild(hudText2);

            s = rootNode.getChild("S1");

            if (dir == true) {

                if (s.getLocalTranslation().x > 7) {
//                    audioFogo.playInstance();
                    s.removeFromParent();
                    rootNode.detachChild(s);
                    cor = ThreadLocalRandom.current().nextInt(1, 4);
                    pontuacao.vida--;
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
                    s.move(tpf + pontuacao.nivel * 0.01f, 0, 0);
                } else {
                    s.move(tpf + 3.0f, 0, 0);
                }
                if (pontuacao.vida == 0) {
                    if (Iniciar == true) {
                        GameOver();
                    }
                }
                if (Aumentar == true) {
                    s.move(tpf + 1.0f, 0, 0);
                    Aumentar = false;
                }
            }
        }

        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Iniciar") && !keyPressed) {
                Iniciar = true;
            }
            if ((name.equals("Colisao") && !keyPressed) || ((name.equals("Mouse") && !keyPressed))) {
                if (verificaArea) {
                    if (cor == corArea) {
//                        audioPonto.playInstance();
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

            if (name.equals("Pausa") && !keyPressed) {
                hudText4.setSize(guiFont.getCharSet().getRenderedSize());
                hudText4.setColor(ColorRGBA.Pink);
                hudText4.setText("Jogo Pausado..!");
                hudText4.setLocalTranslation(652, hudText.getLineHeight() / 2 + 350, 20);
                guiNode.attachChild(hudText4);
                Iniciar = false;
                jogoEmpausa = true;
            }
            if (name.equals("Retomar") && !keyPressed) {
                if (Iniciar == false) {
                    hudText4.setText("");
                    hudText4.setLocalTranslation(652, hudText.getLineHeight() / 2 + 350, 20);
                    guiNode.attachChild(hudText4);
                    Iniciar = true;
                }
            }
            if (name.equals("Reinicia") && !keyPressed) {
                Reiniciar();
            }

            if ((name.equals("AumentaVelocidade") && !keyPressed) || (name.equals("MouseA"))) {
                //audioPassos.playInstance();
                Aumentar = true;
            }
            if ((name.equals("Troca")) || (name.equals("MouseT") && !keyPressed)) {
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

        Box portaEntradaBola = new Box(1f, 1f, 1f);
        portaEntradaBola.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 2.81f, 1.9f));
        Geometry geomBola = new Geometry("Box", portaEntradaBola);
        geomBola.move(-9.9f, -3.2f, -1.2f);
        Material matBola = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBola.setColor("Color", ColorRGBA.Black);
        geomBola.setMaterial(matBola);
        rootNode.attachChild(geomBola);
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
        Box direita = new Box(1f, 1f, 1f);
        direita.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1f, 3f, 1f));
        Geometry geom2 = new Geometry("Box", direita);
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
        vida.setWidth(settings.getWidth() / 70);
        vida.setHeight(settings.getHeight() / 50);
        vida.setPosition(settings.getWidth() / 2 - 240, (settings.getHeight() / 2) + 100);
        guiNode.attachChild(vida);

        Picture pontos = new Picture("HUD Picture");
        pontos.setImage(assetManager, "Imagens/Pontuacao.png", true);
        pontos.setWidth(settings.getWidth() / 70);
        pontos.setHeight(settings.getHeight() / 50);
        pontos.setPosition((settings.getWidth() / 2 - 240) + 80, (settings.getHeight() / 2) + 100);
        guiNode.attachChild(pontos);

        Picture facens = new Picture("HUD Picture");
        facens.setImage(assetManager, "Imagens/facens.png", true);
        facens.setWidth(settings.getWidth() / 20);
        facens.setHeight(settings.getHeight() / 15);
        facens.setPosition((settings.getWidth() / 2 - 750) + 80, (settings.getHeight() / 2) + 100);
        guiNode.attachChild(facens);

    }

//Criação de alerta
    public void CriaAlerta() {
        BitmapText Alerta1 = new BitmapText(guiFont, true);
        BitmapText Alerta2 = new BitmapText(guiFont, true);
        BitmapText Alerta3 = new BitmapText(guiFont, true);
        BitmapText Alerta4 = new BitmapText(guiFont, true);
        BitmapText Alerta5 = new BitmapText(guiFont, true);
        BitmapText Alerta6 = new BitmapText(guiFont, true);
        Alerta1.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta2.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta3.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta4.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta5.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta6.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta1.setColor(ColorRGBA.Yellow);
        Alerta2.setColor(ColorRGBA.Cyan);
        Alerta3.setColor(ColorRGBA.Blue);
        Alerta4.setColor(ColorRGBA.Green);
        Alerta5.setColor(ColorRGBA.Magenta);
        Alerta6.setColor(ColorRGBA.Orange);
        Alerta1.setText("I - Inicia o jogo.");
        Alerta2.setText("T - Troca Cor Area.");
        Alerta3.setText("A - Aumenta passos.");
        Alerta4.setText("P - Pausa o jogo.");
        Alerta5.setText("R - Retoma o jogo.");
        Alerta6.setText("SPACE - Combina as cores.");
        Alerta1.setLocalTranslation(1100, (Alerta1.getLineWidth() / 4 + 120), 2);
        Alerta2.setLocalTranslation(1100, (Alerta1.getLineWidth() / 4 + 100), 2);
        Alerta3.setLocalTranslation(1100, (Alerta1.getLineWidth() / 4 + 80), 2);
        Alerta4.setLocalTranslation(1100, (Alerta1.getLineWidth() / 4 + 60), 2);
        Alerta5.setLocalTranslation(1100, (Alerta1.getLineWidth() / 4 + 40), 2);
        Alerta6.setLocalTranslation(1100, (Alerta1.getLineWidth() / 4 + 20), 2);
        guiNode.attachChild(Alerta1);
        guiNode.attachChild(Alerta2);
        guiNode.attachChild(Alerta3);
        guiNode.attachChild(Alerta4);
        guiNode.attachChild(Alerta5);
        guiNode.attachChild(Alerta6);
    }

//Game Over
    public void GameOver() {
        hudText3.setSize(guiFont.getCharSet().getRenderedSize());
        hudText3.setColor(ColorRGBA.Red);
        hudText3.center();
        hudText3.setText("GAME OVER");
        hudText3.setLocalTranslation(655, hudText.getLineHeight() / 2 + 350, 20);
        guiNode.attachChild(hudText3);
//        audioMorte.playInstance();

        Iniciar = false;
    }
//Reinicia a partida

    public void Reiniciar() {
        hudText3.setSize(guiFont.getCharSet().getRenderedSize());
        hudText3.setColor(ColorRGBA.Red);
        hudText3.setText("");
        hudText3.setLocalTranslation(655, hudText.getLineHeight() / 2 + 350, 20);
        guiNode.attachChild(hudText3);

        hudText4.setSize(guiFont.getCharSet().getRenderedSize());
        hudText4.setColor(ColorRGBA.Pink);
        hudText4.setText("");
        hudText4.setLocalTranslation(655, hudText.getLineHeight() / 2 + 350, 20);
        guiNode.attachChild(hudText4);

        pontuacao.reiniciaJogo();
        Iniciar = true;
    }

    //Funcao para audios
    public void MeusAudios() {
        audioFogo = new AudioNode(assetManager, "Sounds/Fogo.wav", AudioData.DataType.Buffer);
        audioFogo.setLooping(false);
        audioFogo.setVolume(2);
        rootNode.attachChild(audioFogo);

        audioPonto = new AudioNode(assetManager, "Sounds/Ponto.wav", AudioData.DataType.Buffer);
        audioPonto.setLooping(false);
        audioPonto.setVolume(2);
        rootNode.attachChild(audioPonto);

        audioMorte = new AudioNode(assetManager, "Sounds/Morte.wav", AudioData.DataType.Buffer);
        audioMorte.setLooping(false);
        audioMorte.setVolume(2);
        rootNode.attachChild(audioMorte);

        audioPassos = new AudioNode(assetManager, "Sounds/Passo.wav", AudioData.DataType.Buffer);
        audioPassos.setLooping(false);
        audioPassos.setVolume(2);
        rootNode.attachChild(audioPassos);

        audioInicio = new AudioNode(assetManager, "Sounds/Inicio.wav", AudioData.DataType.Buffer);
        audioInicio.setLooping(true);
        audioInicio.setPositional(true);
        audioInicio.setLocalTranslation(Vector3f.ZERO.clone());
        audioInicio.setVolume(3);
        rootNode.attachChild(audioInicio);
        audioInicio.play();
    }

}
