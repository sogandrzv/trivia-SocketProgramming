import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable{
    private Socket clientHolder;
    private Host serverHolder;
    private String name;
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<Integer> answers = new ArrayList<>();
    private ArrayList<ArrayList<String>>options = new ArrayList<>();
    static ArrayList<Integer>scores = new ArrayList<>();
    private int score;

    public ClientManager(Host serverHolder, Socket clientHolder, String name, ArrayList<String>questions,ArrayList<Integer>answers,ArrayList<ArrayList<String>>options) {
        this.serverHolder = serverHolder;
        this.clientHolder = clientHolder;
        this.name = name;
        this.questions=questions;
        this.answers = answers;
        this.options = options;
    }

    @Override
    public void run() {
        try {
            serverHolder.addClientManager(name,this);
            DataInputStream din=new DataInputStream(clientHolder.getInputStream());
            DataOutputStream dout=new DataOutputStream(clientHolder.getOutputStream());
            //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            String str = "", str2 = "";
            for (int i = 0; i < questions.size(); i++) {
                str2 = questions.get(i)+"\n";
                for (int j = 0; j < 4; j++) {
                    str2+= options.get(i).get(j)+"\n";
                }
                dout.writeUTF(str2);
                long start=System.currentTimeMillis();
                long end=start+10*1000;
                while (System.currentTimeMillis()<end){
                    str=din.readUTF();
                }
                if (str.equals(String.valueOf(answers.get(i)))){
                   score++;
                }
                dout.writeUTF(String.valueOf(serverHolder.updateScoreBoard()));
                synchronized (serverHolder){
                    try{
                        serverHolder.wait(5000);
                    }catch (InterruptedException e){
                        e.getMessage();
                    }
                }
                dout.flush();
            }
            din.close();
            clientHolder.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int getScore() {
        return score;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

}
