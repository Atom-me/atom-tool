package com.atom.tool.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker tool
 *
 * @author Atom
 */
@Slf4j
public class FreemarkerUtil {

    /**
     * freemarker config
     */
    private static final Configuration FREEMARKER_CONFIG = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static {
        try {
            String path = ResourceUtils.getURL("classpath:templates").getFile();
            FREEMARKER_CONFIG.setDirectoryForTemplateLoading(new File(path));
            FREEMARKER_CONFIG.setNumberFormat("#");
            FREEMARKER_CONFIG.setClassicCompatible(true);
            FREEMARKER_CONFIG.setDefaultEncoding("UTF-8");
            FREEMARKER_CONFIG.setLocale(Locale.CHINA);
            FREEMARKER_CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * process Template Into String
     *
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateIntoString(Template template, Object model) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    /**
     * process String
     *
     * @param templateName
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processString(String templateName, Map<String, Object> params) throws IOException, TemplateException {
        Template template = FREEMARKER_CONFIG.getTemplate(templateName);
        return processTemplateIntoString(template, params);
    }


}
