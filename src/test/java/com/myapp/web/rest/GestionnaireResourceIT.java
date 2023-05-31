package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.Gestionnaire;
import com.myapp.repository.GestionnaireRepository;
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
 * Integration tests for the {@link GestionnaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GestionnaireResourceIT {

    private static final String DEFAULT_NOM_GESTIONNAIRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_GESTIONNAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_POST_GESTIONNAIRE = "AAAAAAAAAA";
    private static final String UPDATED_POST_GESTIONNAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_GESTIONNAIRE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_GESTIONNAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_GESTIONNAIRE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_GESTIONNAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gestionnaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GestionnaireRepository gestionnaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGestionnaireMockMvc;

    private Gestionnaire gestionnaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gestionnaire createEntity(EntityManager em) {
        Gestionnaire gestionnaire = new Gestionnaire()
            .nomGestionnaire(DEFAULT_NOM_GESTIONNAIRE)
            .postGestionnaire(DEFAULT_POST_GESTIONNAIRE)
            .numeroGestionnaire(DEFAULT_NUMERO_GESTIONNAIRE)
            .emailGestionnaire(DEFAULT_EMAIL_GESTIONNAIRE);
        return gestionnaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gestionnaire createUpdatedEntity(EntityManager em) {
        Gestionnaire gestionnaire = new Gestionnaire()
            .nomGestionnaire(UPDATED_NOM_GESTIONNAIRE)
            .postGestionnaire(UPDATED_POST_GESTIONNAIRE)
            .numeroGestionnaire(UPDATED_NUMERO_GESTIONNAIRE)
            .emailGestionnaire(UPDATED_EMAIL_GESTIONNAIRE);
        return gestionnaire;
    }

    @BeforeEach
    public void initTest() {
        gestionnaire = createEntity(em);
    }

    @Test
    @Transactional
    void createGestionnaire() throws Exception {
        int databaseSizeBeforeCreate = gestionnaireRepository.findAll().size();
        // Create the Gestionnaire
        restGestionnaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionnaire)))
            .andExpect(status().isCreated());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeCreate + 1);
        Gestionnaire testGestionnaire = gestionnaireList.get(gestionnaireList.size() - 1);
        assertThat(testGestionnaire.getNomGestionnaire()).isEqualTo(DEFAULT_NOM_GESTIONNAIRE);
        assertThat(testGestionnaire.getPostGestionnaire()).isEqualTo(DEFAULT_POST_GESTIONNAIRE);
        assertThat(testGestionnaire.getNumeroGestionnaire()).isEqualTo(DEFAULT_NUMERO_GESTIONNAIRE);
        assertThat(testGestionnaire.getEmailGestionnaire()).isEqualTo(DEFAULT_EMAIL_GESTIONNAIRE);
    }

    @Test
    @Transactional
    void createGestionnaireWithExistingId() throws Exception {
        // Create the Gestionnaire with an existing ID
        gestionnaire.setId(1L);

        int databaseSizeBeforeCreate = gestionnaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGestionnaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGestionnaires() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        // Get all the gestionnaireList
        restGestionnaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gestionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomGestionnaire").value(hasItem(DEFAULT_NOM_GESTIONNAIRE)))
            .andExpect(jsonPath("$.[*].postGestionnaire").value(hasItem(DEFAULT_POST_GESTIONNAIRE)))
            .andExpect(jsonPath("$.[*].numeroGestionnaire").value(hasItem(DEFAULT_NUMERO_GESTIONNAIRE)))
            .andExpect(jsonPath("$.[*].emailGestionnaire").value(hasItem(DEFAULT_EMAIL_GESTIONNAIRE)));
    }

    @Test
    @Transactional
    void getGestionnaire() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        // Get the gestionnaire
        restGestionnaireMockMvc
            .perform(get(ENTITY_API_URL_ID, gestionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gestionnaire.getId().intValue()))
            .andExpect(jsonPath("$.nomGestionnaire").value(DEFAULT_NOM_GESTIONNAIRE))
            .andExpect(jsonPath("$.postGestionnaire").value(DEFAULT_POST_GESTIONNAIRE))
            .andExpect(jsonPath("$.numeroGestionnaire").value(DEFAULT_NUMERO_GESTIONNAIRE))
            .andExpect(jsonPath("$.emailGestionnaire").value(DEFAULT_EMAIL_GESTIONNAIRE));
    }

    @Test
    @Transactional
    void getNonExistingGestionnaire() throws Exception {
        // Get the gestionnaire
        restGestionnaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGestionnaire() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();

        // Update the gestionnaire
        Gestionnaire updatedGestionnaire = gestionnaireRepository.findById(gestionnaire.getId()).get();
        // Disconnect from session so that the updates on updatedGestionnaire are not directly saved in db
        em.detach(updatedGestionnaire);
        updatedGestionnaire
            .nomGestionnaire(UPDATED_NOM_GESTIONNAIRE)
            .postGestionnaire(UPDATED_POST_GESTIONNAIRE)
            .numeroGestionnaire(UPDATED_NUMERO_GESTIONNAIRE)
            .emailGestionnaire(UPDATED_EMAIL_GESTIONNAIRE);

        restGestionnaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGestionnaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGestionnaire))
            )
            .andExpect(status().isOk());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
        Gestionnaire testGestionnaire = gestionnaireList.get(gestionnaireList.size() - 1);
        assertThat(testGestionnaire.getNomGestionnaire()).isEqualTo(UPDATED_NOM_GESTIONNAIRE);
        assertThat(testGestionnaire.getPostGestionnaire()).isEqualTo(UPDATED_POST_GESTIONNAIRE);
        assertThat(testGestionnaire.getNumeroGestionnaire()).isEqualTo(UPDATED_NUMERO_GESTIONNAIRE);
        assertThat(testGestionnaire.getEmailGestionnaire()).isEqualTo(UPDATED_EMAIL_GESTIONNAIRE);
    }

    @Test
    @Transactional
    void putNonExistingGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();
        gestionnaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gestionnaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gestionnaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();
        gestionnaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gestionnaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();
        gestionnaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gestionnaire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGestionnaireWithPatch() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();

        // Update the gestionnaire using partial update
        Gestionnaire partialUpdatedGestionnaire = new Gestionnaire();
        partialUpdatedGestionnaire.setId(gestionnaire.getId());

        partialUpdatedGestionnaire.numeroGestionnaire(UPDATED_NUMERO_GESTIONNAIRE).emailGestionnaire(UPDATED_EMAIL_GESTIONNAIRE);

        restGestionnaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestionnaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGestionnaire))
            )
            .andExpect(status().isOk());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
        Gestionnaire testGestionnaire = gestionnaireList.get(gestionnaireList.size() - 1);
        assertThat(testGestionnaire.getNomGestionnaire()).isEqualTo(DEFAULT_NOM_GESTIONNAIRE);
        assertThat(testGestionnaire.getPostGestionnaire()).isEqualTo(DEFAULT_POST_GESTIONNAIRE);
        assertThat(testGestionnaire.getNumeroGestionnaire()).isEqualTo(UPDATED_NUMERO_GESTIONNAIRE);
        assertThat(testGestionnaire.getEmailGestionnaire()).isEqualTo(UPDATED_EMAIL_GESTIONNAIRE);
    }

    @Test
    @Transactional
    void fullUpdateGestionnaireWithPatch() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();

        // Update the gestionnaire using partial update
        Gestionnaire partialUpdatedGestionnaire = new Gestionnaire();
        partialUpdatedGestionnaire.setId(gestionnaire.getId());

        partialUpdatedGestionnaire
            .nomGestionnaire(UPDATED_NOM_GESTIONNAIRE)
            .postGestionnaire(UPDATED_POST_GESTIONNAIRE)
            .numeroGestionnaire(UPDATED_NUMERO_GESTIONNAIRE)
            .emailGestionnaire(UPDATED_EMAIL_GESTIONNAIRE);

        restGestionnaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGestionnaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGestionnaire))
            )
            .andExpect(status().isOk());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
        Gestionnaire testGestionnaire = gestionnaireList.get(gestionnaireList.size() - 1);
        assertThat(testGestionnaire.getNomGestionnaire()).isEqualTo(UPDATED_NOM_GESTIONNAIRE);
        assertThat(testGestionnaire.getPostGestionnaire()).isEqualTo(UPDATED_POST_GESTIONNAIRE);
        assertThat(testGestionnaire.getNumeroGestionnaire()).isEqualTo(UPDATED_NUMERO_GESTIONNAIRE);
        assertThat(testGestionnaire.getEmailGestionnaire()).isEqualTo(UPDATED_EMAIL_GESTIONNAIRE);
    }

    @Test
    @Transactional
    void patchNonExistingGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();
        gestionnaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gestionnaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gestionnaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();
        gestionnaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gestionnaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = gestionnaireRepository.findAll().size();
        gestionnaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGestionnaireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gestionnaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gestionnaire in the database
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGestionnaire() throws Exception {
        // Initialize the database
        gestionnaireRepository.saveAndFlush(gestionnaire);

        int databaseSizeBeforeDelete = gestionnaireRepository.findAll().size();

        // Delete the gestionnaire
        restGestionnaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, gestionnaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gestionnaire> gestionnaireList = gestionnaireRepository.findAll();
        assertThat(gestionnaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
