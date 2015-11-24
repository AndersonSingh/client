package client;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Shiva on 11/23/2015.
 */
public class Forfeit extends BasicGameState{
    private SpriteSheet spriteSheet;
    private Animation spriteAnimation;
    private TrueTypeFont font,font2;
    private boolean antiAlias=true;
    public static int userScore, opponentScore;
    @Override
    public int getID() {
        return 4;
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

        font.drawString(150,350,"Opponent Quit .... You have won the game!!",Color.lightGray);

        spriteAnimation.draw(500, 400);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)){
            System.exit(1);
        }
    }
}
