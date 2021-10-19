package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 여기 들어오는 애는 일단 다 여기에 매핑 됨
@WebServlet(name ="frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

//    생성자.
//    ServletApplication의 @ServletComponentScan에 등록될 때
//    컨트롤러들을 FrontController 에 등록
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
//        System.out.println(controllerMap);
    }

//    HTTP 요청을 통해, 매핑된 URL이 호출되면 서블릿 컨테이너가 이 메서드 실행
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        요청이 들어온 URI 확인
        String requestURI = request.getRequestURI();

//        요청한  URI 에 맞는 컨트롤러 호출
        ControllerV2 controller = controllerMap.get(requestURI);

//        맞는 URI 가 없는 경우 404
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

//        해당 컨트롤러에 request, response 주고 호출
//        이로 인해 각 컨트롤러를 서블릿으로 만들 필요도 없어짐(등록 할 필요 없음)
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
