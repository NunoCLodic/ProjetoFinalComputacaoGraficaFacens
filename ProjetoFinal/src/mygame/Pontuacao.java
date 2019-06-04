package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import java.util.Random;

//Classe que controle o sistema de Pontos, Vidas e Nível do Jogo
public class Pontuacao {

    int score, vida, nivel;

    Pontuacao() {
        score = 0;
        vida = 3;
        nivel = 1;

    }

    //Método que recebe informação se houve colisão correta ou não e incremente ponto ou retira vida
    public void ControlePontos(Objeto x) {
        if (x.acertou == true) {
            score++;
            aumentaNivel();
        } else {
            vida--;
        }

    }

    //Método que aumenta o nível do jogo quando atinge certo score
    public void aumentaNivel() {
        if (score % 5 == 0) { //Aumenta a dificuldade a cada 5 pontos
            nivel++;
            aumentaVida();
            //Chamar método que aumenta nível

        }
    }

    //Método que aumenta uma vida a cada 5 níveis atingidos
    public void aumentaVida() {
        if (nivel % 5 == 0) //Aumenta uma vida a cada 5 niveis
            vida++;
        
    }

}
