package be.mira.jongeren.mailinglist.util.template_engine;

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
        templateEngine.renderResource(templateName + ".pug", (Map<String, Object>) model, response.getWriter());
    }
}
