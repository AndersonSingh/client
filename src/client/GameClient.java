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
    //static String ip = "192.254.152.10";
    static int max_block_time = 10000;					// maximum time client blocks in attempting to connect to server
    static int tcp_port = 8082;
    static int udp_port = 6789;
    BufferedReader inFromUser=null;

    // this function registers the various classes so object instances can be sent over the network
    static public void register(EndPoint endpoint){
        Kryo kryo = endpoint.getKryo();

        kryo.register(String[].class);
        kryo.register(RegisterPlayer.class);
        kryo.register(PlayerScores.class);
        kryo.register(QuestionResponse.class);
        kryo.register(QuestionFeedback.class);
        kryo.register(PlayerWait.class);
        kryo.register(EndGame.class);
        kryo.register(Question.class);

    }

    public GameClient(){
        client = new Client();
        inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // must be done before communication with server
        register(client);

        // starts a thread the outgoing connection
        new Thread(client).start();

        client.addListener(new Listener(){
            // in case we want to perform operations as soon as connection is made (before any objects are sent across from server)
            public void connected(Connection connection){

                // the client has connected to the server so we now register the player
                RegisterPlayer message = new RegisterPlayer();

                // consider whether reading for name blocks for too long
                String name = null;
    		/*
    			try{
    				name = inFromUser.readLine();
    			}
    			catch(IOException e){
    				System.out.println(e.getMessage());
    			}
    		*/
                message.name = "name";
                client.sendTCP(message);

            }

            // this method is called when an object is received from the server
            public void received(Connection connection, Object obj){

                // create and register question class

				if(obj instanceof Question){
					// display question to client
					// accept answer
					// send answer to server

                    System.out.println(((Question) obj).getText());

                    QuestionResponse res = new QuestionResponse();
                    res.answer = 4;

                    connection.sendTCP(res);
                }


                if(obj instanceof PlayerScores){
                    // update scores
                    // display new scores
                }

                if(obj instanceof QuestionFeedback){
                    // display feedback
                    System.out.println("DEBUG: " + ((QuestionFeedback) obj).feedback);
                }

                if(obj instanceof PlayerWait){
                    // not sure what to do here
                    System.out.println("DEBUG: Received a wait packet..");
                }

                if(obj instanceof EndGame){
                    // inform game is over
                    // display winner
                    System.out.println("DEBUG: End game.");
                }
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


    public static void main(String []args){

        new GameClient();
    }

    static public class PlayerScores {
        public int player1Score;
        public int player2Score;
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
}
