package Exo1.Entities;

import Exo1.Enums.OperationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    private int id;
    private double amount;
    private OperationStatus status;
    private int accountId;
    private Timestamp operationDate;
}