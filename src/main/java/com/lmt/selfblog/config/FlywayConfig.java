package com.lmt.selfblog.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig implements BeanFactoryPostProcessor {

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory.containsBeanDefinition("entityManagerFactory")) {
            String[] dependsOn = beanFactory.getBeanDefinition("entityManagerFactory").getDependsOn();
            if (dependsOn == null) {
                beanFactory.getBeanDefinition("entityManagerFactory").setDependsOn("flyway");
            } else {
                String[] newDependsOn = new String[dependsOn.length + 1];
                System.arraycopy(dependsOn, 0, newDependsOn, 0, dependsOn.length);
                newDependsOn[dependsOn.length] = "flyway";
                beanFactory.getBeanDefinition("entityManagerFactory").setDependsOn(newDependsOn);
            }
        }
    }
}
