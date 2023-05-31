package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.Options;
import com.myapp.repository.OptionsRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OptionsResourceIT {

    private static final String DEFAULT_NOM_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_OPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionsMockMvc;

    private Options options;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Options createEntity(EntityManager em) {
        Options options = new Options().nomOptions(DEFAULT_NOM_OPTIONS);
        return options;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Options createUpdatedEntity(EntityManager em) {
        Options options = new Options().nomOptions(UPDATED_NOM_OPTIONS);
        return options;
    }

    @BeforeEach
    public void initTest() {
        options = createEntity(em);
    }

    @Test
    @Transactional
    void createOptions() throws Exception {
        int databaseSizeBeforeCreate = optionsRepository.findAll().size();
        // Create the Options
        restOptionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isCreated());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeCreate + 1);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getNomOptions()).isEqualTo(DEFAULT_NOM_OPTIONS);
    }

    @Test
    @Transactional
    void createOptionsWithExistingId() throws Exception {
        // Create the Options with an existing ID
        options.setId(1L);

        int databaseSizeBeforeCreate = optionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        // Get all the optionsList
        restOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(options.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomOptions").value(hasItem(DEFAULT_NOM_OPTIONS)));
    }

    @Test
    @Transactional
    void getOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        // Get the options
        restOptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, options.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(options.getId().intValue()))
            .andExpect(jsonPath("$.nomOptions").value(DEFAULT_NOM_OPTIONS));
    }

    @Test
    @Transactional
    void getNonExistingOptions() throws Exception {
        // Get the options
        restOptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Update the options
        Options updatedOptions = optionsRepository.findById(options.getId()).get();
        // Disconnect from session so that the updates on updatedOptions are not directly saved in db
        em.detach(updatedOptions);
        updatedOptions.nomOptions(UPDATED_NOM_OPTIONS);

        restOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOptions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOptions))
            )
            .andExpect(status().isOk());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getNomOptions()).isEqualTo(UPDATED_NOM_OPTIONS);
    }

    @Test
    @Transactional
    void putNonExistingOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();
        options.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, options.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(options))
            )
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();
        options.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(options))
            )
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();
        options.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionsWithPatch() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Update the options using partial update
        Options partialUpdatedOptions = new Options();
        partialUpdatedOptions.setId(options.getId());

        restOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptions))
            )
            .andExpect(status().isOk());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getNomOptions()).isEqualTo(DEFAULT_NOM_OPTIONS);
    }

    @Test
    @Transactional
    void fullUpdateOptionsWithPatch() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Update the options using partial update
        Options partialUpdatedOptions = new Options();
        partialUpdatedOptions.setId(options.getId());

        partialUpdatedOptions.nomOptions(UPDATED_NOM_OPTIONS);

        restOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptions))
            )
            .andExpect(status().isOk());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getNomOptions()).isEqualTo(UPDATED_NOM_OPTIONS);
    }

    @Test
    @Transactional
    void patchNonExistingOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();
        options.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, options.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(options))
            )
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();
        options.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(options))
            )
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();
        options.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(options)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeDelete = optionsRepository.findAll().size();

        // Delete the options
        restOptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, options.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
