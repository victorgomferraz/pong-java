package br.com.ufg.ap2.pingpong.componentes;

import br.com.ufg.ap2.pingpong.utils.Configuracoes;
import jplay.Keyboard;
import jplay.Sprite;

public class Barra extends Sprite implements Configuracoes {

    private Integer sentido = sentidoParada;
    public Barra(String fileName) {
        super(fileName);
    }
    private Integer velocidade = 5;

    public void moveY(Keyboard teclado, int up, int down) {
        if (teclado.keyDown(up) && y > limiteSuperior + 5) {
            y -= velocidade;
            sentido = sentidoCima;
        } else if (teclado.keyDown(down) && y + height < limiteInferior - 5) {
            y += velocidade;
            sentido = sentidoBaixo;
        } else {
            sentido = sentidoParada;
        }

    }
    public void moveY(double pos) {
        if(pos!=y) {
            if (y>pos && y > limiteSuperior + 5) {
                y -= velocidade;
                sentido = sentidoCima;
            } else if (y<pos && y + height < limiteInferior - 5) {
                y += velocidade;
                sentido = sentidoBaixo;
            } else {
                sentido = sentidoParada;
            }
        }

    }
    public void setSentido(Integer sentido) {
        this.sentido = sentido;
    }

    public int getSentido() {
        return this.sentido;
    }
    public void centralizarNaTela() {
        this.y = (limiteInferior - height / 2) / 2;
        sentido = sentidoParada;
    }
}
