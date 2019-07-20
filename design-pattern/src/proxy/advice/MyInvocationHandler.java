package proxy.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理使用的动态增强的类
 * 
 * @author think
 *
 */
public class MyInvocationHandler implements InvocationHandler {

	// 目标对象的引用
	private Object target;

	// 通过构造方法将目标对象注入到代理对象中
	public MyInvocationHandler(Object target) {
		super();
		this.target = target;
	}

	/**
	 * 代理对象会执行的方法
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("这是jdk的代理方法");
		// 下面的代码，是反射中的API用法
		// 该行代码，实际调用的是[目标对象]的方法
		// 利用反射，调用[目标对象]的方法
		Object returnValue = method.invoke(target, args);

		// 增强的部分
		return returnValue;
	}
}
