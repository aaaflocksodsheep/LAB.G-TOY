package com.labgenomics.practice.config.datasource;


import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Getter
@Setter
@Configuration

//  ---------- Lombok 라이브러리 어노테이션 ------------
@RequiredArgsConstructor //  초기 상태를 위한 생성자 설정, ex) this.xx

// MyBatis 매퍼 인터페이스를 자동으로 스캔하여 Spring 빈으로 등록
// MapperScan : MyBatis 프레임워크에서 제공하는 어노테이션
// basePackages : MyBatis 매퍼 인터페이스 스캔, sqlSessionFactoryRef : 매퍼 인터페이스가 사용할 빈 지정, 뒤가 변수
@MapperScan(basePackages = "com.labgenomics.practice.*.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBean")

@Slf4j // 클래스를 위한 Logger 인스턴스 자동 생성

public class MybatisConfig {

	private final DataSourceConfig dataSourceConfig; // final > 이 필드는 한 번 초기화된 후에는 변경 X, 타입 / 변수
	private static String mapperLocationsPath = "classpath*:mapper/**/*.xml"; // MyBatis 매퍼 파일의 경로
	
	@PostConstruct // 빈(bean)이 초기화된 후에 메서드 실행
	public void init() {
		// url, 이름, 비번, 드라이브 log 기록
		log.info("DW_LABG Databases initialized.... {}, {}, {}, {}", dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword(), dataSourceConfig.getDriver());
	}

	@Bean // Spring 컨테이너에 의해 관리되는 빈(Bean) 객체를 생성하고 반환
	public DataSource dataSource()  { // 반환 타입 DataSource

		// HikariDataSource 객체를 생성, ds 변수에 할당
		// HikariDataSource : HikariCP 고성능 JDBC 커넥션 풀 라이브러리 구현체 ???
		HikariDataSource ds = new HikariDataSource();

		// dataSourceConfig 객체에서 각 클래스를 가져와 HikariDataSource 드라이버 클래스 이름을 설정
		ds.setDriverClassName(dataSourceConfig.getDriver()); // DriverClassName
		ds.setJdbcUrl(dataSourceConfig.getUrl()); // JdbcUrl
		ds.setUsername(dataSourceConfig.getUsername()); // Username
		ds.setPassword(dataSourceConfig.getPassword()); // Password
		// 즉 HikariDataSource (ds) 설정하여 데이터 소스를 생성, 이를 Spring 컨텍스트에 빈으로 등록??
		// 설정된 데이터 소스는 데이터베이스 연결을 관리하는데 사용 ?

		ds.setMaximumPoolSize(30); // HikariDataSource 객체 ds의 동시에 접속할 수 있는 커넥션 풀 최대 크기를 30으로 설정
		ds.setConnectionTimeout(30000); // 타임아웃을 30,000밀리초(30초)로 설정, 커넥션을 얻기 위해 대기할 수 있는 최대 시간 ?
		ds.setValidationTimeout(30000); // 커넥션이 유효한지 검사하는 데 걸리는 최대 시간
		ds.setMaxLifetime(30000); // 커넥션이 풀에 유지될 수 있는 최대 시간 > 오바되면 새 커넥션?
		return ds;
	}
	
	@Bean
	// SqlSessionFactoryBean 객체 생성 및 설정, 데이터 소스, 매퍼 파일의 위치, MyBatis의 세부 설정 등을 설정
	// SqlSessionFactoryBean 객체를 생성하여 반환 , 예외 지정
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {

		// MyBatis Configuration 객체 생성, MyBatis 세부 설정을 담당
		// org.apache.ibatis.session 패키지의 Configuration 클래스를 인스턴스화하여? "conf" 변수에 할당
		org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();

		// JdbcType.NULL 사용하여 null 값에 대한 JDBC 타입 설정
		conf.setJdbcTypeForNull(JdbcType.NULL);

		// 지연 로딩을 활성화 >> 필요할 때만 연관된 객체를 가져오는 지연 로딩(Lazy Loading) 가능
		conf.setLazyLoadingEnabled(true);

		// 데이터베이스의 언더스코어(_)로 구분된 칼럼 이름 >> 자바의 카멜 케이스(camelCase) 네이밍으로 매핑
		conf.setMapUnderscoreToCamelCase(true);

		// null 값을 설정할 때에도 setter 메서드 호출
		conf.setCallSettersOnNulls(true);

		// SQL 실행 시간 제한
		conf.setDefaultStatementTimeout(300);

		// 캐시 사용 비활성화
		conf.setCacheEnabled(false);

		// 자동 생성된 키를 사용하여 데이터베이스에 데이터를 추가할 때 자동으로 생성된 키를 사용 ??
		conf.setUseGeneratedKeys(true);

		// 기본 실행자 유형을 배치(Batch)로 설정 ?
		conf.setDefaultExecutorType(ExecutorType.BATCH);

		// ???
		ClassLoader cl = this.getClass().getClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
		Resource[] resources = resolver.getResources("classpath*:mapper/**/*.xml"); // XML 파일 >> Resource 객체인 resources 로
		for(Resource r : resources) {
			log.info("mapper : {}", r.getFilename()); // 파일의 이름을 가져와서 로그에 출력
		}

		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean(); // sessionFactoryBean 객체 생성
		sessionFactoryBean.setFailFast(true); // sessionFactoryBean 의 setFailFast 메서드 호출, 매퍼 파일의 위치를 설정
		sessionFactoryBean.setDataSource(dataSource()); // MyBatis 설정 정보를 설정
		sessionFactoryBean.setMapperLocations(resources); // ?
		sessionFactoryBean.setConfiguration(conf); // ?
		return sessionFactoryBean;
	}


	@Bean
	@Primary // 여러 개의 빈 중에서 우선적으로 선택되는 빈을 지정할 때 사용 >> SqlSessionTemplate 빈으로 설정
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		// SqlSessionFactory 가져와서 SqlSessionTemplate 생성
	    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
	}
}