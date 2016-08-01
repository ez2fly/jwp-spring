# 초기화
   - MyWebInitializer 의 onStartup()
   - 스프링 프레임워크 자체에서 WebApplicationInitializer 인터페이스를 구현한
     onStartup() 을 호출
   - web.xml 을 최대한 안쓰는 추세이다.
     초기화는 onStartup() 에서 직접 구현


# jwp-spring
Spring 프레임워크를 활용한 자바 웹 프로그래밍


# jar 내부의 클래스 정의를 확인할때 소스코드까지 보기위한 메이븐 빌드 옵션
- pom.xml 
<artifactId>maven-eclipse-plugin</artifactId>
		<version>2.9</version>
		<configuration>
			<downloadSources>true</downloadSources>
</configuration>
				
위에 <downloadSources> 값으로 jar 만 받는게 아니라 해당 source 도 받아서 정의를 확인할때 소스까지 확인할 수 있다.


# Bean 적용
jsp 에서 사용자 입력을 자바 클래스로 매칭되게 하려면 Bean 규약을 따라야 한다.
1. 파라미터가 없는 디폴트 생성자가 있어야 한다.
2. 각 필드에 대해 Getter, Setter 를 반드시 구현해야 한다.

