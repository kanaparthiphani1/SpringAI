package com.example.tools.tools;

import com.example.tools.dto.TicketRequest;
import com.example.tools.entity.ServiceTicket;
import com.example.tools.service.ServiceTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceTicketDbTool {

    private final ServiceTicketService serviceTicketService;

    @Tool(name = "createServiceTicket", description = "Create the Service Ticket")
    public String createServiceTicket(@ToolParam TicketRequest ticketRequest, ToolContext toolContext) {

        String user = (String) toolContext.getContext().get("user");
        ServiceTicket serviceTicket = serviceTicketService.createTicket(ticketRequest,user);

        return "Ticket #" + serviceTicket.getId() + " created successfully for user " + serviceTicket.getUsername();

    }

    @Tool(name = "getTicketStatus", description = "Fetch the status of the tickets based on a given username")
    public List<ServiceTicket> getTicketStatus(ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("user");
        List<ServiceTicket> tickets =  serviceTicketService.getTicketsByUsername(username);
        return tickets;
    }

}
