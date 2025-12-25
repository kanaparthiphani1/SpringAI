package com.example.tools.service;

import com.example.tools.dto.TicketRequest;
import com.example.tools.entity.ServiceTicket;
import com.example.tools.repository.ServiceTicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTicketService {

    private final ServiceTicketRepo serviceTicketRepo;

    public ServiceTicket createTicket(TicketRequest ticketInput, String username) {
        ServiceTicket ticket = ServiceTicket.builder()
                .issue(ticketInput.issue())
                .username(username)
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return serviceTicketRepo.save(ticket);
    }

    public List<ServiceTicket> getTicketsByUsername(String username) {
        return serviceTicketRepo.findByUsername(username);
    }
}
