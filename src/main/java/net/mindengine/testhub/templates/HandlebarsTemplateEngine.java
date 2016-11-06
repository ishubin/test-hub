package net.mindengine.testhub.templates;

import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.helper.BlockHelper;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HandlebarsTemplateEngine extends TemplateEngine {
    private Handlebars handlebars;
    private Map<String, Template> compiledTemplates = new HashMap<>();

    public HandlebarsTemplateEngine(String templatePath) {
        ClassPathTemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix(templatePath);
        templateLoader.setSuffix(".hbs");
        this.handlebars = new Handlebars(templateLoader);
        initHelpers();
    }

    private void initHelpers() {
        this.handlebars.registerHelper("ifGreaterThan", (var, options) -> {
            long a = ((Number)var).longValue();
            long b = ((Number)options.param(0)).longValue();
            if (a > b) {
                return options.fn(var);
            } else {
                return options.inverse(var);
            }
        });
    }

    public String render(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();

        try {
            Template template = findTemplate(viewName);
            return template.apply(modelAndView.getModel());
        } catch (IOException ex) {
            throw new RuntimeException("Couldn't render template: " + viewName, ex);
        }
    }

    private Template findTemplate(String viewName) throws IOException {
        Template template;
        synchronized (viewName.intern()) {
            if (compiledTemplates.containsKey(viewName)) {
                template = compiledTemplates.get(viewName);
            } else {
                template = this.handlebars.compile(viewName);
                compiledTemplates.put(viewName, template);
            }
        }
        return template;
    }

    public Handlebars getHandlebars() {
        return handlebars;
    }
}
