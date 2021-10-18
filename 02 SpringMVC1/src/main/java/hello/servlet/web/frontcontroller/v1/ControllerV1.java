package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV1 {

//    이거로 구현을 여러개 할거임 회원 가입, 저장, 리스트
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


}
