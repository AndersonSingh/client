package client;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Shiva on 11/23/2015.
 */
public class GameOverScreen extends BasicGameState {
    public static int userScore, opponentScore;
    private SpriteSheet spriteSheet;
    private Animation spriteAnimation;

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        spriteSheet = new SpriteSheet("images/santa.png",165,173);
        spriteAnimation = new Animation(spriteSheet,100);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.orange);
        graphics.drawString("Game is Over",280,50);
        graphics.setColor(Color.green);
        graphics.drawString("> Press 'Enter' to exit game.",50,130);
        graphics.setColor(Color.orange);
        graphics.drawString("Thanks For Playing!!",50,160);
        if(userScore>opponentScore){
            graphics.drawString("You Won!",50,260);
        }
        else if(userScore==opponentScore){
            graphics.drawString("There was a tie!",50,260);
        }
        else{
            graphics.drawString("You lost!",50,260);
        }
        spriteAnimation.draw(500, 400);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)){
            System.exit(1);
        }
    }
}
