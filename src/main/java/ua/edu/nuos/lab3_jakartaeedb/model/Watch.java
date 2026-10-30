package ua.edu.nuos.lab3_jakartaeedb.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="watches")
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "watch_id", nullable = false, unique = true)
    private long watchId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private double price;

    @Column(name = "case_diameter")
    private double caseDiameter;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "warranty_months")
    private int warrantyMonths;

    @Column(name = "manufacturer_id")
    private long manufacturerId;
}
