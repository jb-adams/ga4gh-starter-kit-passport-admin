package org.ga4gh.starterkit.passport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.common.util.DeepObjectMerger;
import org.ga4gh.starterkit.passport.configuration.PassportConfigContainer;
import org.ga4gh.starterkit.passport.model.PassportUser;
import org.ga4gh.starterkit.passport.utils.hibernate.PassportHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties
public class AppConfig implements WebMvcConfigurer {

    /* ******************************
     * PASSPORT CONFIG BEANS
     * ****************************** */

    @Bean
    public Options getCommandLineOptions() {
        final Options options = new Options();
        options.addOption("c", "config", true, "Path to YAML config file");
        return options;
    }

    @Bean
    @Scope(AppConfigConstants.PROTOTYPE)
    @Qualifier(AppConfigConstants.EMPTY_PASSPORT_CONFIG_CONTAINER)
    public PassportConfigContainer emptyPassportConfigContainer() {
        return new PassportConfigContainer();
    }

    @Bean
    @Qualifier(AppConfigConstants.DEFAULT_PASSPORT_CONFIG_CONTAINER)
    public PassportConfigContainer defaultPassportConfigContainer(
        @Qualifier(AppConfigConstants.EMPTY_PASSPORT_CONFIG_CONTAINER) PassportConfigContainer passportConfigContainer
    ) {
        return passportConfigContainer;
    }

    @Bean
    @Qualifier(AppConfigConstants.USER_PASSPORT_CONFIG_CONTAINER)
    public PassportConfigContainer userPassportConfigContainer(
        @Autowired ApplicationArguments args,
        @Autowired() Options options,
        @Qualifier(AppConfigConstants.EMPTY_PASSPORT_CONFIG_CONTAINER) PassportConfigContainer passportConfigContainer
    ) {

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args.getSourceArgs());
            String configFilePath = cmd.getOptionValue("config");
            if (configFilePath != null) {
                File configFile = new File(configFilePath);
                if (configFile.exists() && !configFile.isDirectory()) {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    passportConfigContainer = mapper.readValue(configFile, PassportConfigContainer.class);
                } else {
                    throw new FileNotFoundException();
                }
            }
        } catch (ParseException e) {
            System.out.println("ERROR: problem encountered setting config, config not set");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: problem encountered setting config, config file not found");
        } catch (IOException e) {
            System.out.println("ERROR: problem encountered setting config, config YAML could not be parsed");
        }

        return passportConfigContainer;
    }

    @Bean
    @Qualifier(AppConfigConstants.FINAL_PASSPORT_CONFIG_CONTAINER)
    public PassportConfigContainer finalPassportConfigContainer(
        @Qualifier(AppConfigConstants.DEFAULT_PASSPORT_CONFIG_CONTAINER) PassportConfigContainer defaultContainer,
        @Qualifier(AppConfigConstants.USER_PASSPORT_CONFIG_CONTAINER) PassportConfigContainer userContainer
    ) {
        DeepObjectMerger.merge(userContainer, defaultContainer);
        return defaultContainer;
    }

    /* ******************************
     * HIBERNATE CONFIG BEANS
     * ****************************** */

    @Bean
    public PassportHibernateUtil getPassportHibernateUtil(
        @Qualifier(AppConfigConstants.FINAL_PASSPORT_CONFIG_CONTAINER) PassportConfigContainer passportConfigContainer
    ) {
        PassportHibernateUtil hibernateUtil = new PassportHibernateUtil();
        hibernateUtil.setHibernateProps(passportConfigContainer.getPassportConfig().getHibernateProps());
        return hibernateUtil;
    }

    /* ******************************
     * REQUEST HANDLER BEANS
     * ****************************** */

    @Bean
    @RequestScope
    public BasicShowRequestHandler<String, PassportUser> showUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicShowRequestHandler<String, PassportUser> showUser = new BasicShowRequestHandler<>(PassportUser.class);
        showUser.setHibernateUtil(hibernateUtil);
        return showUser;
    }

    @Bean
    @RequestScope
    public BasicCreateRequestHandler<String, PassportUser> createUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicCreateRequestHandler<String, PassportUser> createUser = new BasicCreateRequestHandler<>(PassportUser.class);
        createUser.setHibernateUtil(hibernateUtil);
        return createUser;
    }

    @Bean
    @RequestScope
    public BasicUpdateRequestHandler<String, PassportUser> updateUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicUpdateRequestHandler<String, PassportUser> updateUser = new BasicUpdateRequestHandler<>(PassportUser.class);
        updateUser.setHibernateUtil(hibernateUtil);
        return updateUser;
    }

    @Bean
    @RequestScope
    public BasicDeleteRequestHandler<String, PassportUser> deleteUserRequestHandler(
        @Autowired PassportHibernateUtil hibernateUtil
    ) {
        BasicDeleteRequestHandler<String, PassportUser> deleteUser = new BasicDeleteRequestHandler<>(PassportUser.class);
        deleteUser.setHibernateUtil(hibernateUtil);
        return deleteUser;
    }
}