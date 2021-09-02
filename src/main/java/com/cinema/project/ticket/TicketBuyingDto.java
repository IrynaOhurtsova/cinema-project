package com.cinema.project.ticket;

import lombok.Data;

@Data
public class TicketBuyingDto {

    Long id; // id - seanceId , but in session name of parametr id and queryValueResolver doesn't understand name seanceID
}
