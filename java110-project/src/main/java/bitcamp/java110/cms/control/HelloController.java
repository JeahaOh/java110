package bitcamp.java110.cms.control;

import java.util.Scanner;

import bitcamp.java110.cms.annotation.Component;
//  일반주석.
//@Component(value="4")
@Component("4")
public class HelloController implements Controller{

    @Override
    public void service(Scanner keyIn) {
        System.out.println("HELL");
    }

}