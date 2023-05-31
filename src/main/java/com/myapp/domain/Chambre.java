package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myapp.domain.enumeration.Disponibilite;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Chambre.
 */
@Entity
@Table(name = "chambre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Chambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_chambre")
    private String numeroChambre;

    @Column(name = "prix_chambre")
    private Double prixChambre;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilite")
    private Disponibilite disponibilite;

    @Column(name = "images")
    private String images;

    @OneToMany(mappedBy = "chambre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chambre", "options", "gestionnaire" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "chambres" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Chambre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroChambre() {
        return this.numeroChambre;
    }

    public Chambre numeroChambre(String numeroChambre) {
        this.setNumeroChambre(numeroChambre);
        return this;
    }

    public void setNumeroChambre(String numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public Double getPrixChambre() {
        return this.prixChambre;
    }

    public Chambre prixChambre(Double prixChambre) {
        this.setPrixChambre(prixChambre);
        return this;
    }

    public void setPrixChambre(Double prixChambre) {
        this.prixChambre = prixChambre;
    }

    public Disponibilite getDisponibilite() {
        return this.disponibilite;
    }

    public Chambre disponibilite(Disponibilite disponibilite) {
        this.setDisponibilite(disponibilite);
        return this;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getImages() {
        return this.images;
    }

    public Chambre images(String images) {
        this.setImages(images);
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setChambre(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setChambre(this));
        }
        this.reservations = reservations;
    }

    public Chambre reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public Chambre addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setChambre(this);
        return this;
    }

    public Chambre removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setChambre(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Chambre client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chambre)) {
            return false;
        }
        return id != null && id.equals(((Chambre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chambre{" +
            "id=" + getId() +
            ", numeroChambre='" + getNumeroChambre() + "'" +
            ", prixChambre=" + getPrixChambre() +
            ", disponibilite='" + getDisponibilite() + "'" +
            ", images='" + getImages() + "'" +
            "}";
    }
}
