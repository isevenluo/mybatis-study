package singleton;

/**
 * 
 * @author 怡吾宇
 *
 */
public class Student5 {

	private Student5() {
	}

	/*
	 * 此处使用一个内部类来维护单例 JVM在类加载的时候，是互斥的，所以可以由此保证线程安全问题
	 */
	private static class SingletonFactory {
		private static Student5 student = new Student5();
	}

	/* 获取实例 */
	public static Student5 getSingletonInstance() {
		return SingletonFactory.student;
	}

}
