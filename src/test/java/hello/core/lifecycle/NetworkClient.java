package hello.core.lifecycle;

//public class NetworkClient {

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 = " + url);
        connect();
        call("초기화 연결 메시지");

    }

    public void setUrl(String url) {
        this.url = url;
    }

    //    서비스 시작시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    //    서비스 종료시 호출
    public void disconnect() {
        System.out.println("close " + url);
    }

//    1. 인터페이스
//   @Override
////    InitializingBean
////    afterPropertiesSet : 의존관계 주입이 끝나면
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    @Override
////    DisposableBean
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

//    2. 메서드
//    BeanLifeCycleTest에서 LifeCycleConfig의 NetworkClient를 Bean으로 등록할때 설정해줌
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public void init(){
//        System.out.println("NetworkClient.init");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    public void close(){
//        System.out.println("NetworkClient.close");
//        disconnect();
//    }

//    3. 애노테이션
//    편하기도 하고 자바 표준 이라 스프링에 의존 x 그냥 이거 쓰세요
//    빈을 안쓰니까 컴포넌트 스캔할때도 잘 쓰임
//    근데 외부 라이브러리에 적용을 못함
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }
}


