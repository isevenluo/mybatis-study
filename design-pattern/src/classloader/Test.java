package classloader;

/**
 父类静态变量、父类静态代码块（从上到下的顺序加载）
 子类静态变量、子类静态代码块（从上到下的顺序加载）
 父类的非静态变量、父类的非静态代码块（从上到下的顺序加载）
 父类的构造方法
 子类的非静态变量、子类的非静态代码块（从上到下的顺序加载）
 子类的构造方法
 */
public class Test {

    public static void main(String[] args) {
        Test1 test1 = new Test1();
//        test1.serA();
//        System.out.println(">>>> 修改之前的a = " + test1.a);
//        Test1 a = new Test1();
//        System.out.println(">>>>a = " + a.a);
//        Test1.test2 test2 = new Test1.test2("张三");
//        Test1.test2 test3 = new Test1.test2("w王五");
        System.out.println(Test1.a);
        Test1.test3 test3 = test1.new test3();

    }
}
