package client;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import static javafx.scene.paint.Color.YELLOWGREEN;

/**
 * Created by Shiva on 11/23/2015.
 */
public class InstructionsScreen extends BasicGameState{

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.orange);
        graphics.drawString("Press 1 to Return to Main Screen",50,70);
        graphics.setColor(Color.lightGray);
        graphics.drawString("Instructions",50,100);
        graphics.setColor(Color.green);
        graphics.drawString("> Your objective is to finish all questions in the shortest time possible!",60,130);
        graphics.drawString("> Upon Starting the game, the user is required to enter his selections [1..4].",60,160);
        graphics.drawString("> After pressing the desired number to select their answer, the user ",60,190);
        graphics.drawString("  then has to press enter to submit his/her response.",60,210);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_1)){
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
    }

    public int getID() {
        return 2;
    }
}
