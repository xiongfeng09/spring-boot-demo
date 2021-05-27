package testSocketRpc.api;

/**
 * @author xiong feng
 * @since 2021-05-18
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String username) {
        System.out.println("username " + username);
        return "hello " + username;
    }
}
