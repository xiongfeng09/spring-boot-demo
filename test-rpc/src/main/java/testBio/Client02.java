package testBio;

/**
 * @author xiong feng
 * @since 2021-05-19
 */
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

//TCP协议Socket使用BIO进行通信：客户端
public class Client02 {
    public static void main(String[] args) throws IOException {
        //创建套接字对象socket并封装ip与port
        Socket socket = new Socket("127.0.0.1", 8000);
        //根据创建的socket对象获得一个输出流
        //基于字节流
        OutputStream outputStream = socket.getOutputStream();
        //控制台输入以IO的形式发送到服务器
        System.out.println("TCP连接成功 \n请输入：");
        String str = new Scanner(System.in).nextLine();
        byte[] car = str.getBytes();
        outputStream.write(car);
        System.out.println("TCP协议的Socket发送成功");
        //刷新缓冲区
        outputStream.flush();
        //关闭连接
        socket.close();
    }
}

