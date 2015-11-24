package client;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;

/**
 * Created by Shiva on 11/23/2015.
 */
public class GameOverScreen extends BasicGameState {
    public static int userScore, opponentScore;
    private SpriteSheet spriteSheet;
    private Animation spriteAnimation;
    private TrueTypeFont font,font2;
    private boolean antiAlias=true;
    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        java.awt.Font awtFont = new java.awt.Font("Cambria", java.awt.Font.PLAIN , 28);
        font = new TrueTypeFont(awtFont, antiAlias);

        java.awt.Font awtFont2 = new java.awt.Font("Cambria", java.awt.Font.PLAIN , 20);
        font2 = new TrueTypeFont(awtFont2, antiAlias);

        spriteSheet = new SpriteSheet("images/santa.png",165,173);
        spriteAnimation = new Animation(spriteSheet,100);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        font.drawString(300,50,"Game Over!",Color.green);
        graphics.setColor(Color.orange);
        graphics.drawString("> Press 'Enter' to exit game.",50,130);

        font2.drawString(50,160,"Your Score: "+userScore,Color.green);
        font2.drawString(450,160,"Opponent Score: "+opponentScore,Color.red);

        if(userScore>opponentScore){
            font.drawString(250,420,"You Won!",Color.lightGray);
            spriteAnimation.draw(500, 400);
        }
        else if(userScore==opponentScore){
            font.drawString(250,420,"There was a tie!",Color.lightGray);
            spriteAnimation.draw(500, 400);
        }
        else{
            font.drawString(250,420,"You Lost!",Color.lightGray);
            graphics.drawImage(new Image("images/sad2.png"), 400, 300);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)){
            System.exit(1);
        }
        if(userScore<opponentScore){
            spriteAnimation.stop();
        }
    }
}
