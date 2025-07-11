package Entities;

import Enums.OperationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {
    private int id;
    private int customerId;
    private Customer customer;
    private List<Operation> operations = new ArrayList<>();
    private Double totalAmount;

    public double calculateBalance() {
        if (operations == null || operations.isEmpty()) {
            return totalAmount != null ? totalAmount : 0.0;
        }

        return operations.stream()
                .mapToDouble(op -> op.getStatus() == OperationStatus.DEPOSIT ?
                        op.getAmount() : -op.getAmount())
                .sum();
    }
}