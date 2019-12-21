package muilt2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author chenbin
 * @ClassName Client
 * @Description TODO
 * @date 2019/12/6 22:45
 * @Vsersion
 */
public class Client {

    final static String ADDERSS = "127.0.0.1";
    final static int PORT = 8765;

    public static void main(String[] args) throws Exception{
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(ADDERSS,PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("接收到客户端的请求数据...");
            String response = in.readLine();
            System.out.println("Client：" + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        in.close();
        out.close();
        socket.close();
    }
}
