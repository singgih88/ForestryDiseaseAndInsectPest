package com.jecyhw.core.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by jecyhw on 16-11-9.
 */
final public class Freemarker {
    private final Configuration configuration;

    public Freemarker(Configuration configuration) {
        this.configuration = configuration;
    }

    private Template getTemplate(String name) throws IOException {
        return configuration.getTemplate(name);
    }

    public String process(String name, Object dataModel) {
        String result = "";
        StringWriter writer = new StringWriter();
        try {
            getTemplate(name).process(dataModel, writer);
            result = writer.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
