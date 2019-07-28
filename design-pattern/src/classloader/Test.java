package classloader;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-28 11:17
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
