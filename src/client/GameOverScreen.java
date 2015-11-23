package client;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Shiva on 11/23/2015.
 */
public class GameOverScreen extends BasicGameState {
    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.orange);
        graphics.drawString("Game is Over",280,50);
        graphics.setColor(Color.green);
        graphics.drawString("> Press 'Enter' to exit game.",50,130);
        graphics.setColor(Color.orange);
        graphics.drawString("Thanks For Playing!!",50,160);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)){
            System.exit(1);
        }
    }
}
