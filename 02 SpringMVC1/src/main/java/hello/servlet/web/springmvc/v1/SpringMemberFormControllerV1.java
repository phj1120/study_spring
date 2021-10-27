package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


// 역할 1
//@Component 가 있으면 자동으로 스프링 @ComponentScan 으로 인식 됨
//근데 @Controller 에 @Component 가 있음
//고로 @Controller 만 있어도 스프링 빈에 자동으로 등록됨

// 역할 2
// @Controller 가 있으면 RequestMappingHandlerMapping 이 매핑 정보로 인식함

@Controller
//@RequestMapping
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
//    어차피 애노테이션으로 인식되므로 메서드 이름 아무렇게나 해도됨
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}