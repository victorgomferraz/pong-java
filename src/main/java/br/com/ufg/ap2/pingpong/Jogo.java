package br.com.ufg.ap2.pingpong;

import br.com.ufg.ap2.pingpong.componentes.Barra;
import br.com.ufg.ap2.pingpong.componentes.Bola;
import br.com.ufg.ap2.pingpong.utils.Configuracoes;
import br.com.ufg.ap2.pingpong.utils.IABar;
import br.com.ufg.ap2.pingpong.utils.Properties;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Sound;
import jplay.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class Jogo implements Configuracoes{
    private java.util.Properties props = Properties.getInstance().getProp();

    public Jogo() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/Pixeled.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Window janela = new Window(800, 600);
        janela.setCursor(janela.createCustomCursor(""));

        Keyboard teclado = janela.getKeyboard();
        teclado.addKey(KeyEvent.VK_S, Keyboard.DETECT_EVERY_PRESS);
        teclado.addKey(KeyEvent.VK_W, Keyboard.DETECT_EVERY_PRESS);
        GameImage fundo = new GameImage(props.getProperty("jogo.filename.background"));

        Bola bola = new Bola();
        bola.x = 300;
        bola.y = 300;
        bola.centralizarNaTela();

        Barra barraEsquerda = new Barra(props.getProperty("jogo.filename.barraEsquerda"));
        barraEsquerda.x = 40;
        barraEsquerda.y = 300;
        barraEsquerda.centralizarNaTela();

        Barra barraDireita = new Barra(props.getProperty("jogo.filename.barraDireita"));
        barraDireita.x = 745;
        barraDireita.y = 300;
        barraDireita.centralizarNaTela();


        Sound musica = new Sound(props.getProperty("jogo.sons.fundo"));
        musica.setRepeat(true);
        //musica.play();

        IABar ia = new IABar();


        int pontuacaoEsquerda = 0;
        int pontuacaoDireita = 0;
        boolean player2 = false;
        int maxPontos = 10;
        boolean executando = true;
        while(executando){

            fundo.draw();
            bola.draw();
            barraEsquerda.draw();
            barraDireita.draw();

            atualizarDadosTela(pontuacaoEsquerda,pontuacaoDireita,janela);
            checaColisaoComBarras(barraEsquerda,barraDireita,bola);

            boolean pontuou = true;
            if(bola.x<limiteEsquerda+1){
                pontuacaoDireita++;
                ia.setTempoReacao(ia.getTempoReacao()+4);
            }else if(bola.x+bola.width>limiteDireita-1){
                pontuacaoEsquerda++;
            }else{
                pontuou = false;
            }
            if (pontuou) {
                bola.setVelocidadeX(velocidadeBolaX);
                bola.setVelocidadeY(velocidadeBolaY);
                bola.centralizarNaTela();
                barraDireita.centralizarNaTela();
                barraEsquerda.centralizarNaTela();
                new Sound(props.getProperty("jogo.sons.ponto")).play();
                if(pontuacaoDireita>=maxPontos){
                    //ganhou
                    executando = false;
                    JOptionPane.showMessageDialog(janela,"Parabéns você ganhou!","Resultado",INFORMATION_MESSAGE);
                }else if(pontuacaoEsquerda>=maxPontos){
                    //perdeu
                    executando = false;
                    JOptionPane.showMessageDialog(janela,"Você perdeu","Resultado",0);
                }
            }

            bola.moveX();
            bola.moveY();
            if(player2) {
                barraEsquerda.moveY(teclado, KeyEvent.VK_W, KeyEvent.VK_S);
            }else{
                ia.move(barraEsquerda,bola);
            }
            barraDireita.moveY(teclado, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
            janela.delay(10);

            if (teclado.keyDown(Keyboard.ESCAPE_KEY)) {
                executando = false;
            }
        }

        janela.delay(500);
        if(pontuacaoEsquerda== maxPontos || pontuacaoDireita== maxPontos) {
            String nomeJogador = JOptionPane.showInputDialog("Insira seu nome");
            String msg = pontuacaoEsquerda + "X" + pontuacaoDireita;
            msg += pontuacaoDireita > pontuacaoEsquerda ? " (Ganhou)" : " (Perdeu)";
            setFileLastPlayers(nomeJogador + "/" + msg);
        }
        Placar placar =new Placar();
        placar.setVisible(true);

    }
    private void setFileLastPlayers(String line){
        String filenameTxt = "last.txt";
        String dados = line+"\n";
        Integer limite = 10;
        try {
            FileReader fileReader = new FileReader(filenameTxt);
            BufferedReader reader = new BufferedReader(fileReader);
            String row;
            Integer i = 0;
            while ((row = reader.readLine()) != null) {
                dados += row+"\n";
                if(i>limite){
                    break;
                }
                i++;
            }
            fileReader.close();
            reader.close();
            FileWriter arq = new FileWriter(filenameTxt);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf(dados);
            gravarArq.close();
            arq.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void atualizarDadosTela(Integer pontuacaoEsquerda, Integer pontuacaoDireita, Window janela){
        Font fonte = new Font("Pixeled", Font.BOLD, 24);
        janela.drawText(Integer.toString(pontuacaoEsquerda), 295,70, Color.white, fonte);
        janela.drawText(Integer.toString(pontuacaoDireita), 475, 70, Color.white, fonte);
        janela.update();
    }
    private void checaColisaoComBarras(Barra barraEsquerda, Barra barraDireita,Bola bola){
        boolean colisao = true;
        if(bola.collided(barraEsquerda)){
            bola.setSentidoX(Configuracoes.sentidoDireita);
            if(barraEsquerda.getSentido()!=sentidoParada) {
                bola.setSentidoY(barraEsquerda.getSentido());
            }
        }else if(bola.collided(barraDireita)){
            bola.setSentidoX(Configuracoes.sentidoEsquerda);
            if(barraDireita.getSentido()!=sentidoParada) {
                bola.setSentidoY(barraDireita.getSentido());
            }
        }else{
            colisao = false;
        }
        if(colisao){
            bola.setVelocidadeX(bola.getVelocidadeX()*1.1);
            bola.setVelocidadeY(bola.getVelocidadeY()*1.1);
            new Sound(props.getProperty("jogo.sons.colisao")).play();
        }
    }




}
