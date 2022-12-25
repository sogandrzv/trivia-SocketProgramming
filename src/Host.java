import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Host {
        ServerSocket host;
        int serverPort = 9090;
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<Integer> answers = new ArrayList<>();
        ArrayList<ArrayList<String>>options = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        HashMap<String, ClientManager> clientsInfo = new HashMap<>();
        ArrayList<Thread>threads = new ArrayList<>();
        public Host() {
            try {
                host = new ServerSocket(serverPort);
                System.out.println("Server Created!");
                jsonHandler();
                //TODO 0 and remove/not remove host
                int count = 1;
                while(true){
                    Socket communicate = host.accept();
                    if(names.size()>=count) {
                        Thread t = new Thread(new ClientManager(this, communicate, names.get(count),questions,answers,options));
                        scores.add(0);
                        count++;
                        threads.add(t);
                        if (threads.size() == 3) {
                            threads.get(0).start();
                            threads.get(1).start();
                            threads.get(2).start();
                        }else if(threads.size()>3){
                            t.start();
                        }
    //                    else break;
                    }
//                    host.close();
//                    communicate.close();
                }
            } catch (IOException e) {}
        }
        public static void main(String[] args) {
            new Host();
        }
        public void jsonHandler(){
            JSONReader jsonReader = new JSONReader();
            jsonReader.questionRead();
            jsonReader.userRead();
            questions = jsonReader.getQuestions();
            answers = jsonReader.getAnswers();
            options = jsonReader.getOptions();
            names = jsonReader.getNames();
        }
        public void addClientManager(String clientName, ClientManager cm){
            clientsInfo.put(clientName,cm);
        }
        public ClientManager findClientManager(String clientName){
            return clientsInfo.get(clientName);
        }
        public ArrayList<Integer> updateScoreBoard(){
            for (int i = 1; i < names.size(); i++) {
                ClientManager temp=findClientManager(names.get(i));
                scores.set(i-1,temp.getScore());
            }
            return scores;
        }
}
