package com.cinema.project.ticket;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TicketRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public Ticket createTicket(Ticket ticket) {
        String sql_query = "INSERT INTO ticket (user_id, seance_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, ticket.getUserId());
            preparedStatement.setLong(2, ticket.getSeanceId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            ticket.setId(resultSet.getLong(1));
            return ticket;
        }
    }

    @SneakyThrows
    public List<Ticket> getTicketsBySeanceId(Long seanceId) {
        String sql_query = "SELECT * FROM ticket WHERE seance_id IN (?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setLong(1, seanceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(new Ticket(resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("seance_id")));
            }
            return tickets;
        }
    }

    @SneakyThrows
    public List<Ticket> getTicketsByUserId(Long userId) {
        String sql_query = "SELECT * FROM ticket WHERE user_id IN (?)";
        List<Ticket> tickets = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("seance_id")));
            }
            return tickets;
        }
    }

    @SneakyThrows
    public List<Ticket> getTicketsBySeancesIds(String ids) {
        String sql_query = "SELECT * FROM ticket WHERE seance_id IN (%s)";
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(sql_query, ids));
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(new Ticket(resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("seance_id")));
            }
            return tickets;
        }
    }
}
