package client;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import java.awt.Font;

public class MainScreen extends BasicGameState{
	private SpriteSheet spriteSheet;
	private Animation spriteAnimation;
	private int CURSOR_HEIGHT=130;
	private TextField username;
	private TrueTypeFont font;
	private boolean antiAlias=true;
	private Music music;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
//		music = new Music("images/tune.ogg");

		Font awtFont = new Font("Cambria", Font.PLAIN , 28);
		font = new TrueTypeFont(awtFont, antiAlias);

	    this.username = new TextField(gc, gc.getDefaultFont(), 220, 85, 200, 20);
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
			CURSOR_HEIGHT=130;
			GameScreen.setUserString(username.getText());
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}

		if(gc.getInput().isKeyPressed(Input.KEY_2)){
			CURSOR_HEIGHT=150;
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_3)){
			CURSOR_HEIGHT=170;
			System.exit(1);
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		font.drawString(50,50,"Quiz Wars",Color.green);
		g.setColor(Color.orange);
		g.drawString("Enter your name:", 50, 85);
		g.setColor(Color.green);
		g.drawString(">", 35, CURSOR_HEIGHT);
		g.setColor(Color.lightGray);
		g.drawString("1. Start Game", 50, 130);
		g.drawString("2. Instructions",50,150);
		g.drawString("3. Exit Game", 50, 170);
//		spriteAnimation.draw(500, 400);
		username.render(gc, g);
		g.drawImage(new Image("images/quiz_night.jpg"), 300, 200);
	}

	@Override
	public int getID() {
		return 0;
	}
}