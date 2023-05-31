package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Options.
 */
@Entity
@Table(name = "options")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_options")
    private String nomOptions;

    @OneToMany(mappedBy = "options")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chambre", "options", "gestionnaire" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Options id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomOptions() {
        return this.nomOptions;
    }

    public Options nomOptions(String nomOptions) {
        this.setNomOptions(nomOptions);
        return this;
    }

    public void setNomOptions(String nomOptions) {
        this.nomOptions = nomOptions;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setOptions(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setOptions(this));
        }
        this.reservations = reservations;
    }

    public Options reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public Options addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setOptions(this);
        return this;
    }

    public Options removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setOptions(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Options)) {
            return false;
        }
        return id != null && id.equals(((Options) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Options{" +
            "id=" + getId() +
            ", nomOptions='" + getNomOptions() + "'" +
            "}";
    }
}
