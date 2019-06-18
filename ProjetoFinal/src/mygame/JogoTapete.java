package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import static org.lwjgl.opengl.Display.setFullscreen;
import sun.applet.Main;

public class JogoTapete extends SimpleApplication {

    private Pontuacao pontuacao;

    private final boolean dir = true;

    private Inimigo inimigo;
    private Area area;
    private Material esfera;
    private Material colisao;

    private BitmapText hudText;
    private BitmapText hudText1;
    private BitmapText hudText2;
    private BitmapText hudText3;
    private BitmapText hudText4;
    private BitmapText hudText5;
    private BitmapText hudText6;
    private BitmapText hudText7;
    private BitmapText hudText8;
    private BitmapText hudText9;
    private BitmapText hudText10;
    private BitmapText hudText11;
    private BitmapText hudText12;
    private BitmapText hudText13;

    private AudioNode audioFogo;
    private AudioNode audioPontoMais;
    private AudioNode audioPontoMenos;
    private AudioNode audioMorte;
    private AudioNode audioPassos;
    private AudioNode audioInicio;
    private AudioNode audioPause;
    private AudioNode audioPreInicio;
    private AudioNode Musica;

    boolean RemoveEsfera = false;
    boolean Iniciar = false;
    boolean verificaArea = false;
    boolean Aumentar = false;
    boolean Reiniciar = false;
    boolean jogoEmpausa = false;
    boolean noPausa = false;
    boolean cameraSolta = false;
    boolean gameOver = false;
    boolean audioJogando = false;
    boolean sonPre = true;
    boolean sonLigado = true;

    boolean camFrente = true;
    boolean camPorta = false;
    boolean camFogueira = false;

    int cor = 1;
    int corArea = 1;

    //Main
    public static void main(String[] args) throws IOException {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1674, 1021);
        JogoTapete app = new JogoTapete();
        app.setSettings(settings);
        app.setShowSettings(false);

        settings.setBitsPerPixel(16);
        settings.setSamples(16);
        settings.setVSync(true);
        settings.setFrequency(60);
        settings.setStencilBits(8);
        settings.setDepthBits(16);
        settings.setGammaCorrection(true);
        settings.setUseInput(true);
        settings.setUseJoysticks(true);
        settings.setEmulateMouse(true);
        settings.setEmulateMouseFlipAxis(true, true);
        settings.setAudioRenderer(AppSettings.LWJGL_OPENAL);
        settings.setTitle("Esteira de Fogo");
//        settings.setSettingsDialogImage("Imagens/Inicio.jpeg");
//        settings.setIcons(new BufferedImage[]{ImageIO.read(new File("Imagens/Coracao.png"))});
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
    }

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(10f);
        flyCam.setRotationSpeed(1);
        if (camFrente == true) {
            cam.setLocation(new Vector3f(-0.4f, 0f, 24f));
        }

        criarTapete();
        criarPortaEntrada();
        criarPortaSaida();
        CriaImagens();
        CriaAlerta();
        MeusAudios();
        sonPreInicio();

        esfera = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        pontuacao = new Pontuacao();

        hudText = new BitmapText(guiFont, false);
        hudText1 = new BitmapText(guiFont, false);
        hudText2 = new BitmapText(guiFont, false);
        hudText3 = new BitmapText(guiFont, false);
        hudText4 = new BitmapText(guiFont, false);
        hudText5 = new BitmapText(guiFont, false);
        hudText6 = new BitmapText(guiFont, false);
        hudText7 = new BitmapText(guiFont, false);
        hudText8 = new BitmapText(guiFont, false);
        hudText9 = new BitmapText(guiFont, false);
        hudText10 = new BitmapText(guiFont, false);
        hudText11 = new BitmapText(guiFont, false);
        hudText12 = new BitmapText(guiFont, false);
        hudText13 = new BitmapText(guiFont, false);

        hudText12.setSize(guiFont.getCharSet().getRenderedSize());
        hudText12.setColor(ColorRGBA.Red);
        hudText12.setLocalTranslation(70, hudText.getLineHeight() / 2 + 400, 20);
        hudText12.setText("Camera solta");
        guiNode.attachChild(hudText12);

        hudText13.setSize(guiFont.getCharSet().getRenderedSize());
        hudText13.setColor(ColorRGBA.Red);
        hudText13.setText("Son ligado");
        hudText13.setLocalTranslation(78, hudText.getLineHeight() / 2 + 380, 20);
        guiNode.attachChild(hudText13);

        inputManager.addMapping("Iniciar", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addListener(actionListener, "Iniciar");
        inputManager.addMapping("Colisao", new KeyTrigger(KeyInput.KEY_ADD));
        inputManager.addListener(actionListener, "Colisao");
        inputManager.addMapping("Retomar", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(actionListener, "Retomar");
        inputManager.addMapping("Pausa", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionListener, "Pausa");
        inputManager.addMapping("Troca", new KeyTrigger(KeyInput.KEY_NUMPAD0));
        inputManager.addListener(actionListener, "Troca");
        inputManager.addMapping("AumentaVelocidade", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addListener(actionListener, "AumentaVelocidade");
        inputManager.addMapping("Reinicia", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addListener(actionListener, "Reinicia");
        inputManager.addMapping("Mouse", new MouseButtonTrigger(0));
        inputManager.addListener(actionListener, "Mouse");
        inputManager.addMapping("MouseT", new MouseButtonTrigger(1));
        inputManager.addListener(actionListener, "MouseT");
        inputManager.addMapping("MouseA", new MouseButtonTrigger(2));
        inputManager.addListener(actionListener, "MouseA");
        inputManager.addMapping("Ler", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addListener(actionListener, "Ler");
        inputManager.addMapping("Sair", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(actionListener, "Sair");
        inputManager.addMapping("Camera", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addListener(actionListener, "Camera");
        inputManager.addMapping("CameraFixa", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(actionListener, "CameraFixa");
        inputManager.addMapping("DesligarSon", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addListener(actionListener, "DesligarSon");
        inputManager.addMapping("EscutarMusica", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addListener(actionListener, "EscutarMusica");
        inputManager.addMapping("LigarSon", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addListener(actionListener, "LigarSon");

        inputManager.addMapping("camPorta", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(actionListener, "camPorta");
        inputManager.addMapping("camFrente", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addListener(actionListener, "camFrente");
        inputManager.addMapping("camFogueira", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addListener(actionListener, "camFogueira");

        area = new Area(pontuacao.nivel, assetManager, corArea);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //Cameras e suas posicoes
        hudText5.setSize(guiFont.getCharSet().getRenderedSize());
        hudText5.setColor(ColorRGBA.Pink);
        hudText5.setText("Facens -Computacao Grafica");
        hudText5.setLocalTranslation(20, hudText.getLineHeight() / 2 + 600, 20);
        guiNode.attachChild(hudText5);

        if (Iniciar == true) {
            sonPre = false;
            audioInicio.play();
            audioPreInicio.stop();

            inimigo = new Inimigo(pontuacao.nivel, assetManager, cor);
            Spatial s = rootNode.getChild("S1");
            if (s == null) {
                rootNode.attachChild(inimigo.esfera);
            }
            rootNode.attachChild(area.tapeteArea);

            hudText.setSize(guiFont.getCharSet().getRenderedSize());
            hudText.setColor(ColorRGBA.Green);

            if (pontuacao.vida <= 0 || noPausa == true) {
                hudText.setColor(ColorRGBA.Red);
            }
            hudText.setText("Nivel: " + pontuacao.nivel);
            hudText.setLocalTranslation(765, hudText.getLineHeight() / 2 + 620, 20);
            guiNode.attachChild(hudText);

            hudText1.setSize(guiFont.getCharSet().getRenderedSize());
            hudText1.setColor(ColorRGBA.Green);
            if (pontuacao.vida <= 0 || noPausa == true) {
                hudText1.setColor(ColorRGBA.Red);
            }
            hudText1.setText("" + pontuacao.score);
            hudText1.setLocalTranslation(705, hudText1.getLineHeight() / 2 + 620, 20);
            guiNode.attachChild(hudText1);

            hudText2.setSize(guiFont.getCharSet().getRenderedSize());
            hudText2.setColor(ColorRGBA.Green);
            if (pontuacao.vida <= 0 || noPausa == true) {
                hudText2.setColor(ColorRGBA.Red);
            }
            hudText2.setText("" + pontuacao.vida);
            hudText2.setLocalTranslation(625, hudText2.getLineHeight() / 2 + 620, 20);
            guiNode.attachChild(hudText2);

            s = rootNode.getChild("S1");

            if (dir == true) {

                if (s.getLocalTranslation().x > 7) {
                    sonFogo();
                    s.removeFromParent();
                    rootNode.detachChild(s);
                    cor = ThreadLocalRandom.current().nextInt(1, 4);
                    pontuacao.vida--;
                    if (pontuacao.vida <= 0) {
                        hudText.setColor(ColorRGBA.Red);
                        hudText1.setColor(ColorRGBA.Red);
                        hudText2.setColor(ColorRGBA.Red);
                    }
                }
                //Verifica a Area de Ponto
                if ((s.getLocalTranslation().x > -1.9) && (s.getLocalTranslation().x < 1.3)) {
                    verificaArea = true;
                } else {
                    verificaArea = false;
                }
                //Remover a Bola 
                if (RemoveEsfera == true) {
                    s.removeFromParent();
                    RemoveEsfera = false;
                }
                //Verifica nivel de aumento de velocidade
                if (pontuacao.nivel < 20) {
                    s.move(tpf + pontuacao.nivel * 0.01f, 0, 0);
                } else {
                    s.move(tpf + 3.0f, 0, 0);
                }
                //Verifica quando chamar GameOver
                if (pontuacao.vida <= 0) {
                    GameOver();
                }
                if (Aumentar == true) {
                    s.move(tpf + 1.1f, 0, 0);
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

//*************************************SON*******************************************************
            //desligar son (G)           
            if (name.equals("DesligarSon") && !keyPressed) {
                hudText13.setText("Son desligado");
                silencioGeral();
                audioInicio.pause();
                sonLigado = false;
            }
            //Escuta musica de  stromae papaoutai
            if (name.equals("EscutarMusica") && !keyPressed) {
                Musica();
            }
            if (name.equals("LigarSon") && !keyPressed) {
                hudText13.setText("Son ligado");
                volumeAccoes();
                //PreInicio
                if (sonPre == true) {
                    audioPreInicio.setVolume(3);
                }
                //Em Jogo
                if (Iniciar == true) {
                    audioInicio.setVolume(3);
                }
                //Em Pause
                if (jogoEmpausa == true) {
                    audioPause.setVolume(3);
                }
                //Em GameOver
                if (gameOver == true) {
                    audioMorte.setVolume(3);
                }
                sonLigado = true;
            }
//*************************************AJUDA-(L)*******************************************************
            if (name.equals("Ler") && !keyPressed) {
                hudText6.setSize(guiFont.getCharSet().getRenderedSize());
                hudText7.setSize(guiFont.getCharSet().getRenderedSize());
                hudText8.setSize(guiFont.getCharSet().getRenderedSize());
                hudText9.setSize(guiFont.getCharSet().getRenderedSize());
                hudText10.setSize(guiFont.getCharSet().getRenderedSize());
                hudText11.setSize(guiFont.getCharSet().getRenderedSize());

                hudText6.setColor(ColorRGBA.Yellow);
                hudText7.setColor(ColorRGBA.Yellow);
                hudText8.setColor(ColorRGBA.Yellow);
                hudText9.setColor(ColorRGBA.Yellow);
                hudText10.setColor(ColorRGBA.Red);
                hudText11.setColor(ColorRGBA.White);

                hudText6.setText(">> Ao Iniciar a partida 'I', irao sair varias bolas de diferentes cores, nao deixe que nenhuma");
                hudText7.setText("delas caiam na fornalha, para isso tera de combinar a cor da bola com a cor do tapete");
                hudText8.setText("clicando no + ou mouse esquerdo, podes aumentar os passos com a no TAB");
                hudText9.setText("ou com o scroll do mouse e pode ate mudar tambem as cores do tapete com tecla '0' ou mouse direito <<");
                hudText10.setText("E  || Sair da Ajuda");
                hudText11.setText("*** Ajuda ***");

                hudText6.setLocalTranslation(375, hudText.getLineHeight() / 2 + 160, 20);
                hudText7.setLocalTranslation(380, hudText.getLineHeight() / 2 + 140, 20);
                hudText8.setLocalTranslation(381, hudText.getLineHeight() / 2 + 120, 20);
                hudText9.setLocalTranslation(330, hudText.getLineHeight() / 2 + 100, 20);
                hudText10.setLocalTranslation(620, hudText.getLineHeight() / 2 + 40, 20);
                hudText11.setLocalTranslation(630, hudText.getLineHeight() / 2 + 200, 20);

                guiNode.attachChild(hudText6);
                guiNode.attachChild(hudText7);
                guiNode.attachChild(hudText8);
                guiNode.attachChild(hudText9);
                guiNode.attachChild(hudText10);
                guiNode.attachChild(hudText11);
            }
//*************************************SAIR-(S)********************************************************

            if (name.equals("Sair") && !keyPressed) {
                hudText6.setText("");
                hudText7.setText("");
                hudText8.setText("");
                hudText9.setText("");
                hudText10.setText("");
                hudText11.setText("");
            }
//*************************************INICIAR-(I)*****************************************************

            if (name.equals("Iniciar") && !keyPressed) {
                if (jogoEmpausa != true) {
                    Iniciar = true;
                    audioJogando = true;
                }
            }
//*************************************COMBINAR-(SPACE)************************************************            

            if ((name.equals("Colisao") && !keyPressed) || ((name.equals("Mouse") && !keyPressed))) {
                if (Iniciar == true) {
                    if (verificaArea == true) {
                        if (cor == corArea) {
                            sonPontomais();
                            corArea = ThreadLocalRandom.current().nextInt(1, 4);
                            area = new Area(pontuacao.nivel, assetManager, corArea);
                            pontuacao.controlePontos(true);
                            RemoveEsfera = true;
                        } else {
                            pontuacao.controlePontos(false);
                            sonPontomenos();
                        }
                    } else {
                        pontuacao.controlePontos(false);
                        sonPontomenos();
                    }
                    RemoveEsfera = true;
                    cor = ThreadLocalRandom.current().nextInt(1, 4);
                }
            }
//*************************************PAUSA-(P)*******************************************************

            if (name.equals("Pausa") && !keyPressed) {
                if (noPausa == false && Iniciar == true) {
                    if (sonLigado == false) {
                        audioPause.setVolume(0);
                    } else {
                        audioPause.play();
                        audioInicio.setVolume(0);
                    }
                    hudText4.setSize(guiFont.getCharSet().getRenderedSize());
                    hudText4.setColor(ColorRGBA.Pink);
                    hudText4.setText("Jogo Pausado..!");
                    hudText4.setLocalTranslation(630, hudText.getLineHeight() / 2 + 350, 20);
                    guiNode.attachChild(hudText4);
                    Iniciar = false;
                    jogoEmpausa = true;
                    gameOver = false;
                }
            }
//*************************************RETOMAR-(R)*****************************************************           

            if (name.equals("Retomar") && !keyPressed) {
                if (Iniciar == false && jogoEmpausa == true) {
                    if (sonLigado == false) {
                        silencioGeral();
                    }
                    if (sonLigado == true) {
                        volumeAccoes();
                        audioPause.pause();
                        audioInicio.setVolume(3);
                    }
                    hudText4.setText("");
                    Iniciar = true;
                    jogoEmpausa = false;
                }
            }
//*************************************REINICIAR-(N)***************************************************            
            if (name.equals("Reinicia") && !keyPressed) {
                if (sonLigado == true) {
                    audioMorte.pause();
                    audioInicio.play();
                    audioInicio.setVolume(3);
                }
                if (sonLigado == false) {
                    audioMorte.pause();
                    audioInicio.setVolume(0);
                }
                hudText12.setText("Camera solta");
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
                noPausa = false;
                flyCam.setEnabled(true);
            }
//*************************************AUMENTA_PASSO-(A)***********************************************

            if ((name.equals("AumentaVelocidade") && !keyPressed) || (name.equals("MouseA"))) {
                if (Iniciar == true) {
                    sonPasso();
                    Aumentar = true;
                }
            }
//*************************************TROCA_COR-(T)***************************************************           

            if ((name.equals("Troca") && !keyPressed) || (name.equals("MouseT") && !keyPressed)) {
                if (jogoEmpausa != true) {
                    corArea += 1;
                    area.cor = corArea;
                    if (corArea == 4) {
                        corArea = 1;
                    }
                    area.trocaCor(corArea);
                }
            }
//*************************************CAMERAS_FIXA(f),(c)***************************************************           
            //camera solta (C)
            if (name.equals("Camera") && !keyPressed && camFrente == true) {
                flyCam.setEnabled(true);
                hudText12.setText("Camera solta");
                cameraSolta = true;
            }

            //camera fixa (f)
            if (name.equals("CameraFixa") && !keyPressed && camFrente == true) {
                flyCam.setEnabled(false);
                hudText12.setText("Camera fixa");
                cameraSolta = false;
            }
            //camera porta (t)
            if (name.equals("camPorta") && !keyPressed) {
                camPorta = true;
                camFogueira = false;
                camFrente = false;
                flyCam.setEnabled(false);
                hudText12.setText("Camera fixa");
                cameraSolta = true;
            }
            //camera frente(y)
            if (name.equals("camFrente") && !keyPressed) {
                camPorta = false;
                camFogueira = false;
                camFrente = true;
                flyCam.setEnabled(true);
                cam.setLocation(new Vector3f(-0.4f, 0f, 24f));
                Quaternion Frente = new Quaternion();
                Frente.fromAngleAxis(FastMath.PI, new Vector3f(0, 1, 0));
                cam.setRotation(Frente);
                cam.update();
            }
            //camera fogueira(u)
            if (name.equals("camFogueira") && !keyPressed) {
                camPorta = false;
                camFogueira = true;
                camFrente = false;
                flyCam.setEnabled(false);
                hudText12.setText("Camera fixa");
                cameraSolta = false;
            }
            if (camPorta == true) {
                Quaternion Porta = new Quaternion();
                Porta.fromAngleAxis(FastMath.PI / 2, new Vector3f(0, 1, 0));
                cam.setLocation(new Vector3f(-10.89f, -2.5f, -0.2f));
                cam.setParallelProjection(false);
                cam.setRotation(Porta);
                cam.update();
            }
            if (camFogueira == true) {
                Quaternion Fogueira = new Quaternion();
                Fogueira.fromAngleAxis(-FastMath.PI / 2, new Vector3f(0, 1, 0));
                cam.setLocation(new Vector3f(9.6f, -2.5f, -0.5f));
                cam.setParallelProjection(false);
                cam.setRotation(Fogueira);
                cam.update();
            }
        }
    };

//******************************************   ****************************************************
//                                        FUNCOES
//******************************************   ****************************************************
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
        floor1.updateGeometry(new Vector3f(-5f, -1.5f, -5f), new Vector3f(5f, -1.5f, 1.5f));
        Geometry tapete1 = new Geometry("Floor", floor1);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tap1 = assetManager.loadTexture("Textures/Barreira.jpg");
        mat1.setTexture("ColorMap", tap1);
        tapete1.setMaterial(mat1);
        tapete1.scale(2.2f, 2f, 1f);
        tapete1.move(-0.5f, -0.2f, 1.5f);
        rootNode.attachChild(tapete1);
    }

    //Criação da porta de entrada dos objetos
    public void criarPortaEntrada() {
        Box portaEntrada = new Box(1f, 1f, 1f);
        portaEntrada.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(1.5f, 3f, 4.5f));
        Geometry geom = new Geometry("Box", portaEntrada);
        geom.move(-10.5f, -3.2f, -2.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/PortaBola.jpg");
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
        portaSaida.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(2.5f, 3f, 4.5f));
        Geometry geom = new Geometry("Box", portaSaida);
        geom.move(7f, -3.2f, -2.5f);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Fogo.jpg");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);

        Box esq = new Box(1f, 1f, 1f);
        esq.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(2.5f, 3f, 1f));
        Geometry geom1 = new Geometry("Box", esq);
        geom1.move(7f, -3.2f, -3.5f);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex1 = assetManager.loadTexture("Textures/Pedra.jpg");
        mat1.setTexture("ColorMap", tex1);
        geom1.setMaterial(mat1);
        rootNode.attachChild(geom1);

        Box direita = new Box(1f, 1f, 1f);
        direita.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(2.5f, 3f, 1f));
        Geometry geom2 = new Geometry("Box", direita);
        geom2.move(7f, -3.2f, 2f);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex2 = assetManager.loadTexture("Textures/Pedra.jpg");
        mat2.setTexture("ColorMap", tex2);
        geom2.setMaterial(mat2);
        rootNode.attachChild(geom2);

        Box cima = new Box(1f, 1f, 1f);
        cima.updateGeometry(new Vector3f(0f, 0f, 0f), new Vector3f(2.5f, 0.6f, 6.5f));
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
        facens.setHeight(settings.getHeight() / 12);
        facens.setPosition((settings.getWidth() / 2 - 830) + 80, (settings.getHeight() / 2) + 100);
        guiNode.attachChild(facens);

        Picture Direcoes = new Picture("HUD Picture");
        Direcoes.setImage(assetManager, "Imagens/Direcoes.PNG", true);
        Direcoes.setWidth(settings.getWidth() / 8);
        Direcoes.setHeight(settings.getHeight() / 14);
        Direcoes.setPosition((settings.getWidth() / 2 - 900) + 80, (settings.getHeight() / 4) + 190);
        guiNode.attachChild(Direcoes);

    }

    //Criação de alerta
    public void CriaAlerta() {
        BitmapText Alerta1 = new BitmapText(guiFont, true);
        BitmapText Alerta2 = new BitmapText(guiFont, true);
        BitmapText Alerta3 = new BitmapText(guiFont, true);
        BitmapText Alerta4 = new BitmapText(guiFont, true);
        BitmapText Alerta5 = new BitmapText(guiFont, true);
        BitmapText Alerta6 = new BitmapText(guiFont, true);
        BitmapText Alerta7 = new BitmapText(guiFont, true);
        BitmapText Alerta8 = new BitmapText(guiFont, true);
        BitmapText Alerta9 = new BitmapText(guiFont, true);
        BitmapText Alerta10 = new BitmapText(guiFont, true);
        BitmapText Alerta11 = new BitmapText(guiFont, true);
        BitmapText Alerta12 = new BitmapText(guiFont, true);
        BitmapText Alerta13 = new BitmapText(guiFont, true);
        BitmapText Alerta14 = new BitmapText(guiFont, true);
        BitmapText Alerta15 = new BitmapText(guiFont, true);
        BitmapText Alerta16 = new BitmapText(guiFont, true);
        Alerta1.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta2.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta3.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta4.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta5.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta6.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta7.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta8.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta9.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta10.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta11.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta12.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta13.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta14.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta15.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta16.setSize(guiFont.getCharSet().getRenderedSize());
        Alerta1.setColor(ColorRGBA.Yellow);
        Alerta2.setColor(ColorRGBA.Cyan);
        Alerta3.setColor(ColorRGBA.Blue);
        Alerta4.setColor(ColorRGBA.Green);
        Alerta5.setColor(ColorRGBA.Magenta);
        Alerta6.setColor(ColorRGBA.Orange);
        Alerta7.setColor(ColorRGBA.White);
        Alerta8.setColor(ColorRGBA.Blue);
        Alerta9.setColor(ColorRGBA.Yellow);
        Alerta10.setColor(ColorRGBA.Orange);
        Alerta11.setColor(ColorRGBA.Magenta);
        Alerta12.setColor(ColorRGBA.Yellow);
        Alerta13.setColor(ColorRGBA.Orange);
        Alerta14.setColor(ColorRGBA.Magenta);
        Alerta15.setColor(ColorRGBA.Orange);
        Alerta16.setColor(ColorRGBA.White);
        Alerta1.setText("I  || Inicia o jogo");
        Alerta2.setText("0  || Troca cor area");
        Alerta3.setText("TAB  || Aumenta o passo");
        Alerta4.setText("P  || Pausa o jogo");
        Alerta5.setText("R  || Retoma o jogo");
        Alerta6.setText("+  || Combina as cores");
        Alerta7.setText("L  || Ajuda!");
        Alerta8.setText("N  || Reiniciar a Partida");
        Alerta9.setText("F  || Fixar a camera");
        Alerta10.setText("C  || Camera a movimentar");
        Alerta11.setText("G  || Desligar son");
        Alerta12.setText("H  || Escutar musica");
        Alerta13.setText("J  || Voltar son");
        Alerta14.setText("T  || Camera Porta ");
        Alerta15.setText("Y  || Camera Global");
        Alerta16.setText("U  || Camera Fogo");
        Alerta1.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 120), 2);
        Alerta2.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 100), 2);
        Alerta3.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 80), 2);
        Alerta4.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 60), 2);
        Alerta5.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 40), 2);
        Alerta6.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 20), 2);
        Alerta7.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 500), 2);
        Alerta8.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 520), 2);
        Alerta9.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 540), 2);
        Alerta10.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 560), 2);
        Alerta11.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 580), 2);
        Alerta12.setLocalTranslation(1125, (Alerta1.getLineWidth() / 4 + 600), 2);
        Alerta13.setLocalTranslation(1123, (Alerta1.getLineWidth() / 4 + 620), 2);
        Alerta14.setLocalTranslation(50, (Alerta1.getLineWidth() / 4 + 320), 2);
        Alerta15.setLocalTranslation(50, (Alerta1.getLineWidth() / 4 + 300), 2);
        Alerta16.setLocalTranslation(50, (Alerta1.getLineWidth() / 4 + 280), 2);
        guiNode.attachChild(Alerta1);
        guiNode.attachChild(Alerta2);
        guiNode.attachChild(Alerta3);
        guiNode.attachChild(Alerta4);
        guiNode.attachChild(Alerta5);
        guiNode.attachChild(Alerta6);
        guiNode.attachChild(Alerta7);
        guiNode.attachChild(Alerta8);
        guiNode.attachChild(Alerta9);
        guiNode.attachChild(Alerta10);
        guiNode.attachChild(Alerta11);
        guiNode.attachChild(Alerta12);
        guiNode.attachChild(Alerta13);
        guiNode.attachChild(Alerta14);
        guiNode.attachChild(Alerta15);
        guiNode.attachChild(Alerta16);
    }

    //Game Over
    public void GameOver() {
        if (sonLigado == false) {
            audioMorte.setVolume(0);
             audioInicio.stop();
        }
        if (sonLigado == true) {
            audioMorte.play();
            audioMorte.setVolume(3);
            audioInicio.stop();
        }
        hudText12.setText("Camera fixa");
        hudText3.setSize(guiFont.getCharSet().getRenderedSize());
        hudText3.setColor(ColorRGBA.Red);
        hudText3.center();
        hudText3.setText("..GAME OVER..");
        hudText3.setLocalTranslation(622, hudText.getLineHeight() / 2 + 350, 20);
        guiNode.attachChild(hudText3);

        Iniciar = false;
        noPausa = true;
        gameOver = true;
        flyCam.setEnabled(false);
    }

    //Funcao para audios
    public void MeusAudios() {
        audioFogo = new AudioNode(assetManager, "Sounds/Fogo.wav", AudioData.DataType.Buffer);
        audioPontoMais = new AudioNode(assetManager, "Sounds/Ponto.wav", AudioData.DataType.Buffer);
        audioPontoMenos = new AudioNode(assetManager, "Sounds/Falhaponto.wav", AudioData.DataType.Buffer);
        audioMorte = new AudioNode(assetManager, "Sounds/Morte.wav", AudioData.DataType.Buffer);
        audioPassos = new AudioNode(assetManager, "Sounds/Passo.wav", AudioData.DataType.Buffer);
        audioInicio = new AudioNode(assetManager, "Sounds/Inicio.wav", AudioData.DataType.Buffer);
        audioPause = new AudioNode(assetManager, "Sounds/Pausa.wav", AudioData.DataType.Buffer);
        audioPreInicio = new AudioNode(assetManager, "Sounds/PreInicio.wav", AudioData.DataType.Buffer);
        Musica = new AudioNode(assetManager, "Sounds/Musica.wav", AudioData.DataType.Stream);
    }

    private void Musica() {
        Musica.setLooping(true);
        Musica.setPositional(true);
        Musica.setLocalTranslation(Vector3f.ZERO.clone());
        Musica.setVolume(3);
        Musica.attachChild(Musica);
        Musica.play();
    }

    private void sonPreInicio() {
        audioPreInicio.setLooping(true);
        audioPreInicio.setPositional(true);
        audioPreInicio.setLocalTranslation(Vector3f.ZERO.clone());
        audioPreInicio.setVolume(3);
        audioPreInicio.attachChild(audioPreInicio);
        audioPreInicio.play();
    }

    private void sonPause() {
        audioPause.setLooping(true);
        audioPause.setPositional(true);
        audioPause.setLocalTranslation(Vector3f.ZERO.clone());
        audioPause.setVolume(3);
        rootNode.attachChild(audioPause);
        audioPause.play();
    }

    private void sonFogo() {
        audioFogo.setLooping(false);
        rootNode.attachChild(audioFogo);
        audioFogo.playInstance();
    }

    private void sonPontomais() {
        audioPontoMais.setLooping(false);
        rootNode.attachChild(audioPontoMais);
        audioPontoMais.playInstance();
    }

    private void sonPontomenos() {
        audioPontoMenos.setLooping(false);
        rootNode.attachChild(audioPontoMenos);
        audioPontoMenos.playInstance();
    }

    private void sonPasso() {
        audioPassos.setLooping(false);
        rootNode.attachChild(audioPassos);
        audioPassos.playInstance();
    }

    private void sonMorte() {
        audioMorte.setLooping(true);
        audioMorte.setPositional(true);
        audioMorte.setLocalTranslation(Vector3f.ZERO.clone());
        rootNode.attachChild(audioMorte);
        audioMorte.play();
    }

    public void silencioGeral() {
        audioFogo.setVolume(0);
        audioPassos.setVolume(0);
        audioPontoMais.setVolume(0);
        audioPontoMenos.setVolume(0);
        audioPreInicio.setVolume(0);
        audioMorte.setVolume(0);
        audioPause.setVolume(0);
        audioInicio.setVolume(0);
    }

    public void volumeAccoes() {
        audioFogo.setVolume(3);
        audioPassos.setVolume(3);
        audioPontoMais.setVolume(3);
        audioPontoMenos.setVolume(3);
    }
}
