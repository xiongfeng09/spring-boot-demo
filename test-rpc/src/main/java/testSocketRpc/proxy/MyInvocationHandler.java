package testSocketRpc.proxy;

import testSocketRpc.api.HelloService;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class MyInvocationHandler implements InvocationHandler {
    private Class target;

    public MyInvocationHandler(Class target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before");
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        Socket socket = null;
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 8088));
            output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Client 传递信息中...");

            output.writeUTF(target.getName());
            output.writeUTF(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(args);

            input = new ObjectInputStream(socket.getInputStream());
            Object o = input.readObject();
            System.out.println("result: " + o);
            return o;

        }catch (Exception e){
            System.out.println(e);
        }finally {
            if(output !=null)output.close();
            if(input != null)input.close();
            if(socket != null)socket.close();
        }
        System.out.println("invoke end");
        return null;

    }
}
