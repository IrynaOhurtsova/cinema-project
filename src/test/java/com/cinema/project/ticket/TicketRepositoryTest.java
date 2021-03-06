package com.cinema.project.ticket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private PreparedStatement extraPreparedStatement;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private TicketRepository ticketRepository;

    private final Ticket expectedTicket = new Ticket(1L, 1L, 1L);

    @Test
    public void createTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(1L);
        ticket.setUserId(1L);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(extraPreparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        when(extraPreparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(expectedTicket, ticketRepository.createTicket(ticket));

        verify(connection, times(1)).setAutoCommit(false);
        verify(preparedStatement, times(1)).setLong(1, ticket.getUserId());
        verify(preparedStatement, times(1)).setLong(2, ticket.getSeanceId());
        verify(preparedStatement, times(1)).setLong(1, ticket.getSeanceId());
        verify(connection, times(1)).commit();
    }

    @Test(expected = SQLException.class)
    public void createTicketNotSussesInSecondQuery() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(1L);
        ticket.setUserId(1L);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(extraPreparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        when(extraPreparedStatement.executeUpdate()).thenThrow(new SQLException());

        doNothing().when(connection).rollback();
        doNothing().when(connection).close();

        ticketRepository.createTicket(ticket);

        verify(connection, times(1)).setAutoCommit(false);
        verify(preparedStatement, times(1)).setLong(1, ticket.getUserId());
        verify(preparedStatement, times(1)).setLong(2, ticket.getSeanceId());
        verify(preparedStatement, times(1)).setLong(1, ticket.getSeanceId());
        verify(connection, times(1)).rollback();
        verify(connection, times(1)).close();
    }

    @Test(expected = SQLException.class)
    public void createTicketNotSussesInFirstQuery() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setSeanceId(1L);
        ticket.setUserId(1L);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(extraPreparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenThrow(new SQLException());

        ticketRepository.createTicket(ticket);

        verify(connection, times(1)).setAutoCommit(false);
        verify(preparedStatement, times(1)).setLong(1, ticket.getUserId());
        verify(preparedStatement, times(1)).setLong(2, ticket.getSeanceId());
        verify(connection, times(1)).rollback();
        verify(connection, times(1)).close();
    }

    @Test
    public void getTicketsByUserId() throws Exception {
        List<Ticket> expectedTickets = Collections.singletonList(expectedTicket);

        LinkedList<Boolean> nextAnswers = new LinkedList<>();
        nextAnswers.add(true);
        nextAnswers.add(false);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenAnswer(invocation -> nextAnswers.remove());
        when(resultSet.getLong("id")).thenReturn(expectedTicket.getId());
        when(resultSet.getLong("user_id")).thenReturn(expectedTicket.getUserId());
        when(resultSet.getLong("seance_id")).thenReturn(expectedTicket.getSeanceId());

        assertEquals(expectedTickets, ticketRepository.getTicketsByUserId(expectedTicket.getUserId()));

        verify(preparedStatement, times(1)).setLong(1, expectedTicket.getSeanceId());
    }
}