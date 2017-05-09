package be.mira.jongeren.mailinglist.config;

import be.mira.jongeren.mailinglist.util.template_engine.PugViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import ro.pippo.core.Application;
import ro.pippo.core.PippoConstants;
import ro.pippo.core.PippoSettings;
import ro.pippo.core.RuntimeMode;
import ro.pippo.jade.JadeTemplateEngine;

@Configuration
public class PugTemplateConfig {

    @Bean
    public JadeTemplateEngine jadeTemplateEngine(){
        JadeTemplateEngine templateEngine = new JadeTemplateEngine();

        PippoSettings pippoSettings = new PippoSettings(RuntimeMode.DEV);
        pippoSettings.overrideSetting(PippoConstants.SETTING_TEMPLATE_PATH_PREFIX, "/templates");

        templateEngine.setFileExtension("pug");
        templateEngine.init(
            new Application(pippoSettings)
        );
        return templateEngine;
    }

    @Bean
    public ViewResolver jadeViewResolver(@Autowired JadeTemplateEngine templateEngine){
        PugViewResolver resolver = new PugViewResolver();
        resolver.setTemplateEngine(templateEngine);
        return resolver;
    }
}
