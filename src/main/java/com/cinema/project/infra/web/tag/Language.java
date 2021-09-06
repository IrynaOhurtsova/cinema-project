package com.cinema.project.infra.web.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Language extends BodyTagSupport{

    @Override
    public int doAfterBody() {
        HttpSession session = pageContext.getSession();
        Locale selectedLocale = (Locale) session.getAttribute("selectedLocale");
        String key = bodyContent.getString();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.resources", selectedLocale);
        if (key != null && !key.isEmpty()) {
            String value = bundle.getString(key);
            JspWriter out = bodyContent.getEnclosingWriter();
            try {
                out.print(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SKIP_BODY;
    }
}
