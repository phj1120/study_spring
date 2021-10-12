/*
 * 2021.09.20
 * 02. 싱글톤 패턴
 * */

package hello.core.singleton;

public class SingletonService {

    // 하나만 올라가도록 스태틱 영역에
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }
    
    // 사용자가 임의로 생성하는 것을 방지하기 위해 
    // 프라이빗 생성자 활용
    private SingletonService(){
        
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
