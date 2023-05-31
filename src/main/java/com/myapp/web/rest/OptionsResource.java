package com.myapp.web.rest;

import com.myapp.domain.Options;
import com.myapp.repository.OptionsRepository;
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
 * REST controller for managing {@link com.myapp.domain.Options}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OptionsResource {

    private final Logger log = LoggerFactory.getLogger(OptionsResource.class);

    private static final String ENTITY_NAME = "options";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionsRepository optionsRepository;

    public OptionsResource(OptionsRepository optionsRepository) {
        this.optionsRepository = optionsRepository;
    }

    /**
     * {@code POST  /options} : Create a new options.
     *
     * @param options the options to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new options, or with status {@code 400 (Bad Request)} if the options has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/options")
    public ResponseEntity<Options> createOptions(@RequestBody Options options) throws URISyntaxException {
        log.debug("REST request to save Options : {}", options);
        if (options.getId() != null) {
            throw new BadRequestAlertException("A new options cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Options result = optionsRepository.save(options);
        return ResponseEntity
            .created(new URI("/api/options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /options/:id} : Updates an existing options.
     *
     * @param id the id of the options to save.
     * @param options the options to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated options,
     * or with status {@code 400 (Bad Request)} if the options is not valid,
     * or with status {@code 500 (Internal Server Error)} if the options couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/options/{id}")
    public ResponseEntity<Options> updateOptions(@PathVariable(value = "id", required = false) final Long id, @RequestBody Options options)
        throws URISyntaxException {
        log.debug("REST request to update Options : {}, {}", id, options);
        if (options.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, options.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Options result = optionsRepository.save(options);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, options.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /options/:id} : Partial updates given fields of an existing options, field will ignore if it is null
     *
     * @param id the id of the options to save.
     * @param options the options to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated options,
     * or with status {@code 400 (Bad Request)} if the options is not valid,
     * or with status {@code 404 (Not Found)} if the options is not found,
     * or with status {@code 500 (Internal Server Error)} if the options couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/options/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Options> partialUpdateOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Options options
    ) throws URISyntaxException {
        log.debug("REST request to partial update Options partially : {}, {}", id, options);
        if (options.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, options.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Options> result = optionsRepository
            .findById(options.getId())
            .map(existingOptions -> {
                if (options.getNomOptions() != null) {
                    existingOptions.setNomOptions(options.getNomOptions());
                }

                return existingOptions;
            })
            .map(optionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, options.getId().toString())
        );
    }

    /**
     * {@code GET  /options} : get all the options.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of options in body.
     */
    @GetMapping("/options")
    public List<Options> getAllOptions() {
        log.debug("REST request to get all Options");
        return optionsRepository.findAll();
    }

    /**
     * {@code GET  /options/:id} : get the "id" options.
     *
     * @param id the id of the options to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the options, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/options/{id}")
    public ResponseEntity<Options> getOptions(@PathVariable Long id) {
        log.debug("REST request to get Options : {}", id);
        Optional<Options> options = optionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(options);
    }

    /**
     * {@code DELETE  /options/:id} : delete the "id" options.
     *
     * @param id the id of the options to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/options/{id}")
    public ResponseEntity<Void> deleteOptions(@PathVariable Long id) {
        log.debug("REST request to delete Options : {}", id);
        optionsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
