package pl.damian.beautyglow.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="treatment")
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="duration")
    private int duration;

    @Column(name="cost")
    private int cost;

    @OneToMany(mappedBy = "treatment")
    List<UsersTreatments> usersTreatments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<UsersTreatments> getUsersTreatments() {
        return usersTreatments;
    }

    public void setUsersTreatments(List<UsersTreatments> usersTreatments) {
        this.usersTreatments = usersTreatments;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", cost=" + cost +
                '}';
    }
}
