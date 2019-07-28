package classloader;

public class OuterClass
{
    static{
        System.out.println("OuterClass static load.");
    }

    public OuterClass()
    {
        System.out.println("flag");
    }

    public OuterClass(String flag)
    {
        System.out.println("flag:"+flag);
    }

    class InnerClass
    {
        //private static String te = "";
//        static{
//            System.out.println("InnerClass static load.");
//        }
        // 非静态内部类不能拥有 静态变量 静态语句 静态方法
        private OuterClass out = new OuterClass("inner");
    }

    static class InnerStaticClass
    {
        private static OuterClass out = new OuterClass("innerStatic");
        static{
            System.out.println("InnerStaticClass static load.");
        }
        private static void load()
        {
            System.out.println("InnerStaticClass func load().");
        }
    }

    public static OuterClass getInstatnce()
    {
        return OuterClass.InnerStaticClass.out;
    }

    public static void main(String[] args)
    {
        System.out.println("Begin");
        OuterClass.InnerStaticClass.load();
        // 静态内部类无需外部类实例即可调用
        OuterClass out = OuterClass.InnerStaticClass.out;
        OuterClass.InnerClass innerClass = out.new InnerClass();
        // 非静态内部类需要外部类实例调用
    }

}
