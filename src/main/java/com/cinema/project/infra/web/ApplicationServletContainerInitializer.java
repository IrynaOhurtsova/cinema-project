package com.cinema.project.infra.web;

import com.cinema.project.infra.config.ConfigLoader;
import com.cinema.project.infra.db.DataSourceConfig;
import com.cinema.project.infra.web.exeption.handler.ExceptionHandler;
import com.cinema.project.infra.web.exeption.handler.ExceptionHandlerConfig;
import com.cinema.project.infra.web.request.ControllerFunctionHolder;
import com.cinema.project.infra.web.request.RequestHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.infra.web.response.ModelAndViewHandler;
import com.cinema.project.infra.web.response.ResponseHandler;
import com.cinema.project.movie.MovieController;
import com.cinema.project.movie.MovieRepository;
import com.cinema.project.movie.MovieService;
import com.cinema.project.seance.*;
import com.cinema.project.user.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

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
        objectMapper.registerModule(new JSR310Module());
        ConfigLoader configLoader = new ConfigLoader(objectMapper);
        QueryValueResolver queryValueResolver = new QueryValueResolver(objectMapper);

        //datasource
        DataSource dataSource = new DataSourceConfig(configLoader).configureDataSource();

        //user
        UserRepository userRepository = new UserRepository(dataSource);
        ClientRegisterValidator clientRegisterValidator = new ClientRegisterValidator(userRepository);
        UserLoginRequestDtoToClientMapper userLoginRequestDtoToClientMapper = new UserLoginRequestDtoToClientMapper();
        UserService userService = new UserService(userRepository, userLoginRequestDtoToClientMapper, clientRegisterValidator);
        UserController userController = new UserController(userService, queryValueResolver);

        //movie
        MovieRepository movieRepository = new MovieRepository(dataSource);
        MovieService movieService = new MovieService(movieRepository);
        MovieController movieController = new MovieController(movieService);

        //seance
        SeanceRepository seanceRepository = new SeanceRepository(dataSource);
        SeanceCreateValidatorConfig seanceCreateValidatorConfig = new SeanceCreateValidatorConfig();
        SeanceCreateValidator seanceCreateValidator = seanceCreateValidatorConfig.seanceCreateValidator();
        seanceCreateValidator.setSeanceRepository(seanceRepository);
        SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper = new SeanceCreateDtoToSeanceMapper(movieService);
        SeanceService seanceService = new SeanceService(seanceRepository, movieService, seanceCreateValidator, seanceCreateDtoToSeanceMapper);
        SeanceController seanceController = new SeanceController(seanceService, queryValueResolver);

        //web
        ExceptionHandlerConfig exceptionHandlerConfig = new ExceptionHandlerConfig();
        ExceptionHandler exceptionHandler = exceptionHandlerConfig.exceptionHandler();
        Supplier<ModelAndView> controllerNotFoundResponseSupplier = () -> ModelAndView.withView("/error/notfound.jsp");


        ControllerFunctionHolder holder2 =
                new ControllerFunctionHolder("/seance/create", "GET", request -> seanceController.createSeance(request));
        ControllerFunctionHolder holder1 =
                new ControllerFunctionHolder("/mainpage", "GET", request -> seanceController.allSeances());
        ControllerFunctionHolder holder =
                new ControllerFunctionHolder("/user/login", "POST", request -> userController.login(request));
        ControllerFunctionHolder holder3 =
                new ControllerFunctionHolder("/seance/delete", "POST", request -> seanceController.delete(request));
        ControllerFunctionHolder holder4 =
                new ControllerFunctionHolder("/mainpagewithdelete", "GET", request -> seanceController.allSeancesWithDelete());
        ControllerFunctionHolder holder5 =
                new ControllerFunctionHolder("/user/register", "POST", request -> userController.registerUser(request));
        List<ControllerFunctionHolder> controllerFunctionHolders = Arrays.asList(holder, holder1, holder2, holder3, holder4, holder5);

        RequestHandler requestHandler = new RequestHandler(controllerFunctionHolders, exceptionHandler, controllerNotFoundResponseSupplier);
        ResponseHandler<ModelAndView> responseHandler = new ModelAndViewHandler();
        return new FrontServlet(requestHandler, responseHandler);

    }
}
