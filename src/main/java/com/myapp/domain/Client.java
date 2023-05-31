package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_client")
    private String nomClient;

    @Column(name = "post_nom")
    private String postNom;

    @Column(name = "numero_client")
    private String numeroClient;

    @Column(name = "email_client")
    private String emailClient;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservations", "client" }, allowSetters = true)
    private Set<Chambre> chambres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClient() {
        return this.nomClient;
    }

    public Client nomClient(String nomClient) {
        this.setNomClient(nomClient);
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPostNom() {
        return this.postNom;
    }

    public Client postNom(String postNom) {
        this.setPostNom(postNom);
        return this;
    }

    public void setPostNom(String postNom) {
        this.postNom = postNom;
    }

    public String getNumeroClient() {
        return this.numeroClient;
    }

    public Client numeroClient(String numeroClient) {
        this.setNumeroClient(numeroClient);
        return this;
    }

    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public String getEmailClient() {
        return this.emailClient;
    }

    public Client emailClient(String emailClient) {
        this.setEmailClient(emailClient);
        return this;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public Set<Chambre> getChambres() {
        return this.chambres;
    }

    public void setChambres(Set<Chambre> chambres) {
        if (this.chambres != null) {
            this.chambres.forEach(i -> i.setClient(null));
        }
        if (chambres != null) {
            chambres.forEach(i -> i.setClient(this));
        }
        this.chambres = chambres;
    }

    public Client chambres(Set<Chambre> chambres) {
        this.setChambres(chambres);
        return this;
    }

    public Client addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setClient(this);
        return this;
    }

    public Client removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nomClient='" + getNomClient() + "'" +
            ", postNom='" + getPostNom() + "'" +
            ", numeroClient='" + getNumeroClient() + "'" +
            ", emailClient='" + getEmailClient() + "'" +
            "}";
    }
}
