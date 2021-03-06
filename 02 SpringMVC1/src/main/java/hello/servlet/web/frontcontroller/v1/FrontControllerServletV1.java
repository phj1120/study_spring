package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 여기 들어오는 애는 일단 다 여기에 매핑 됨
@WebServlet(name ="frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

//    생성자.
//    ServletApplication의 @ServletComponentScan에 등록될 때
//    컨트롤러들을 FrontController 에 등록
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
//        System.out.println(controllerMap);
    }

//    HTTP 요청을 통해, 매핑된 URL이 호출되면 서블릿 컨테이너가 이 메서드 실행
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");
//        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

//        요청이 들어온 URI 확인
        String requestURI = request.getRequestURI();

//        요청한  URI 에 맞는 컨트롤러 호출
        ControllerV1 controller = controllerMap.get(requestURI);

//        맞는 URI 가 없는 경우 404
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

//        해당 컨트롤러에 request, response 주고 호출
//        이로 인해 각 컨트롤러를 서블릿으로 만들 필요도 없어짐(등록 할 필요 없음)
        controller.process(request, response);
    }
}
