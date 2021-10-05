package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
//@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;
//    private final MyLogger myLogger;
    /*
    스프링 컨테이너에 등록되고 받아오려 하는데 값이 없음
    얘는 생존 기간이 고객의 요청이 들어와서 나갈때 까지인데
    리퀘스트가 없으니까 당연히 내용이 없지 그래서 오류남
    Scope 'request' is not active

    Provider 를 여기서 쓰면 됩니다.
        ? 음.... 싱글톤에서 다 반납하기 위한게 Provider 아니었나..
    */

    @Autowired
    public LogDemoController(LogDemoService logDemoService, ObjectProvider<MyLogger> myLoggerProvider) {
        this.logDemoService = logDemoService;
        this.myLoggerProvider = myLoggerProvider;
    }

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testID");
        return "Ok";
    }
}