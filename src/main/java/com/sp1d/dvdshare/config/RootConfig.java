/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
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
/*
 * Настройки берутся из classpath:c3p0.properties
 */

   @Bean
    DataSource dataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
//        LOG.debug("DataSource is set: {}", ds);
//        com.mchange.v2.log.MLog.getLogger().setLevel(MLevel.OFF);
        return ds;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        va.setDatabase(Database.HSQL);
        va.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        va.setGenerateDdl(false);
        va.setShowSql(false);
        emf.setJpaVendorAdapter(va);

        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.sp1d.dvdshare.entities");
        emf.setPersistenceUnitName("com.sp1d.dvdshare_PU0");

/*
 * Инициализация БД из скриптов
 */
        initDatabase();
        return emf;
    }

    private void initDatabase() {
        int existingTables = 0;

        try (Statement st = dataSource().getConnection().createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='PUBLIC'")) {

            rs.next();
            existingTables = rs.getInt(1);
        } catch (SQLException ex) {
            LOG.fatal(ex);
        }

        if (existingTables == 0) {
            LOG.debug("Database seems to be empty. Trying to fill it");
            ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
            rdp.addScript(new ClassPathResource(env.getRequiredProperty("db.schema.initial")));
            rdp.addScript(new ClassPathResource(env.getRequiredProperty("db.schema.exampledata")));
            DatabasePopulatorUtils.execute(rdp, dataSource());
        }
    }

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
