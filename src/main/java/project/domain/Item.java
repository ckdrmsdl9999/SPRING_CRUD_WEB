package project.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( length = 100, unique = true)
    private String itemname;
    @Column
    private Integer price;
    @Column
    private Integer quantity;





}
