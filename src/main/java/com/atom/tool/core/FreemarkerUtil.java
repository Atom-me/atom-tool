package com.atom.tool.core;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

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
    private static final List<TemplateLoader> TEMPLATE_LOADERS = new ArrayList<>();

    static {
        try {
            ClassTemplateLoader classTemplateLoader = new ClassTemplateLoader(FreemarkerUtil.class.getClassLoader(), "templates");
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            TEMPLATE_LOADERS.addAll(Arrays.asList(classTemplateLoader, stringTemplateLoader));
            FREEMARKER_CONFIG.setTemplateLoader(new MultiTemplateLoader(new TemplateLoader[]{classTemplateLoader, stringTemplateLoader}));
            FREEMARKER_CONFIG.setNumberFormat("#");
            FREEMARKER_CONFIG.setClassicCompatible(true);
            FREEMARKER_CONFIG.setDefaultEncoding("UTF-8");
            FREEMARKER_CONFIG.setLocale(Locale.CHINA);
            FREEMARKER_CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (Exception e) {
            log.error("freemarker init config error: ", e);
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

    /**
     * process template to string use templateContentString in memory
     *
     * @param templateName
     * @param templateContent
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateContentToString(String templateName, String templateContent, Map<String, Object> params) throws IOException, TemplateException {
        StringTemplateLoader stringTemplateLoader = lookUpStringTemplateLoader();
        stringTemplateLoader.putTemplate(templateName, templateContent);
        Template template = FREEMARKER_CONFIG.getTemplate(templateName);
        return processTemplateIntoString(template, params);
    }

    /**
     * look up the StringTemplateLoader
     *
     * @return
     */
    private static StringTemplateLoader lookUpStringTemplateLoader() {
        for (TemplateLoader templateLoader : TEMPLATE_LOADERS) {
            if (templateLoader instanceof StringTemplateLoader) {
                return (StringTemplateLoader) templateLoader;
            }
        }
        throw new RuntimeException("未初始化StringTemplateLoader！");
    }
}
