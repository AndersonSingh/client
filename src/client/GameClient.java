package client;

/**
 * Created by user on 11/21/15.
 */


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;
import java.io.*;
import game.Question;

public class GameClient {
    static Client client;
    static String ip = "localhost";				// IP address of server
//    static String ip = "192.168.1.108";
    static int max_block_time = 10000000;					// maximum time client blocks in attempting to connect to server
    static int tcp_port = 8082;
    static int udp_port = 8083;
    BufferedReader inFromUser=null;
    GameScreen gameScreen;
    int player;

    // this function registers the various classes so object instances can be sent over the network
    static public void register(EndPoint endpoint){
        Kryo kryo = endpoint.getKryo();

        kryo.register(String[].class);
        kryo.register(int[].class);
        kryo.register(RegisterPlayer.class);
        kryo.register(PlayerScores.class);
        kryo.register(QuestionResponse.class);
        kryo.register(QuestionFeedback.class);
        kryo.register(PlayerWait.class);
        kryo.register(EndGame.class);
        kryo.register(Question.class);
        kryo.register(Player.class);
        kryo.register(Forfeit.class);
    }

    public GameClient(final GameScreen gameScreen){
        this.gameScreen=gameScreen;

        client = new Client();
        inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // must be done before communication with server
        register(client);

        // starts a thread the outgoing connection
        new Thread(client).start();


//        client.start();
//        client.setKeepAliveTCP(2000);
//        client.setTimeout(20000);
        client.addListener(new Listener(){
            // in case we want to perform operations as soon as connection is made (before any objects are sent across from server)
            public void connected(Connection connection){

                // the client has connected to the server so we now register the player
                RegisterPlayer message = new RegisterPlayer();

                // consider whether reading for name blocks for too long
//                String name = gameScreen.getPlayerName();
    		/*
    			try{
    				name = inFromUser.readLine();
    			}
    			catch(IOException e){
    				System.out.println(e.getMessage());
    			}
    		*/
                message.name = gameScreen.getUsername();
                client.sendTCP(message);

            }

            // this method is called when an object is received from the server
            public void received(Connection connection, Object obj){

                if(obj instanceof Player){
                    player=((Player) obj).playerId;
                    gameScreen.setPlayer(player);
                }

                // create and register question class

				if(obj instanceof Question){
                    gameScreen.setMessage2("Waiting for your response...");
					// display question to client
                    gameScreen.setQuestion(((Question) obj).getText());
                    String[] options = ((Question) obj).getOptions();
                    gameScreen.setAnswer1(options[0]);
                    gameScreen.setAnswer2(options[1]);
                    gameScreen.setAnswer3(options[2]);
                    gameScreen.setAnswer4(options[3]);
					// accept answer
                    //need to implement some sort of wait until user presses enter
                    while(!gameScreen.getAnswered()){
                        //induces a wait
                        System.out.println("");
                    }
                    gameScreen.setAnswered(false);
                    //Once we have exited the while loop, this means that the user has pressed enter, meaning that they selected a choice
//                  System.out.println(((Question) obj).getText());

                    QuestionResponse res = new QuestionResponse();
//                  res.answer = 4;
                    //Get answer from game screen
                    System.out.println("USER ENTERED::"+gameScreen.getUserAnswer());
                    res.answer=gameScreen.getUserAnswer();
//                    res.answer=3;
//                  send answer to server
                    connection.sendTCP(res);
                    gameScreen.setMessage2("Answer Sent To Server");
                }


                if(obj instanceof PlayerScores){
                    // update scores
                    // display new scores
                    gameScreen.setMessage2("....");
                    gameScreen.setPlayer1Score(((PlayerScores) obj).scores[0]);
                    gameScreen.setPlayer2Score(((PlayerScores) obj).scores[1]);

                }

                if(obj instanceof QuestionFeedback){
                    // display feedback
                    gameScreen.setMessage2(((QuestionFeedback) obj).feedback);
                    System.out.println("DEBUG: " + ((QuestionFeedback) obj).feedback);

                }

                if(obj instanceof PlayerWait){
                    // not sure what to do here
                    gameScreen.setMessage2("Waiting on other player....");
                    System.out.println("DEBUG: Received a wait packet..");
                }

                if(obj instanceof EndGame){
                    // inform game is over
                    // display winner
                    //need to call game over screen
                    gameScreen.setMessage2("Game Over");
                    System.out.println("DEBUG: End game.");
                }
//                connection.setTimeout(1000);
                connection.setKeepAliveTCP(5);
                System.out.println("ALIVE!");
            }

            public void disconnected(Connection connection){
                // in case we want to run operation connection to the server is severed
            }

        });

        try{
            client.connect(max_block_time, ip, tcp_port);
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


//    public static void main(String []args){
//
//        new GameClient();
//    }

    static public class PlayerScores {
        public int scores[] = new int[2];
    }

    static public class QuestionResponse {
        public int answer;
    }

    static public class QuestionFeedback {
        public String feedback;
    }

    static public class PlayerWait {
        public boolean wait;
    }

    static public class EndGame {
        public boolean end;
    }

    static public class RegisterPlayer {
        public String name;
    }

    static public class Player{
        public int playerId;
    }

    static public class Forfeit{
        public boolean playerForfeit;
    }

    public void sendKeepAlive(int time) {
        client.setKeepAliveTCP(time * 1000);
    }
}
