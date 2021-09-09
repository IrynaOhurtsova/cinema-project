package com.cinema.project.infra.web.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Language extends TagSupport {

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
                e.printStackTrace();
            }
        }
        return SKIP_BODY;
    }
}
