package com.cinema.project.ticket;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketRepository {

    private final DataSource dataSource;

    @SneakyThrows
    public Ticket createTicket(Ticket ticket) {
        String sqlInsert = "INSERT INTO ticket (user_id, seance_id) VALUES (?, ?)";
        String sqlUpdate = "UPDATE seance SET free_places=free_places-1 WHERE id=?";
        Connection connection = dataSource.getConnection();
        try (PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementUpdate = connection.prepareStatement(sqlUpdate)) {
            connection.setAutoCommit(false);
            insertTicket(preparedStatementInsert, ticket);
            updateFreePlaces(preparedStatementUpdate, ticket);
            connection.commit();
            return ticket;
        } catch (SQLException exception) {
            connection.rollback();
            throw exception;
        } finally {
            connection.close();
        }
    }

    private void insertTicket(PreparedStatement preparedStatement, Ticket ticket) throws SQLException {
        preparedStatement.setLong(1, ticket.getUserId());
        preparedStatement.setLong(2, ticket.getSeanceId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        ticket.setId(resultSet.getLong(1));
    }

    private void updateFreePlaces(PreparedStatement preparedStatement, Ticket ticket) throws SQLException {
        preparedStatement.setLong(1, ticket.getSeanceId());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public List<Ticket> getTicketsByUserId(Long userId) {
        String sqlQuery = "SELECT * FROM ticket WHERE user_id IN (?)";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
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

}
