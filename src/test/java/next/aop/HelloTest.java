package next.aop;

import static org.junit.Assert.*;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class HelloTest {

	@Test
	public void hello() throws Exception {
		Hello hello = new HelloTarget();
		HelloUppercase proxy = new HelloUppercase(hello);
		System.out.println(proxy.sayHello("jea sung"));
	}
	
	@Test
	public void simpleProxy() {
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(
						getClass().getClassLoader()
						, new Class[] { Hello.class }
						, new UppercaseHandler(new HelloTarget()));
		System.out.println(proxiedHello.sayHello("test"));
	}
}
