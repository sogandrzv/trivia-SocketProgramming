import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
public class Client {
    Socket clientSocket;
    int port = 9090;
    public Client() {
        try {
            clientSocket = new Socket("localhost", port);
            System.out.println("connect to server ....");
            DataInputStream din=new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dout=new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

            String str="",str2="";
            while(!str.equals("stop")){
                str2=din.readUTF();
                System.out.println("Question: "+str2);
                str=br.readLine();
                dout.writeUTF(str);
                dout.flush();
                str2=din.readUTF();
                System.out.println("Scoreboard: "+str2);
            }
        } catch (UnknownHostException e) {
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}

