package client;

/**
 * Created by user on 11/21/15.
 */


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;
import game.Question;


public class GameClient {

    static Client client;
    static String ip = "localhost";				// IP address of server
//    static String ip = "192.168.1.108";
    static int max_block_time = 10000000;					// maximum time client blocks in attempting to connect to server
    static int tcp_port = 8082;
    static int udp_port = 8083;
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
        kryo.register(BlockStatus.class);
        kryo.register(PlayerNames.class);
    }

    public GameClient(final GameScreen gameScreen){
        this.gameScreen=gameScreen;

        client = new Client();

        // must be done before communication with server
        register(client);

        // starts a thread the outgoing connection
        new Thread(client).start();

        client.addListener(new Listener(){
            // in case we want to perform operations as soon as connection is made (before any objects are sent across from server)
            public void connected(Connection connection){
                connection.setTimeout(10000);
            }

            // this method is called when an object is received from the server
            public void received(Connection connection, Object obj){

                // object to indicate whether or not this IP is blocked
                if(obj instanceof  BlockStatus){
                    if(((BlockStatus)obj).state){

                        client.stop();
                        System.out.println("DEBUG: Client IP is blocked.");
                    }
                    else{
                        System.out.println("DEBUG: Client IP is not blocked.");
                        // the client has connected and is not blocked so we now register the player
                        RegisterPlayer message = new RegisterPlayer();

                        message.name = gameScreen.getUsername();
                        connection.sendTCP(message);
                    }
                }

                //When a player object is received
                if(obj instanceof Player){
                    player=((Player) obj).playerId;
                    gameScreen.setPlayer(player);
                }

                //When a question object is received
				if(obj instanceof Question){
                    gameScreen.setMessage2("Waiting for your response...");
					// display question to client
                    gameScreen.setQuestion(((Question) obj).getText());
                    String[] options = ((Question) obj).getOptions();
                    gameScreen.setAnswer1(options[0]);
                    gameScreen.setAnswer2(options[1]);
                    gameScreen.setAnswer3(options[2]);
                    gameScreen.setAnswer4(options[3]);

//                  //need to implement some sort of wait until user presses enter to accept answer
                    int x;
                    while(!gameScreen.getAnswered()){
                        x=1;
                    }
                    gameScreen.setAnswered(false);

                    //Once we have exited the while loop, this means that the user has pressed enter, meaning that they selected a choice

                    QuestionResponse res = new QuestionResponse();
                    //Get answer from game screen
                    System.out.println("USER ENTERED::"+gameScreen.getUserAnswer());
                    res.answer=gameScreen.getUserAnswer();
//                  send answer to server
                    connection.sendTCP(res);
                    gameScreen.setMessage2("Answer Sent To Server");
                }


                if(obj instanceof PlayerScores){
                    // update and display scores
                    gameScreen.setMessage2("....");
                    if(player==0){
                        gameScreen.setMessage2("....");
                        gameScreen.setPlayer1Score(((PlayerScores) obj).scores[0]);
                        gameScreen.setPlayer2Score(((PlayerScores) obj).scores[1]);
                    }
                    else {
                        gameScreen.setPlayer1Score(((PlayerScores) obj).scores[1]);
                        gameScreen.setPlayer2Score(((PlayerScores) obj).scores[0]);
                    }
                }

                if(obj instanceof PlayerNames){
                    if(player==0){
                        gameScreen.setPlayer1Name(((PlayerNames) obj).names[0]);
                        gameScreen.setPlayer2Name(((PlayerNames) obj).names[1]);
                    }
                    else{
                        gameScreen.setPlayer1Name(((PlayerNames) obj).names[1]);
                        gameScreen.setPlayer2Name(((PlayerNames) obj).names[0]);
                    }

                }
                if(obj instanceof QuestionFeedback){
                    // display feedback
                    gameScreen.setMessage2(((QuestionFeedback) obj).feedback);
                }

                if(obj instanceof PlayerWait){
                    // not sure what to do here
                    gameScreen.setMessage2("Waiting on other player....");
                }

                if(obj instanceof EndGame){
                    // inform game is over
                    // display winner
                    //need to call game over screen
                    gameScreen.setMessage2("Game Over");
                    gameScreen.setGameOver(true);
                }

                if(obj instanceof Forfeit){
                    System.out.println("PLAYER WON SA!");
                    gameScreen.setMessage2("Opponent Quit!");
                    gameScreen.setOpponentForfeit(true);

                }
            }

            public void disconnected(Connection connection){
                // in case we want to run operation connection to the server is severed
            }

        });

        try{
            client.connect(max_block_time, ip, tcp_port,udp_port);
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

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

    static public class BlockStatus{
        public boolean state;
    }

    static public class PlayerNames{
        public String[] names = new String[2];
    }
}
