package client;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.*;
import java.awt.Font;

import static javafx.scene.paint.Color.YELLOWGREEN;

/**
 * Created by Shiva on 11/23/2015.
 */
public class InstructionsScreen extends BasicGameState{
    private TrueTypeFont font;
    private boolean antiAlias=true;
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Font awtFont = new Font("Cambria", Font.BOLD , 24);
        font = new TrueTypeFont(awtFont, antiAlias);
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        font.drawString(50,50,"Instructions",Color.green);

        graphics.setColor(Color.lightGray);
        graphics.drawString("> Your objective is to finish all questions in the shortest time possible!",60,100);
        graphics.drawString("> Upon Starting the game, the user is required to enter his selections [1..4].",60,130);
        graphics.drawString("> After pressing the desired number to select their answer, the user ",60,160);
        graphics.drawString("  then has to press enter to submit his/her response.",60,180);

        graphics.setColor(Color.orange);
        graphics.drawString("Press F1 to Return to Main Screen",50,210);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_F1)){
            stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
        }
    }

    public int getID() {
        return 2;
    }
}
