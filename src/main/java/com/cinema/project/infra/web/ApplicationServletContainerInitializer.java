package com.cinema.project.infra.web;

import com.cinema.project.infra.web.listener.*;
import com.cinema.project.infra.config.ConfigLoader;
import com.cinema.project.infra.db.DataSourceConfig;
import com.cinema.project.infra.web.exeption.handler.ExceptionHandler;
import com.cinema.project.infra.web.exeption.handler.ExceptionHandlerConfig;
import com.cinema.project.infra.web.listener.LocaleSessionListenerConfiguration;
import com.cinema.project.infra.web.request.ControllerFunctionHolder;
import com.cinema.project.infra.web.request.RequestHandler;
import com.cinema.project.infra.web.response.ModelAndView;
import com.cinema.project.infra.web.response.ModelAndViewHandler;
import com.cinema.project.infra.web.response.ResponseHandler;
import com.cinema.project.movie.MovieController;
import com.cinema.project.movie.MovieRepository;
import com.cinema.project.movie.MovieService;
import com.cinema.project.seance.*;
import com.cinema.project.ticket.TicketController;
import com.cinema.project.ticket.TicketCreateValidator;
import com.cinema.project.ticket.TicketRepository;
import com.cinema.project.ticket.TicketService;
import com.cinema.project.user.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Supplier;

public class ApplicationServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
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
        Map<UserRole, ModelAndView> modelAndViewHome = new HashMap<>();
        modelAndViewHome.put(UserRole.CLIENT, ModelAndView.withView("/home/client.jsp"));
        modelAndViewHome.put(UserRole.ADMIN, ModelAndView.withView("/home/admin.jsp"));
        UserController userController = new UserController(userService, queryValueResolver, modelAndViewHome);

        //movie
        MovieRepository movieRepository = new MovieRepository(dataSource);
        MovieService movieService = new MovieService(movieRepository);
        MovieController movieController = new MovieController(movieService);

        //seance
        SeanceRepository seanceRepository = new SeanceRepository(dataSource);
        SeanceCreateValidatorConfig seanceCreateValidatorConfig = new SeanceCreateValidatorConfig(300, LocalTime.of(9, 0), LocalTime.of(22, 0));
        SeanceCreateValidator seanceCreateValidator = new SeanceCreateValidator(seanceRepository, seanceCreateValidatorConfig);
        SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper = new SeanceCreateDtoToSeanceMapper(movieService);
        SeanceService seanceService = new SeanceService(seanceRepository, movieService, seanceCreateValidator, seanceCreateDtoToSeanceMapper);
        SeanceController seanceController = new SeanceController(seanceService, queryValueResolver);

        //ticket
        TicketRepository ticketRepository = new TicketRepository(dataSource);
        TicketCreateValidator ticketCreateValidator = new TicketCreateValidator(seanceService);
        TicketService ticketService = new TicketService(ticketRepository, ticketCreateValidator, seanceService);
        TicketController ticketController = new TicketController(ticketService, queryValueResolver);

        //web
        ExceptionHandlerConfig exceptionHandlerConfig = new ExceptionHandlerConfig();
        ExceptionHandler exceptionHandler = exceptionHandlerConfig.exceptionHandler();
        Supplier<ModelAndView> controllerNotFoundResponseSupplier = () -> ModelAndView.withView("/error/notfound.jsp");


        ControllerFunctionHolder createSeance =
                new ControllerFunctionHolder("/seance/create", "GET", seanceController::createSeance);
        ControllerFunctionHolder allSeances =
                new ControllerFunctionHolder("/mainpage", "GET", request -> seanceController.allSeances());
        ControllerFunctionHolder login =
                new ControllerFunctionHolder("/user/login", "POST", userController::login);
        ControllerFunctionHolder deleteSeance =
                new ControllerFunctionHolder("/seance/delete", "POST", seanceController::delete);
        ControllerFunctionHolder allSeancesForAdmin =
                new ControllerFunctionHolder("/mainpageforadmin", "GET", request -> seanceController.allSeancesWithDelete());
        ControllerFunctionHolder registerUser =
                new ControllerFunctionHolder("/user/register", "POST", userController::registerUser);
        ControllerFunctionHolder allSeancesForClient =
                new ControllerFunctionHolder("/mainpageforclient", "GET", request -> seanceController.allSeancesWithBuying());
        ControllerFunctionHolder createTicket =
                new ControllerFunctionHolder("/ticket/buy", "POST", ticketController::createTicket);
        ControllerFunctionHolder allTicketsByUserId =
                new ControllerFunctionHolder("/ticket/mytickets", "GET", ticketController::getAllTicketsByUserId);
        ControllerFunctionHolder filterSeanceForUser =
                new ControllerFunctionHolder("/seance/available", "POST", ticketController::getSeancesForUserByTickets);
        ControllerFunctionHolder changeLocale =
                new ControllerFunctionHolder("/user/change/language", "POST", userController::changeLocale);
        List<ControllerFunctionHolder> controllerFunctionHolders =
                Arrays.asList(login, allSeances, createSeance, deleteSeance, allSeancesForAdmin, registerUser,
                        allSeancesForClient, createTicket, allTicketsByUserId, filterSeanceForUser, changeLocale);

        RequestHandler requestHandler = new RequestHandler(controllerFunctionHolders, exceptionHandler, controllerNotFoundResponseSupplier);
        ResponseHandler<ModelAndView> responseHandler = new ModelAndViewHandler();
        return new FrontServlet(requestHandler, responseHandler);

    }
}
