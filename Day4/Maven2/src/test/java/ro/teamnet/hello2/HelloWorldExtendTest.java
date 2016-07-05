package ro.teamnet.hello2;

import org.testng.annotations.Test;

/**
 * Created by user on 7/5/2016.
 */
public class HelloWorldExtendTest {
    @Test
    public void test() throws Exception {
        new HelloWorldExtend().extendSayHello();
    }

}
