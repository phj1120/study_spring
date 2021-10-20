package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {
//        어떻게 파라미터를 정할지는 각자가 알아서 정하는 거로
//        여기서는 /WEB-INF/views/ 와 .jsp 를 없애는거를 로직으로
        return new ModelView("new-form");
    }
}
