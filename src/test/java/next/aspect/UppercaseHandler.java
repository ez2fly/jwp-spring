package next.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class UppercaseHandler implements InvocationHandler {
	Object target;

	public UppercaseHandler(Object target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// .invoke 가 실제 함수 실행과 같으므로
		// 만약, 함수의 실행시간을 구하려면 .invoke 위아래에서 시간측정하면 됨
		Object ret = method.invoke(target, args);
		if (ret instanceof String) {
			return ((String) ret).toUpperCase();
		} else {
			return ret;
		}
	}
}
