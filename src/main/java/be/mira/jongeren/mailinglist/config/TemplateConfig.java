package be.mira.jongeren.mailinglist.config;

import be.mira.jongeren.mailinglist.util.template_engine.CustomHandlebarsView;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class TemplateConfig {

    @Bean
    public ViewResolver handleBarsViewResolver(){
        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver(CustomHandlebarsView.class);
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.registerHelper("hasFieldErrors", (Helper<BindingResult>) (context, options) -> {
            String field = options.get("field");
            if(context.hasFieldErrors(field)){
                return options.fn();
            }
            return null;
        });
        viewResolver.registerHelper("getFieldError", (Helper<BindingResult>) (context, options) -> {
            String field = options.get("field");
            FieldError fieldError = context.getFieldError(field);
            if(fieldError != null){
                return fieldError.getCode();
            }
            return "unknown";
        });

        return viewResolver;
    }
}
