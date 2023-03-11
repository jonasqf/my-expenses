package com.jonasqf.myexpenses.owner;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="owner")
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    private UUID id;
    @Column(name="first_name")
    @Getter
    @Setter
    private BigDecimal firstName;

    @Column(name="last_name")
    @Getter
    @Setter
    private BigDecimal lastName;

}
