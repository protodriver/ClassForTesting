public class ToTest {
    @BeforeSuite
    public static void before() {
        System.out.println("bef method");
    }
    @AfterSuite
    public static void after() {
        System.out.println("aft method");
    }
    @Test(priory = 3)
    public static void test() {
        System.out.println("started test1");
    }
    @Test(priory = 2)
    public static void test2() {
        System.out.println("started test2");
    }
    @Test
    public static void test3() {
        System.out.println("started test3");
    }
}
