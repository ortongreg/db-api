package hello;


import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testHello(){
        assert "HELLO" == new HelloWorld().hello();
    }

}