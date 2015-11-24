package client;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Shiva on 11/24/2015.
 */
public class BlockedScreen extends BasicGameState {
    private TrueTypeFont font,font2;
    private boolean antiAlias=true;
    @Override
    public int getID() {
        return 5;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        java.awt.Font awtFont = new java.awt.Font("Cambria", java.awt.Font.PLAIN , 28);
        font = new TrueTypeFont(awtFont, antiAlias);

        java.awt.Font awtFont2 = new java.awt.Font("Cambria", java.awt.Font.PLAIN , 20);
        font2 = new TrueTypeFont(awtFont2, antiAlias);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        font.drawString(350,50,"ERROR", Color.red);
        graphics.setColor(Color.orange);
        graphics.drawString("> Press 'Enter' to exit game.",50,130);
        font2.drawString(250,350,"ERROR: 001 - IP Address is Blocked!",Color.lightGray);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)){
            System.exit(1);
        }
    }
}
