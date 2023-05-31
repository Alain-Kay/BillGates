package com.myapp.web.rest;

import com.myapp.domain.Gestionnaire;
import com.myapp.repository.GestionnaireRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myapp.domain.Gestionnaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GestionnaireResource {

    private final Logger log = LoggerFactory.getLogger(GestionnaireResource.class);

    private static final String ENTITY_NAME = "gestionnaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GestionnaireRepository gestionnaireRepository;

    public GestionnaireResource(GestionnaireRepository gestionnaireRepository) {
        this.gestionnaireRepository = gestionnaireRepository;
    }

    /**
     * {@code POST  /gestionnaires} : Create a new gestionnaire.
     *
     * @param gestionnaire the gestionnaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gestionnaire, or with status {@code 400 (Bad Request)} if the gestionnaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gestionnaires")
    public ResponseEntity<Gestionnaire> createGestionnaire(@RequestBody Gestionnaire gestionnaire) throws URISyntaxException {
        log.debug("REST request to save Gestionnaire : {}", gestionnaire);
        if (gestionnaire.getId() != null) {
            throw new BadRequestAlertException("A new gestionnaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gestionnaire result = gestionnaireRepository.save(gestionnaire);
        return ResponseEntity
            .created(new URI("/api/gestionnaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gestionnaires/:id} : Updates an existing gestionnaire.
     *
     * @param id the id of the gestionnaire to save.
     * @param gestionnaire the gestionnaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionnaire,
     * or with status {@code 400 (Bad Request)} if the gestionnaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gestionnaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gestionnaires/{id}")
    public ResponseEntity<Gestionnaire> updateGestionnaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gestionnaire gestionnaire
    ) throws URISyntaxException {
        log.debug("REST request to update Gestionnaire : {}, {}", id, gestionnaire);
        if (gestionnaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestionnaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestionnaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gestionnaire result = gestionnaireRepository.save(gestionnaire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionnaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gestionnaires/:id} : Partial updates given fields of an existing gestionnaire, field will ignore if it is null
     *
     * @param id the id of the gestionnaire to save.
     * @param gestionnaire the gestionnaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gestionnaire,
     * or with status {@code 400 (Bad Request)} if the gestionnaire is not valid,
     * or with status {@code 404 (Not Found)} if the gestionnaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the gestionnaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gestionnaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gestionnaire> partialUpdateGestionnaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gestionnaire gestionnaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gestionnaire partially : {}, {}", id, gestionnaire);
        if (gestionnaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gestionnaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gestionnaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gestionnaire> result = gestionnaireRepository
            .findById(gestionnaire.getId())
            .map(existingGestionnaire -> {
                if (gestionnaire.getNomGestionnaire() != null) {
                    existingGestionnaire.setNomGestionnaire(gestionnaire.getNomGestionnaire());
                }
                if (gestionnaire.getPostGestionnaire() != null) {
                    existingGestionnaire.setPostGestionnaire(gestionnaire.getPostGestionnaire());
                }
                if (gestionnaire.getNumeroGestionnaire() != null) {
                    existingGestionnaire.setNumeroGestionnaire(gestionnaire.getNumeroGestionnaire());
                }
                if (gestionnaire.getEmailGestionnaire() != null) {
                    existingGestionnaire.setEmailGestionnaire(gestionnaire.getEmailGestionnaire());
                }

                return existingGestionnaire;
            })
            .map(gestionnaireRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gestionnaire.getId().toString())
        );
    }

    /**
     * {@code GET  /gestionnaires} : get all the gestionnaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gestionnaires in body.
     */
    @GetMapping("/gestionnaires")
    public List<Gestionnaire> getAllGestionnaires() {
        log.debug("REST request to get all Gestionnaires");
        return gestionnaireRepository.findAll();
    }

    /**
     * {@code GET  /gestionnaires/:id} : get the "id" gestionnaire.
     *
     * @param id the id of the gestionnaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gestionnaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gestionnaires/{id}")
    public ResponseEntity<Gestionnaire> getGestionnaire(@PathVariable Long id) {
        log.debug("REST request to get Gestionnaire : {}", id);
        Optional<Gestionnaire> gestionnaire = gestionnaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gestionnaire);
    }

    /**
     * {@code DELETE  /gestionnaires/:id} : delete the "id" gestionnaire.
     *
     * @param id the id of the gestionnaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gestionnaires/{id}")
    public ResponseEntity<Void> deleteGestionnaire(@PathVariable Long id) {
        log.debug("REST request to delete Gestionnaire : {}", id);
        gestionnaireRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
