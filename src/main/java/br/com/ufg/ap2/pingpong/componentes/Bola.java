package br.com.ufg.ap2.pingpong.componentes;

import br.com.ufg.ap2.pingpong.utils.Configuracoes;
import jplay.Sound;
import jplay.Sprite;

public class Bola extends Sprite implements Configuracoes {
    int sentidoX = sentidoDireita;
    int sentidoY = sentidoParada;



    double velocidadeX = velocidadeBolaX;
    double velocidadeY = velocidadeBolaY;

    public Bola() {
        super(props.getProperty("jogo.filename.bola"), Integer.parseInt(props.getProperty("jogo.nFrames.bola")));
        setTotalDuration(210);
    }

    public void moveX() {
        if (sentidoX == sentidoDireita && x + width < limiteDireita) {
            x += velocidadeX;
        } else if (sentidoX == sentidoEsquerda && x > limiteEsquerda) {
            x -= velocidadeX;
        }
    }

    public void moveY() {
        if (sentidoY == sentidoBaixo && (y + height) < limiteInferior) {
            y += velocidadeY;
        } else if (sentidoY == sentidoCima && y > limiteSuperior) {
            y -= velocidadeY;
        } else {
            ocorreColisaoY();
        }
    }

    private void ocorreColisaoY() {
        boolean colisao = true;
        if (y + height >= limiteInferior) {
            sentidoY = sentidoCima;
        } else if (y <= limiteSuperior) {
            sentidoY = sentidoBaixo;
        } else {
            colisao = false;
        }
        if (colisao) {
            new Sound(props.getProperty("jogo.sons.colisao")).play();
        }
    }

    public void setSentidoX(int sentido) {
        this.sentidoX = sentido;
    }

    public void setSentidoY(int sentido) {
        this.sentidoY = sentido;
    }

    public int getSentidoX() {
        return sentidoX;
    }

    public int getSentidoY() {
        return sentidoY;
    }

    public double getVelocidadeX() {
        return velocidadeX;
    }

    public double getVelocidadeY() {
        return velocidadeY;
    }


    public void setVelocidadeX(double velocidadeX) {
        this.velocidadeX = velocidadeX>20.0 ? 20 : velocidadeX;
    }

    public void setVelocidadeY(double velocidadeY) {
        this.velocidadeY = velocidadeY>20.0 ? 20 : velocidadeY;
    }
    public void centralizarNaTela() {
        int largura = limiteDireita - limiteEsquerda;
        int altura = limiteInferior - limiteSuperior;

        this.x = (largura - width) / 2;
        this.y = (altura + 7 + height) / 2;
        sentidoY = sentidoParada;
    }

}
