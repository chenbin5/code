package socket;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chenbin
 * @ClassName Server
 * @Description TODO
 * @date 2019/12/6 22:39
 * @Vsersion
 */
public class Server {

    final static int PORT = 8765;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("server start...");
            Socket socket = serverSocket.accept();
            new Thread(new ServerHandler(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
