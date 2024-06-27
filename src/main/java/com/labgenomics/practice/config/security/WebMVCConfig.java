package com.labgenomics.practice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kaeun0320
 * @apiNote : 웹 애플리케이션의 웹 설정을 정의하는 클래스. 여기서는 인터셉터, 뷰 컨트롤러, 정적 리소스 핸들러 등을 설정
 * @since 2024-06-27
 */

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMVCConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;



    /**
     *  이 배열은 정적 리소스가 위치할 수 있는 경로를 나열하고 있다. 이 경로들은 addResourceHandlers 메서드에서 사용된다.
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/static/",
            "classpath:/public/",
            "classpath:/",
            "classpath:/resources/",
            "classpath:/META-INF/resources/",
            "classpath:/META-INF/resources/webjars/"
    };


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor);
    }



    /**
     * 뷰 컨트롤러를 추가할때 사용된다. 여기서는 루트 URL("/")에 접근했을 때 "/login"으로 리다이렉트하도록 설정하고 있다.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.debug("[+] WebConfig Start !!! ");
        registry.addRedirectViewController("/", "/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }



    /**
     * 정적 리소스를 처리하는 핸들러를 추가하는데 사용된다.
     * 여기서는 모든 요청("/**")에 대해 CLASSPATH_RESOURCE_LOCATIONS에 지정된 경로에서 리소스를 찾도록 설정하고 있다.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry.addResourceHandler("/resources/**").addResourceLocations("/static/**").resourceChain(true);
    }
}
