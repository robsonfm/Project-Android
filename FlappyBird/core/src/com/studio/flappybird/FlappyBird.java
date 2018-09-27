package com.studio.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaro;
    private Texture background;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Texture gameOver;
    private Random numeroRandomico;
    private BitmapFont fonte;
    private BitmapFont mensagem;
    private Circle passaraCirculo;
    private Rectangle canoTopoRetengulo;
    private Rectangle canoBaixoRetangulo;
    //private ShapeRenderer shape;

    //Atributos de configuração
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float variacao = 0;
    private float velocidadeQueda = 0;
    private float posicaoInicialVertical;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
    private int estadoJogo = 0;
    private int pontuacao = 0;
    private boolean marcouPonto;

    //Câmera
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;
	
	@Override
	public void create () {

        batch = new SpriteBatch();
        //shape = new ShapeRenderer();

        numeroRandomico = new Random();

        passaraCirculo = new Circle();

        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);

        mensagem = new BitmapFont();
        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        passaro = new Texture[3];
        passaro[0] = new Texture("passaro1.png");
        passaro[1] = new Texture("passaro2.png");
        passaro[2] = new Texture("passaro3.png");

        canoBaixo = new Texture("cano_baixo_maior.png");
        canoTopo = new Texture("cano_topo_maior.png");

        background = new Texture("fundo.png");
        gameOver = new Texture("game_over.png");

        //Confuguração da camera
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH/2,VIRTUAL_HEIGHT/2,0);
        viewport = new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);

        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;
        posicaoInicialVertical = alturaDispositivo/2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 300;

	}

	@Override
	public void render () {

        camera.update();
        //limpar frames anteriores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        deltaTime = Gdx.graphics.getDeltaTime();
        variacao += deltaTime * 10;
        if (variacao > 2)
            variacao = 0;

        if (estadoJogo == 0){
            if(Gdx.input.justTouched()){
                estadoJogo = 1;
            }
        } else {

            velocidadeQueda++;
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                posicaoInicialVertical -= velocidadeQueda;
            }


            if(estadoJogo == 1){

                posicaoMovimentoCanoHorizontal -= deltaTime * 200;

                //Toque na tela para fazer o passaro pular
                if (Gdx.input.justTouched()) {
                    velocidadeQueda = -15;
                }
                //Verifica se o cano saiu da tela
                if (posicaoMovimentoCanoHorizontal < -canoTopo.getHeight()/4) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                    alturaEntreCanosRandomica = numeroRandomico.nextInt(600) - 300;
                    marcouPonto = false;
                }

                //Verifica pontuação
                if(posicaoMovimentoCanoHorizontal < 120){
                    if (!marcouPonto){
                        pontuacao++;
                        marcouPonto = true;
                    }
                }
            }else{//Tela de Game Over
                if (Gdx.input.justTouched()) {

                    estadoJogo = 0;
                    pontuacao = 0;
                    velocidadeQueda = 0;
                    posicaoInicialVertical = alturaDispositivo/2;
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;

                }
            }
        }

        //Configurar dados de projeçao da camera
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica);
        batch.draw(passaro[(int) variacao], 150, posicaoInicialVertical);
        fonte.draw(batch, String.valueOf(pontuacao),(larguraDispositivo+fonte.getScaleX())/2,alturaDispositivo - 50);

        if(estadoJogo == 2){
            batch.draw(gameOver,larguraDispositivo/2 - gameOver.getWidth()/2,alturaDispositivo/2 - gameOver.getHeight()/2);
            mensagem.draw(batch,"Toque na tela para recomeçar!",larguraDispositivo/2 - 290,alturaDispositivo/2-gameOver.getHeight());
        }

        batch.end();

        passaraCirculo.set(150+passaro[0].getWidth()/2,posicaoInicialVertical + passaro[0].getHeight()/2, passaro[0].getWidth()/2);
        canoBaixoRetangulo = new Rectangle(posicaoMovimentoCanoHorizontal,alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica,
                canoBaixo.getWidth(),canoBaixo.getHeight());
        canoTopoRetengulo = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica,
                canoTopo.getWidth(),canoTopo.getHeight());

        /*
        //Desenhar as formar
        shape.begin(ShapeRenderer.ShapeType.Filled );

        shape.circle(passaraCirculo.x,passaraCirculo.y,passaraCirculo.radius);
        shape.setColor(Color.RED);
        shape.rect(canoBaixoRetangulo.x,canoBaixoRetangulo.y,canoBaixoRetangulo.width,canoBaixoRetangulo.height);
        shape.rect(canoTopoRetengulo.x,canoTopoRetengulo.y,canoTopoRetengulo.width,canoTopoRetengulo.height);


        shape.end();
        */

        //Teste de colisão
        if(Intersector.overlaps(passaraCirculo,canoBaixoRetangulo) || Intersector.overlaps(passaraCirculo,canoTopoRetengulo)
            || posicaoInicialVertical <=0 || posicaoInicialVertical >= alturaDispositivo){
            estadoJogo = 2;
        }

	}

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

}
