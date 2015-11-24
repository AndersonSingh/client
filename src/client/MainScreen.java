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
	private int CURSOR_HEIGHT;
	private TextField username,ipAddress;
	private TrueTypeFont font;
	private boolean antiAlias=true;
	private Music music;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
//		music = new Music("images/tune.ogg");

		Font awtFont = new Font("Cambria", Font.PLAIN , 28);
		font = new TrueTypeFont(awtFont, antiAlias);
		CURSOR_HEIGHT=150;
	    this.username = new TextField(gc, gc.getDefaultFont(), 200, 85, 200, 20);
	    username.setBorderColor(Color.black);
        username.setBackgroundColor(Color.darkGray);
        username.setTextColor(Color.green);
        username.setAcceptingInput(true);
        username.setCursorVisible(true);
        username.setText("Player1");

		this.ipAddress = new TextField(gc, gc.getDefaultFont(), 270, 105, 200, 20);
		ipAddress.setBorderColor(Color.black);
		ipAddress.setBackgroundColor(Color.darkGray);
		ipAddress.setTextColor(Color.green);
		ipAddress.setAcceptingInput(true);
		ipAddress.setCursorVisible(true);
		ipAddress.setText("IPAddress");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_F1)){
			CURSOR_HEIGHT=150;
			GameScreen.setUserString(username.getText());
			if(ipAddress.getText().isEmpty() || ipAddress.getText().equals("IPAddress")){
				GameClient.setIPAddress("localhost");
			}
			else {
				GameClient.setIPAddress(ipAddress.getText());
			}
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}

		if(gc.getInput().isKeyPressed(Input.KEY_F2)){
			CURSOR_HEIGHT=170;
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_F3)){
			CURSOR_HEIGHT=190;
			System.exit(1);
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		font.drawString(50,50,"Quiz Wars",Color.green);
		g.setColor(Color.orange);
		g.drawString("Enter your name:", 50, 85);
		g.drawString("Enter Sever IP Address:", 50, 105);
		g.setColor(Color.green);
		g.drawString(">", 35, CURSOR_HEIGHT);
		g.setColor(Color.lightGray);
		g.drawString("F1. Start Game", 50, 150);
		g.drawString("F2. Instructions",50,170);
		g.drawString("F3. Exit Game", 50, 190);
//		spriteAnimation.draw(500, 400);
		username.render(gc, g);
		ipAddress.render(gc, g);
		g.drawImage(new Image("images/quiz_night.jpg"), 300, 200);
	}

	@Override
	public int getID() {
		return 0;
	}
}