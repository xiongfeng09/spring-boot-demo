package testSocketRpc.service;

import testSocketRpc.api.HelloService;
import testSocketRpc.api.HelloServiceImpl;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class ServerStart {
    public static void main(String args[]) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server server = new Server();
                    server.register(HelloService.class, HelloServiceImpl.class);
                    server.start();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
