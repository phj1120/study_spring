package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 컴포넌트 스캔으로 싹 뒤져서 등록하는데 뺄걸 지정해주는거
        // AppConfig 는 수동으로 등록해줘야지 자동으로 등록되면 안됨
        // -> ? AppConfig, TestConfig 등등 @Configuration 의 @Bean 들이 등록 되므로 안되게 해주는 거임
        // @Configuration 을 안지우고 하려고 이렇게 함 실무에선 안합니당
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),

        // 다 뒤지는데 오래걸려서 어디서부터 찾는지 지정 가능
        // 지정 안하면 @ComponentScan이 있는 클래스 패키지가 시작 위치
        // -> 관례상 설정 정보 클래스를 프로젝트 최상단에 둠
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class
)
public class AutoAppConfig {
    
//    빈으로 등록할 경우 스프링 부트에서 오류 발생 -> 동일한 이름의 빈이 이미 등록되어있다고
//    코드 많이 꼬여서 스프링 부트에서는 아예 오류 처리 내버림
//    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
