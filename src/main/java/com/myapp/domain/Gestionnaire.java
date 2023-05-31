package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gestionnaire.
 */
@Entity
@Table(name = "gestionnaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gestionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_gestionnaire")
    private String nomGestionnaire;

    @Column(name = "post_gestionnaire")
    private String postGestionnaire;

    @Column(name = "numero_gestionnaire")
    private String numeroGestionnaire;

    @Column(name = "email_gestionnaire")
    private String emailGestionnaire;

    @OneToMany(mappedBy = "gestionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chambre", "options", "gestionnaire" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gestionnaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomGestionnaire() {
        return this.nomGestionnaire;
    }

    public Gestionnaire nomGestionnaire(String nomGestionnaire) {
        this.setNomGestionnaire(nomGestionnaire);
        return this;
    }

    public void setNomGestionnaire(String nomGestionnaire) {
        this.nomGestionnaire = nomGestionnaire;
    }

    public String getPostGestionnaire() {
        return this.postGestionnaire;
    }

    public Gestionnaire postGestionnaire(String postGestionnaire) {
        this.setPostGestionnaire(postGestionnaire);
        return this;
    }

    public void setPostGestionnaire(String postGestionnaire) {
        this.postGestionnaire = postGestionnaire;
    }

    public String getNumeroGestionnaire() {
        return this.numeroGestionnaire;
    }

    public Gestionnaire numeroGestionnaire(String numeroGestionnaire) {
        this.setNumeroGestionnaire(numeroGestionnaire);
        return this;
    }

    public void setNumeroGestionnaire(String numeroGestionnaire) {
        this.numeroGestionnaire = numeroGestionnaire;
    }

    public String getEmailGestionnaire() {
        return this.emailGestionnaire;
    }

    public Gestionnaire emailGestionnaire(String emailGestionnaire) {
        this.setEmailGestionnaire(emailGestionnaire);
        return this;
    }

    public void setEmailGestionnaire(String emailGestionnaire) {
        this.emailGestionnaire = emailGestionnaire;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setGestionnaire(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setGestionnaire(this));
        }
        this.reservations = reservations;
    }

    public Gestionnaire reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public Gestionnaire addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setGestionnaire(this);
        return this;
    }

    public Gestionnaire removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setGestionnaire(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gestionnaire)) {
            return false;
        }
        return id != null && id.equals(((Gestionnaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gestionnaire{" +
            "id=" + getId() +
            ", nomGestionnaire='" + getNomGestionnaire() + "'" +
            ", postGestionnaire='" + getPostGestionnaire() + "'" +
            ", numeroGestionnaire='" + getNumeroGestionnaire() + "'" +
            ", emailGestionnaire='" + getEmailGestionnaire() + "'" +
            "}";
    }
}
