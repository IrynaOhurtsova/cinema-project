package com.cinema.project.infra.web.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Language extends TagSupport {

    private static final Logger logger = Logger.getLogger(Language.class.getName());

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        if (message != null && !message.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.resources", selectedLocale);
            String value = bundle.getString(message);
            try {
                pageContext.getOut().print(value);
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
        return SKIP_BODY;
    }
}
