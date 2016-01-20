/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.log.MLevel;
import java.net.MalformedURLException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author sp1d
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.sp1d.dvdshare.repos", "com.sp1d.dvdshare.service"})
public class RootConfig {

    @Autowired
    Environment env;

    private static final Logger LOG = LogManager.getLogger(RootConfig.class);

    @Bean
    DataSource dataSource() {
//        Настройки берутся из classpath:c3p0.properties
        ComboPooledDataSource ds = new ComboPooledDataSource();
        LOG.debug("DataSource is set: {}", ds);
//        DatabasePopulatorUtils.execute(databasePopulator(), ds);
//        com.mchange.v2.log.MLog.getLogger().setLevel(MLevel.OFF);
        return ds;
    }

    private DatabasePopulator databasePopulator() throws IllegalStateException, MalformedURLException {
        ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
        rdp.setContinueOnError(true);
        try {
            rdp.addScript(new UrlResource(env.getRequiredProperty("db.schema.initial")));
            rdp.addScript(new UrlResource(env.getRequiredProperty("db.schema.exampledata")));
        } catch (MalformedURLException | IllegalStateException e) {
            LOG.error("Can not read db initial scripts");
            throw e;
        }
        return rdp;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        va.setDatabase(Database.HSQL);
        va.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        va.setGenerateDdl(true);
        va.setShowSql(true);
        emf.setJpaVendorAdapter(va);

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.sp1d.dvdshare.entities");
        emf.setPersistenceUnitName("com.sp1d.dvdshare_PU0");


        return emf;
    }

//    @Bean
//    PlatformTransactionManager transactionManager() {
//        JpaTransactionManager tm = new JpaTransactionManager();
//        tm.setEntityManagerFactory(entityManagerFactory().getObject());
////        tm.setDataSource(dataSource());
//        tm.setPersistenceUnitName("com.sp1d.dvdshare_PU0");
//        return tm;
//    }
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory,
            DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setPersistenceUnitName("com.sp1d.dvdshare_PU0");
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
