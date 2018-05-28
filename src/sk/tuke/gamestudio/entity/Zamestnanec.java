package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Zamestnanec {
    @Id
    @GeneratedValue
    int ident;
    private String meno;
    @ManyToOne
    private Oddelenie oddelenie;
}
