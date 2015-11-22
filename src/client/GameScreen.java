package client;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameScreen extends BasicGameState{
	private int TICK_HEIGHT=60;
	private int TICK_WIDTH=260;
	private int SELECTED=1;
	private int OPPONENT_SCORE;
	private int MY_SCORE;
	private String ans1,ans2,ans3,ans4,question,IMG_LOC,message;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		setOpponentScore(80);
		setUserScore(80);
		//Call server
		setAnswer1("1962");
		setAnswer2("1962");
		setAnswer3("1962");
		setAnswer4("1962");
		setQuestion("When did Trinidad become an Independent Nation?");
		setImageLocation("images/happy.png");
		setMessage("The game is evenly matched!");
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		if(OPPONENT_SCORE < MY_SCORE){
			setImageLocation("images/happy.png");
			setMessage("You are Winning!");
		}
//		else if()
		
		if(gc.getInput().isKeyPressed(Input.KEY_1)){
			TICK_HEIGHT=55;
			TICK_WIDTH=260;
			SELECTED=1;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_3)){
			TICK_HEIGHT=55;
			TICK_WIDTH=310;
			SELECTED=2;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_2)){
			TICK_HEIGHT=555;
			TICK_WIDTH=260;
			SELECTED=3;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_4)){
			TICK_HEIGHT=555;
			TICK_WIDTH=310;
			SELECTED=4;
			MY_SCORE=0;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			//Send response to server
			//update ui elements
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.black);
		//For persons Score
		g.setColor(Color.yellow);
		g.drawString("Your Score", 50, 30);
		g.drawRect(50, 50, 200, 50);
		g.drawString(""+MY_SCORE+" Points", 90, 65);
		g.drawString("Player : Name", 50, 110);
		
		//For Opponents score
		g.setColor(Color.red);
		g.drawString("Opponent Score", 550, 30);
		g.drawRect(550, 50, 200, 50);
		g.drawString(""+OPPONENT_SCORE+" Points", 590, 65);
		g.drawString("Player : Name", 550, 110);
		
		//For the message
		g.setColor(Color.cyan);
		g.drawString(message, 330, 10);
		
		//Display smile
		g.drawImage(new Image(IMG_LOC), 330, 40);
		
		//For set of questions to be displayed
		g.setColor(Color.white);
		g.drawRect(50, 200, 700, 300);
		
		//Display Question
		g.setColor(Color.green);
		if(question.length()>10){
			g.drawString("Question: "+question, 150, 210);
		}else{
			g.drawString("Question: "+question, 280, 210);
		}
		
		//Display possible answers
		g.setColor(Color.white);
		g.drawString("1:"+ans1, 90, 270);
		g.drawString("2:"+ans2, 590, 270);
		g.drawString("3:"+ans3, 90, 320);
		g.drawString("4:"+ans4, 590, 320);
		
		//Display selector for answers
		g.setColor(Color.green);
		g.drawImage(new Image("images/arrow.png"), TICK_HEIGHT, TICK_WIDTH);
		
		//Display box for selected answer
		g.setColor(Color.white);
		g.drawRect(50, 400, 700, 100);
		
		//Display selected answer
		g.setColor(Color.magenta);
		g.drawString("You Selected Answer: "+SELECTED, 270, 410);
		g.drawString("Press ENTER To Submit Amswer", 240, 430);
	}

	public int getID() {
		return 1;
	}
	
	public void setAnswer1(String ans){
		ans1=ans;
	}
	
	public void setAnswer2(String ans){
		ans2=ans;
	}
	
	public void setAnswer3(String ans){
		ans3=ans;
	}
	
	public void setAnswer4(String ans){
		ans4=ans;
	}
	
	public void setQuestion(String quest){
		question=quest;
	}
	
	public void setImageLocation(String loc){
		IMG_LOC=loc;
	}
	
	public void setMessage(String msg){
		message=msg;
	}
	
	public void setUserScore(int uScore){
		MY_SCORE=uScore;
	}
	
	public void setOpponentScore(int oppScore){
		OPPONENT_SCORE=oppScore;
	}
	
	public String getUserAnswer(){
		//use the SELCTED int variable and minus 1 to we can see the access index for the question that the user selected.
		return null;
	}
}
