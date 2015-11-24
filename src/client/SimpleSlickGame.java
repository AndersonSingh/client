package client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SimpleSlickGame extends StateBasedGame{
	public SimpleSlickGame(String name) {
		super(name);
	}

	public static void main(String args[]){
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new SimpleSlickGame("Quiz Wars"));
			appgc.setDisplayMode(800, 600, false);
			appgc.setAlwaysRender(true);
			appgc.setTargetFrameRate(60);
			appgc.start();
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MainScreen());
		this.addState(new GameScreen());
		this.addState(new InstructionsScreen());
		this.addState(new GameOverScreen());
		this.addState(new Forfeit());
		this.addState(new BlockedScreen());
	}
}
