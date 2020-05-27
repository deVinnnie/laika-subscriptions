package be.mira.jongeren.mailinglist.util.template_engine;

import com.github.jknack.handlebars.springmvc.HandlebarsView;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomHandlebarsView extends HandlebarsView {
    @Override
    protected void renderMergedTemplateModel(
        Map<String, Object> model,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        model.put("bindingResult", model.get("org.springframework.validation.BindingResult.subscriber"));

        final CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        if(csrf != null) {
            model.put("_csrf", csrf.getToken());
        }

        super.renderMergedTemplateModel(model, request, response);
    }
}
