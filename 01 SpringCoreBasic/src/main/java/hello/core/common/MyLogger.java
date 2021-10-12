package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
// 껍데기 MyLogger 를 넣고 실제 동작할 때 진짜 MyLogger 를 넣음
// class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$cf8658ee
// ac.getBean("myLogger", MyLogger.class) 해도 프록시 객체가 조회됨
// 실제 요청이 와야지만 생성되고 진짜를 찾아옴
// 그래서 프록시라고 함
// 가짜 프록시 개체는 request scope 와 관계가 없음 그냥 가짜고 내부에 단순한 위임 로직만 있고 싱글톤 처럼 동작
// 싱글톤 쓰듯이 request scope 사용 가능
// 뭘 쓰든 진짜 객체 조회를 꼭 필요한 시점 가지 지연처리 한다는 점
// 다형성과 DI 컨테이너가 가진 큰 강점임
// 싱글톤이랑 비슷하게 사용하지만 동작은 확 다르기 때문에 꼭 필요한 경우에만 사용 할 것
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}