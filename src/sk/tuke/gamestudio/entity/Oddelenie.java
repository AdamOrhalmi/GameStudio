package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Oddelenie {
    @Id
    @GeneratedValue
    int ident;
    private String meno;
    @OneToMany(mappedBy = "oddelenie")
    //@JoinColumn(name = "ident_zamestnanec")
    private Set<Zamestnanec> zamestnanci;
}
