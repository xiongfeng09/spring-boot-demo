package testSocketRpc.client;

import testSocketRpc.api.HelloService;
import testSocketRpc.proxy.MyInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class RpcClient {
    public static void main(String[] args) {
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(HelloService.class);

        HelloService h = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class<?>[]{HelloService.class}, myInvocationHandler);
        h.sayHello("123") ;
    }
}
