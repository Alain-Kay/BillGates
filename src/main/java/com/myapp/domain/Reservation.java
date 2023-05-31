package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_reservation")
    private Integer numeroReservation;

    @Column(name = "heure_debut")
    private String heureDebut;

    @Column(name = "heure_fin")
    private String heureFin;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservations", "client" }, allowSetters = true)
    private Chambre chambre;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservations" }, allowSetters = true)
    private Options options;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservations" }, allowSetters = true)
    private Gestionnaire gestionnaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroReservation() {
        return this.numeroReservation;
    }

    public Reservation numeroReservation(Integer numeroReservation) {
        this.setNumeroReservation(numeroReservation);
        return this;
    }

    public void setNumeroReservation(Integer numeroReservation) {
        this.numeroReservation = numeroReservation;
    }

    public String getHeureDebut() {
        return this.heureDebut;
    }

    public Reservation heureDebut(String heureDebut) {
        this.setHeureDebut(heureDebut);
        return this;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return this.heureFin;
    }

    public Reservation heureFin(String heureFin) {
        this.setHeureFin(heureFin);
        return this;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public Chambre getChambre() {
        return this.chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public Reservation chambre(Chambre chambre) {
        this.setChambre(chambre);
        return this;
    }

    public Options getOptions() {
        return this.options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public Reservation options(Options options) {
        this.setOptions(options);
        return this;
    }

    public Gestionnaire getGestionnaire() {
        return this.gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public Reservation gestionnaire(Gestionnaire gestionnaire) {
        this.setGestionnaire(gestionnaire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", numeroReservation=" + getNumeroReservation() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            "}";
    }
}
