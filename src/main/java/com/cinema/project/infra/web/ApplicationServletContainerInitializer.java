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
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.time.LocalTime;
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
        SeanceCreateValidatorConfig seanceCreateValidatorConfig = new SeanceCreateValidatorConfig(5, LocalTime.of(9, 0), LocalTime.of(22, 0));
        SeanceCreateValidator seanceCreateValidator = new SeanceCreateValidator(seanceRepository, seanceCreateValidatorConfig);
        SeanceCreateDtoToSeanceMapper seanceCreateDtoToSeanceMapper = new SeanceCreateDtoToSeanceMapper(movieService);
        SeanceService seanceService = new SeanceService(seanceRepository, movieService, seanceCreateValidator, seanceCreateDtoToSeanceMapper);
        SeanceController seanceController = new SeanceController(seanceService, queryValueResolver);

        //ticket
        TicketRepository ticketRepository = new TicketRepository(dataSource);
        TicketCreateValidator ticketCreateValidator = new TicketCreateValidator(ticketRepository, seanceRepository, seanceCreateValidatorConfig);
        TicketService ticketService = new TicketService(ticketRepository, ticketCreateValidator, seanceService, seanceRepository);
        TicketController ticketController = new TicketController(ticketService, queryValueResolver);

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
        ControllerFunctionHolder holder6 =
                new ControllerFunctionHolder("/buyticket", "GET", request -> seanceController.allSeancesWithBuying());
        ControllerFunctionHolder holder7 =
                new ControllerFunctionHolder("/ticket/buy", "POST", request -> ticketController.createTicket(request));
        ControllerFunctionHolder holder8 =
                new ControllerFunctionHolder("/ticket/mytickets", "GET", request -> ticketController.getAllTicketsByUserId(request));
        ControllerFunctionHolder holder9 =
                new ControllerFunctionHolder("/seance/freeplaces", "POST", request -> ticketController.getFreePlacesForSeance(request));
        ControllerFunctionHolder holder10
                = new ControllerFunctionHolder("/seance/attendance", "POST", request -> ticketController.getAttendance(request));

        List<ControllerFunctionHolder> controllerFunctionHolders =
                Arrays.asList(holder, holder1, holder2, holder3, holder4, holder5, holder6, holder7, holder8, holder9, holder10);

        RequestHandler requestHandler = new RequestHandler(controllerFunctionHolders, exceptionHandler, controllerNotFoundResponseSupplier);
        ResponseHandler<ModelAndView> responseHandler = new ModelAndViewHandler();
        return new FrontServlet(requestHandler, responseHandler);

    }
}
