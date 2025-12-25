package com.example.tools.repository;

import com.example.tools.entity.ServiceTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTicketRepo extends JpaRepository<ServiceTicket, Integer> {
    List<ServiceTicket> findByUsername(String username);
}
