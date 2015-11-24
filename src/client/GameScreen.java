package client;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.*;
import java.awt.Font;

public class GameScreen extends BasicGameState{
	private int TICK_HEIGHT=55;
	private int TICK_WIDTH=360;
	private int SELECTED;
	private int player2Score;
	private int player1Score;
	private String ans1,ans2,ans3,ans4,question,IMG_LOC,message,message2,feedback;
	private boolean serverStarted,gameOver,forfeit,blocked;
	public static String player1Name,player2Name,username;
	private int player;
	GameClient gameClient;
	private TrueTypeFont font,font2;
	private boolean antiAlias=true;
	public static void setUserString(String str){
		username=str;
	}


	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Font awtFont = new Font("Cambria", Font.HANGING_BASELINE , 18);
		font = new TrueTypeFont(awtFont, antiAlias);

		Font awtFont2 = new Font("Cambria", Font.PLAIN , 18);
		font2 = new TrueTypeFont(awtFont2, antiAlias);

		SELECTED=1;

		gameOver=false;
		forfeit=false;
		serverStarted=false;
		setBlocked(false);
		setFeedback("No Feedback");
		setPlayer1Score(0);
		setPlayer2Score(0);
		setPlayer1Name("...");
		setPlayer2Name("...");
		setAnswer1("...");
		setAnswer2("...");
		setAnswer3("...");
		setAnswer4("...");
		setQuestion("...");
		setImageLocation("images/happy.png");
		setMessage("...");
		setMessage2("Waiting on other player to connect...");
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		if(gameOver){
			GameOverScreen.userScore=player1Score;
			GameOverScreen.opponentScore=player2Score;
			sbg.enterState(3, new FadeOutTransition(), new FadeInTransition());
		}

		if(blocked){
			sbg.enterState(5, new FadeOutTransition(), new FadeInTransition());
		}

		if(forfeit){
			sbg.enterState(4, new FadeOutTransition(), new FadeInTransition());
		}

		if(!serverStarted){
			serverStarted=true;
			gameClient = new GameClient(this);
		}

		if(player1Score > player2Score){
			setImageLocation("images/happy.png");
			setMessage("You are Winning!");
		}
		else if(player2Score>player1Score){
			setImageLocation("images/sad.png");
			setMessage("You are Loosing!");
		}
		else{
			setImageLocation("images/happy.png");
			setMessage("The game is even!");
		}

		if(gc.getInput().isKeyPressed(Input.KEY_1)){
			TICK_HEIGHT=55;
			TICK_WIDTH=360;
			SELECTED=1;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_3)){
			TICK_HEIGHT=55;
			TICK_WIDTH=410;
			SELECTED=3;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_2)){
			TICK_HEIGHT=555;
			TICK_WIDTH=360;
			SELECTED=2;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_4)){
			TICK_HEIGHT=555;
			TICK_WIDTH=410;
			SELECTED=4;
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			//Send response to server
			//update ui elements
			GameClient.sendUserResponse(SELECTED);
			setMessage2("Response sent to server.");
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(Color.black);
		//For persons Score
		g.setColor(Color.yellow);
		g.drawString("Your Score", 50, 30);
		g.drawRect(50, 50, 200, 50);
		g.drawString(""+player1Score+" Points", 90, 65);
		g.drawString("Player : "+player1Name, 50, 110);
		
		//For Opponents score
		g.setColor(Color.red);
		g.drawString("Opponent Score", 550, 30);
		g.drawRect(550, 50, 200, 50);
		g.drawString(""+ player2Score +" Points", 590, 65);
		g.drawString("Player : "+player2Name, 550, 110);
		
		//For the message
		font.drawString(330,10,message,Color.green);
		
		//Display smile
		g.drawImage(new Image(IMG_LOC), 330, 40);

		//Message Box at the bottom
		g.setColor(Color.cyan);
		g.drawRect(50,200,700,90);

		//The message
		font2.drawString(240,210,"Message: "+message2,Color.lightGray);
		g.setColor(Color.orange);
		font2.drawString(255,245,"Feedback: "+feedback,Color.lightGray);
		g.setColor(Color.orange);

		//For set of questions to be displayed
		g.setColor(Color.white);
		g.drawRect(50, 300, 700, 200);


		//Display Question
		g.setColor(Color.green);
		if(question.length()>15){
			font2.drawString(150,310,"Question: "+question,Color.green);
		}else{
			font2.drawString(280,310,"Question: "+question,Color.green);
		}
		
		//Display possible answers
		g.setColor(Color.orange);
		g.drawString("1:"+ans1, 90, 370);
		g.drawString("2:"+ans2, 590, 370);
		g.drawString("3:"+ans3, 90, 420);
		g.drawString("4:"+ans4, 590, 420);
		
		//Display selector for answers
		g.setColor(Color.green);
		g.drawImage(new Image("images/arrow.png"), TICK_HEIGHT, TICK_WIDTH);
		
		//Display box for selected answer
		g.setColor(Color.cyan);
		g.drawRect(50, 510, 700, 60);
		
		//Display selected answer
		g.setColor(Color.gray);
		font2.drawString(270,515,"You Selected Answer: "+SELECTED,Color.lightGray);
		font2.drawString(240,535,"Press 'ENTER' To Submit Answer",Color.lightGray);

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
	
	public void setMessage(String msg){message=msg;}

	public void setMessage2(String msg){message2=msg;}

	public void setPlayer1Score(int uScore){player1Score=uScore;}
	
	public void setPlayer2Score(int oppScore){
		player2Score =oppScore;
	}

	public void setPlayer1Name(String opp){player1Name=opp;}

	public void setPlayer2Name(String player){player2Name=player;}

	public String getUsername(){return username;}

	public void setPlayer(int player){this.player=player;}

	public void setGameOver(boolean gameState){this.gameOver=gameState;}

	public void setOpponentForfeit(boolean forfeit){	this.forfeit=forfeit;}

	public void setFeedback(String feedback){this.feedback = feedback;}

	public void setBlocked(boolean blocked){this.blocked=blocked;}
}
