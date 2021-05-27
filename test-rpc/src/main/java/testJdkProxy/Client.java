package testJdkProxy;

import testSocketRpc.api.HelloService;
import testSocketRpc.api.HelloServiceImpl;

import java.lang.reflect.Proxy;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class Client {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(helloService);

        HelloService h = (HelloService) Proxy.newProxyInstance(helloService.getClass().getClassLoader(), helloService.getClass().getInterfaces(), myInvocationHandler);
//        HelloService h = myInvocationHandler.getInstance(helloService);
        h.sayHello("123") ;
    }
}
