package com.fis.crm.service.dto;

import java.util.List;

public class TicketProcessDTO {

    private TicketDTO ticketDTO;
    private ProcessTicketDTO processTicketDTO;
    private ConfirmTicketDTO confirmTicketDTO;
    private List<ProcessTicketDTO> listProcessTicketDTO;

    public ConfirmTicketDTO getConfirmTicketDTO() {
        return confirmTicketDTO;
    }

    public void setConfirmTicketDTO(ConfirmTicketDTO confirmTicketDTO) {
        this.confirmTicketDTO = confirmTicketDTO;
    }

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }

    public void setTicketDTO(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }

    public ProcessTicketDTO getProcessTicketDTO() {
        return processTicketDTO;
    }

    public void setProcessTicketDTO(ProcessTicketDTO processTicketDTO) {
        this.processTicketDTO = processTicketDTO;
    }

    public List<ProcessTicketDTO> getListProcessTicketDTO() {
        return listProcessTicketDTO;
    }

    public void setListProcessTicketDTO(List<ProcessTicketDTO> listProcessTicketDTO) {
        this.listProcessTicketDTO = listProcessTicketDTO;
    }
}
