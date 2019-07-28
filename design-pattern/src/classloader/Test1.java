package classloader;


/**
 * @description:
 * @outhor: chong
 * @create: 2019-07-28 11:11
 */
public class Test1 {
    static int a = 1;
    Test1 () {
        System.out.println("Test1实例化啦");
    }

    static class test2{
        User user = new User();
        test2(String name) {
            user.setName(name);
            System.out.println("===========name" + user.hashCode());
        }
        static {
            System.out.println("家太累加载");
            System.out.println("a="+a);
        }
    }

    class test3{

        test3(){
            System.out.println("test3实例化");
        }
    }

    static {
        System.out.println("外面a= " + a);
    }
    public void serA(){
        a= 2;
    }
}
