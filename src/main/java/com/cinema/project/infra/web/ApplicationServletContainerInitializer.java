package com.cinema.project.infra.web;

import com.cinema.project.infra.config.ConfigLoader;
import com.cinema.project.infra.db.DataSourceConfig;
import com.cinema.project.infra.web.exeption.handler.BaseExceptionHandler;
import com.cinema.project.infra.web.exeption.handler.ExceptionHandler;
import com.cinema.project.infra.web.exeption.handler.ExceptionHandlerConfig;
import com.cinema.project.infra.web.request.ControllerFunctionHolder;
import com.cinema.project.infra.web.request.RequestHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.infra.web.response.ModelAndViewHandler;
import com.cinema.project.infra.web.response.ResponseHandler;
import com.cinema.project.schedule.ScheduleController;
import com.cinema.project.schedule.ScheduleRepository;
import com.cinema.project.schedule.ScheduleService;
import com.cinema.project.user.UserController;
import com.cinema.project.user.UserRepository;
import com.cinema.project.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ApplicationServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        FrontServlet frontServlet = buildApplication();
        ServletRegistration.Dynamic registration = ctx.addServlet("front", frontServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/cinema/*");
    }

    private FrontServlet buildApplication() {
        //infra
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ConfigLoader configLoader = new ConfigLoader(objectMapper);
        QueryValueResolver queryValueResolver = new QueryValueResolver(objectMapper);

        //datasource
        DataSource dataSource = new DataSourceConfig(configLoader).configureDataSource();

        //user
        UserRepository userRepository = new UserRepository(dataSource);
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        //schedule
        ScheduleRepository scheduleRepository = new ScheduleRepository(dataSource);
        ScheduleService scheduleService = new ScheduleService(scheduleRepository);
        ScheduleController scheduleController = new ScheduleController(scheduleService);

        //web
        ExceptionHandlerConfig exceptionHandlerConfig = new ExceptionHandlerConfig();
        ExceptionHandler exceptionHandler = exceptionHandlerConfig.exceptionHandler();
        Supplier<ModelAndView> controllerNotFoundResponseSupplier = () -> ModelAndView.withView("/error/notfound.jsp");

        final ControllerFunctionHolder holder1 =
                new ControllerFunctionHolder("/mainpage", "GET", request -> scheduleController.fullSchedule());
        ControllerFunctionHolder holder =
                new ControllerFunctionHolder("/user/login", "POST", request -> userController.login(request));
        List<ControllerFunctionHolder> controllerFunctionHolders = Arrays.asList(holder, holder1);

        RequestHandler requestHandler = new RequestHandler(controllerFunctionHolders, exceptionHandler, controllerNotFoundResponseSupplier);
        ResponseHandler<ModelAndView> responseHandler = new ModelAndViewHandler();
        return new FrontServlet(requestHandler, responseHandler);

    }
}
