package be.mira.jongeren.mailinglist.util.template_engine;

import be.mira.jongeren.mailinglist.util.date.DateTimeFormatterHelper;
import org.springframework.web.servlet.View;
import ro.pippo.jade.JadeTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PugView implements View {

    private String templateName;

    private JadeTemplateEngine templateEngine;

    public PugView(String templateName, JadeTemplateEngine templateEngine) {
        this.templateName = templateName;
        this.templateEngine = templateEngine;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        modelFilter(model);


        // Fix issue where browser (chrome) displays page source instead
        // of rendering html due to missing contentType.
        response.setContentType("text/html");
        templateEngine.renderResource(templateName + ".pug", (Map<String, Object>) model, response.getWriter());
    }

    // https://docs.oracle.com/javase/tutorial/java/generics/capture.html
    private <T> void modelFilter(Map<String, T> model){
        model.put("bindingResult", model.get("org.springframework.validation.BindingResult.subscriber"));
        model.put("dateHelper", (T) new DateTimeFormatterHelper());
    }
}
