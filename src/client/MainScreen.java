package client;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainScreen extends BasicGameState{
	private SpriteSheet spriteSheet;
	private Animation spriteAnimation;
	private int CURSOR_HEIGHT=130;
	private TextField username;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		spriteSheet = new SpriteSheet("images/santa.png",165,173);
		spriteAnimation = new Animation(spriteSheet,100);
	    this.username = new TextField(gc, gc.getDefaultFont(), 280, 70, 200, 20);
	    username.setBorderColor(Color.black);
        username.setBackgroundColor(Color.darkGray);
        username.setTextColor(Color.green);
        username.setAcceptingInput(true);
        username.setCursorVisible(true);
        username.setText("Player1");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_1)){
			CURSOR_HEIGHT=90;
			System.out.println("Username:"+username.getText());
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_2)){
			CURSOR_HEIGHT=110;
			System.exit(1);
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Quiz Wars", 50, 50);
		g.drawString("Enter your fucking name:", 50, 70);
		g.drawString(">", 35, CURSOR_HEIGHT);
		g.drawString("1.Start Game", 50, 130);
		g.drawString("2.Exit Game", 50, 150);
		spriteAnimation.draw(500, 400);
		username.render(gc, g);
	}

	@Override
	public int getID() {
		return 0;
	}
}