package mygame;

//Classe que controle o sistema de Pontos, Vidas e Nível do Jogo
public class Pontuacao {

    public int score, vida, nivel;

    Pontuacao() {
        score = 0;
        vida = 5;
        nivel = 1;
    }

    //Método que recebe informação se houve colisão correta ou não e incremente ponto ou retira vida
    public void controlePontos(boolean x) {
        if (x == true) {
            score++;
            aumentaNivel();
        } else {
            vida--;
        }
    }

    //Método que aumenta o nível do jogo quando atinge certo score
    public void aumentaNivel() {
        if (score % 3 == 0) { //Aumenta a dificuldade a cada 5 pontos
            nivel++;
            aumentaVida();
        }
    }

    //Método que aumenta uma vida a cada 5 níveis atingidos
    public void aumentaVida() {
        if (nivel % 2 == 0) //Aumenta uma vida a cada 2 niveis
        {
            vida++;
        }
    }

    public void reiniciaJogo() {
        score = 0;
        vida = 5;
        nivel = 1;
    }

}
