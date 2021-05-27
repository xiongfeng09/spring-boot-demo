package testSocketRpc.service;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class Server {
    private static final HashMap<String, Class> serviceRegistry = new HashMap<String, Class>();

    public void start() throws Exception {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(8088));
        System.out.println("Server 等待客服端的链接...");
        try {
            while (true) {
                ServerSocketRunnable serverSocket = new ServerSocketRunnable(server.accept());
                serverSocket.run();
            }
        }finally {
            server.close();
        }
    }
    public void register(Class serviceInterface, Class impl) {
        System.out.println("注册"+serviceInterface.getName());
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    public class ServerSocketRunnable implements Runnable {

        private Socket client;

        public ServerSocketRunnable(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            System.out.println("Server start...");
            ObjectInputStream objectInputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                /**
                 * 获得类名(className)以及方法名(methodName)，通过类名和方法我们可以得到一个method，想要执行这个method还要有调用method参数值。
                 */
                objectInputStream = new ObjectInputStream(client.getInputStream());
                System.out.println("Server 等待接受...");
                String className = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
                Object[] arguments = (Object[]) objectInputStream.readObject();

                //根据名字把实现接口的方法类返回回去
                System.out.println("Server 收到"+className);
                Class serverClass = serviceRegistry.get(className);
                Method method = serverClass.getMethod(methodName,parameterTypes);
                Object result = method.invoke(serverClass.newInstance(),arguments);
                //给client的响应
                System.out.println("Server 回应消息...");
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(result);

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
