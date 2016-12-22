package br.com.ufg.ap2.pingpong.utils;


import br.com.ufg.ap2.pingpong.componentes.Barra;
import br.com.ufg.ap2.pingpong.componentes.Bola;

public class IABar implements Configuracoes {
    private Double goTo = -1.00;
    private int tempoReacao = 20;




    public void move(Barra bar, Bola bola) {
        double tempoX = tempoParaColisaoX(bola);
        if (tempoX < tempoReacao) {
            if (bola.getSentidoX() == Configuracoes.sentidoEsquerda) {
                double tempoParedeY = tempoParaColisaoYParede(bola);

                if (bola.getSentidoY() != Configuracoes.sentidoParada && (tempoParedeY < tempoX && tempoParedeY != -1)) {
                    goTo = (bola.y + bola.getVelocidadeY() * (tempoX - tempoParedeY));
                } else if (bola.getSentidoY() == Configuracoes.sentidoParada) {
                    goTo = bola.y;
                } else if (tempoParedeY > tempoX) {
                    if (bola.getSentidoY() == sentidoCima) {
                        goTo = (bola.y - bola.getVelocidadeY() * (tempoX));
                    } else if (bola.getSentidoY() == sentidoBaixo) {
                        goTo = (bola.y + bola.getVelocidadeY() * (tempoX));
                    }
                }

            }
        } else {

            if(bar.getSentido()==sentidoBaixo){
                goTo = (limiteInferior-30) * 1.0;
            }else{
                goTo = (limiteSuperior +30)* 1.0;
            }
            if (bar.y < limiteSuperior + 100) {
                bar.setSentido(Configuracoes.sentidoBaixo);
            } else if (bar.y > limiteInferior - 100) {
                bar.setSentido(Configuracoes.sentidoCima);
            }

        }

        if (goTo != -1) {
            bar.moveY(goTo);
        }

    }

    public int getTempoReacao() {
        return tempoReacao;
    }

    public void setTempoReacao(int tempoReacao) {
        this.tempoReacao = tempoReacao;
    }

    private double tempoParaColisaoX(Bola bola) {
        return (bola.getSentidoX() == sentidoEsquerda) ? ((bola.x - 56) / (bola.getVelocidadeX())) : ((670.5 - (bola.x - 56)) / (bola.getVelocidadeX()));
    }

    private double tempoParaColisaoYParede(Bola bola) {
        double tempo = -1;
        if (bola.getSentidoY() != sentidoParada) {
            tempo = (bola.getSentidoY() == sentidoCima) ? ((bola.y - 118) / (bola.getVelocidadeY())) : ((426 - (bola.y - 118)) / (bola.getVelocidadeY()));
        }
        return tempo;
    }


}
