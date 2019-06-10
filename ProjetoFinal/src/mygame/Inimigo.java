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
class Inimigo {

    public int velocidade;

    public Inimigo(int nivel) {
        velocidade = 10 * nivel;
        criarInimigo();
    }

    public void criarInimigo() {
        
        
    }

}
