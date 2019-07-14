package singleton;

/**
 * 双重检查枷锁
 *
 */
public class Student6 {

	private Student6() {
	}

	private volatile static Student6 student;

	public static Student6 getSingletonInstance() {
		if (student == null) {
			// B线程检测student不为空
			synchronized (Student6.class) {
				if (student == null) {
					student = new Student6();
					//A线程被指令重排，刚好先赋值，但还没有执行完构造函数
				}
			}
		}
		return student;//后边B线程执行时引发：对象商未初始化错误
	}

}
