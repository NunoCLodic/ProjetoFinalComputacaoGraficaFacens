/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author delga
 */
//Classe que cria e controla as ações dos objetos do cenário
public class Objeto {

    Geometry esfera;
    boolean acertou;

    Objeto() {
        Sphere s = new Sphere(20, 20, 1);
        esfera = new Geometry("S1", s);
        esfera.rotate(0, FastMath.PI, 0);
        esfera.move(-9f, -2.42f, 0f);
        //esfera.Color("Color", ColorRGBA.Red); necessário setar a cor        

    }

    //Método para controle de Pontuação, variavel será usada no método da classe Pontuação
    public void ControlePonto(JogoTapete X) {
        if (X.score == true) {
            acertou = true;
        } else {
            acertou = false;
        }
    }
    

}
