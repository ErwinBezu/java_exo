package Exo1.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
