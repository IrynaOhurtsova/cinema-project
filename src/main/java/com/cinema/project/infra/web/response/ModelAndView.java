package com.cinema.project.infra.web.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ModelAndView {

    private String view;
    private Map<String, Object> attributes = new HashMap<>();
    private boolean isRedirect;

    public ModelAndView(String view, boolean isRedirect) {
        this.view = view;
        this.isRedirect = isRedirect;
    }

    public static ModelAndView withView(String view) {
        return new ModelAndView(view, false);
    }

    public void addAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }

}
