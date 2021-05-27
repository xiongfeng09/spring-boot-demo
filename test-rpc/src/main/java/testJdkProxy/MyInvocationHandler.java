package testJdkProxy;

import testSocketRpc.api.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class MyInvocationHandler implements InvocationHandler {
    private HelloService target;

    public  HelloService getInstance(HelloService target) {
        this.target = target;
        return (HelloService) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public MyInvocationHandler(HelloService target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy ......");
        return method.invoke(this.target, args);
    }
}
