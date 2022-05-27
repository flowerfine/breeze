package cn.sliew.scaleph.api.config;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.MASTER_MAPPER_PACKAGE, sqlSessionFactoryRef = MasterDataSourceConfig.MASTER_SQL_SESSION_FACTORY)
public class MasterDataSourceConfig {

    static final String MASTER_ENTITY_PACKAGE = "cn.sliew.scaleph.dao.entity.master";
    static final String MASTER_MAPPER_PACKAGE = "cn.sliew.scaleph.dao.mapper.master";
    static final String MASTER_MAPPER_XML_PATH =
        "classpath*:cn.sliew.scaleph.dao.mapper/master/**/*.xml";

    static final String MASTER_SQL_SESSION_FACTORY = "masterSqlSessionFactory";
    static final String MASTER_DATA_SOURCE_FACTORY = "masterDataSource";
    static final String MASTER_TRANSACTION_MANAGER_FACTORY = "masterTransactionManager";

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Primary
    @Bean(MASTER_DATA_SOURCE_FACTORY)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
            .build();
    }

    @Primary
    @Bean(MASTER_TRANSACTION_MANAGER_FACTORY)
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Primary
    @Bean(MASTER_SQL_SESSION_FACTORY)
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();

        MybatisPlusProperties props = new MybatisPlusProperties();
        props.setMapperLocations(new String[] {MASTER_MAPPER_XML_PATH});
        factoryBean.setMapperLocations(props.resolveMapperLocations());

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(StdOutImpl.class);
        factoryBean.setConfiguration(configuration);

        factoryBean.setDataSource(masterDataSource());
        factoryBean.setTypeAliasesPackage(MASTER_ENTITY_PACKAGE);
        factoryBean.setPlugins(mybatisPlusInterceptor);
        return factoryBean.getObject();
    }

}
