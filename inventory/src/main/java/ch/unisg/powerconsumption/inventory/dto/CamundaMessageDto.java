package ch.unisg.powerconsumption.inventory.dto;

import lombok.*;
import ch.unisg.powerconsumption.inventory.dto.MessageProcessDto;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CamundaMessageDto implements Serializable {

    private String correlationId;
    private MessageProcessDto dto;

}