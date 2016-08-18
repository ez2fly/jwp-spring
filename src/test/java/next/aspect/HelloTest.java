package next.aspect;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

public class HelloTest {
	
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		Hello proxiedHello = (Hello) pfBean.getObject();
		System.out.println(proxiedHello.sayHello("jae sung"));
		System.out.println(proxiedHello.sayHi("joo han"));
	}

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
