package br.com.ufg.ap2.pingpong.utils;


public interface Configuracoes {
    java.util.Properties props = Properties.getInstance().getProp();
    Integer limiteInferior = Integer.parseInt(props.getProperty("jogo.limites.inferior"));
    Integer limiteSuperior = Integer.parseInt(props.getProperty("jogo.limites.superior"));
    Integer limiteDireita = Integer.parseInt(props.getProperty("jogo.limites.direita"));
    Integer limiteEsquerda = Integer.parseInt(props.getProperty("jogo.limites.esquerda"));


    Integer sentidoCima = Integer.parseInt(props.getProperty("jogo.sentido.cima"));
    Integer sentidoBaixo = Integer.parseInt(props.getProperty("jogo.sentido.baixo"));
    Integer sentidoDireita = Integer.parseInt(props.getProperty("jogo.sentido.direita"));
    Integer sentidoEsquerda = Integer.parseInt(props.getProperty("jogo.sentido.esquerda"));
    Integer sentidoParada = Integer.parseInt(props.getProperty("jogo.sentido.parado"));


    Double velocidadeBolaX = Double.parseDouble(props.getProperty("jogo.velocidade.bola.x"));
    Double velocidadeBolaY = Double.parseDouble(props.getProperty("jogo.velocidade.bola.y"));

}
