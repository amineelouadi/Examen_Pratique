package com.ehei;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

	private ProduitService produitService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeClass - Configuration initiale avant tous les tests");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AfterClass - Nettoyage après tous les tests");
	}

	@Before
	public void setUp() throws Exception {
		produitService = new ProduitService();
		System.out.println("Before - Configuration avant chaque test");
	}

	@After
	public void tearDown() throws Exception {
		produitService = null;
		System.out.println("After - Nettoyage après chaque test");
	}

	@Test
	public void testCreateProduit() {
		Produit produit = new Produit(1L, "Ordinateur", 999.99, 5);

		try {
			produitService.createProduit(produit);
		} catch (ProduitService.UniciteProduitException | ProduitService.ValidationDonneesException e) {
			fail("Exception inattendue : " + e.getMessage());
		}
		assertEquals(1, produitService.getProduits().size());

	}

	@Test
	public void testCreatDuplicatProduct() {
		produitService.getProduits().clear();
		Produit produitDuplique = new Produit(1L, "Laptop", 899.99, 3);
		try {
			produitService.createProduit(produitDuplique);
			produitService.createProduit(produitDuplique);
		} catch (ProduitService.UniciteProduitException ignored) {
			// System.err.println(ignored.getMessage());
		}

		catch (ProduitService.ValidationDonneesException e) {
			fail("Exception de ValidationDonneesException inattendue : " + e.getMessage());
		}
		assertEquals(1, produitService.getProduits().size());
	}

	@Test
	public void testInvalidData() {
		produitService.getProduits().clear();
		Produit produitPrixNegatif = new Produit(2L, "Smartphone", -199.99, 10);
		try {
			produitService.createProduit(produitPrixNegatif);
			fail("ValidationDonneesException attendue, mais elle n'a pas été levée");
		} catch (ProduitService.UniciteProduitException e) {
			fail("Exception UniciteProduitException inattendue : " + e.getMessage());
		} catch (ProduitService.ValidationDonneesException ignored) {

		}
		assertEquals(0, produitService.getProduits().size());
	}

	@Test
	public void testReadProduit() {
		Produit produitExistant = new Produit(3L, "Tablette", 299.99, 8);
		produitService.getProduits().add(produitExistant);

		try {
			Produit readProduit = produitService.readProduit(3L);
			assertNotNull(readProduit);
			assertEquals(produitExistant, readProduit);
		} catch (ProduitService.ProduitInexistantException e) {
			fail("Exception inattendue de ProduitInexistantException : " + e.getMessage());
		}

		try {
			produitService.readProduit(4L);
			fail("ProduitInexistantException attendue, mais elle n'a pas été levée");
		} catch (ProduitService.ProduitInexistantException ignored) {

		}
	}

	@Test
	public void testUpdateProduit() throws ProduitService.ProduitInexistantException {
		Produit produitExistant = new Produit(4L, "Casque audio", 79.99, 15);
		produitService.getProduits().add(produitExistant);

		Produit produitMisAJour = new Produit(4L, "Casque sans fil", 99.99, 20);
		try {
			produitService.updateProduit(produitMisAJour);
		} catch (ProduitService.ProduitInexistantException | ProduitService.ValidationDonneesException e) {
			fail("Exception inattendue : " + e.getMessage());
		}
		assertEquals("Casque sans fil", produitService.readProduit(4L).getNom());

		Produit produitInexistant = new Produit(5L, "Enceinte Bluetooth", 129.99, 10);
		try {
			produitService.updateProduit(produitInexistant);
			fail("ProduitInexistantException attendue, mais elle n'a pas été levée");
		} catch (ProduitService.ProduitInexistantException ignored) {

		} catch (ProduitService.ValidationDonneesException e) {
			fail("Exception de ValidationDonneesException inattendue : " + e.getMessage());
		}

		Produit produitPrixNegatif = new Produit(4L, "Casque sans fil", -49.99, 10);
		try {
			produitService.updateProduit(produitPrixNegatif);
			fail("ValidationDonneesException attendue, mais elle n'a pas été levée");
		} catch (ProduitService.ProduitInexistantException ignored) {
			fail("Exception de ProduitInexistantException inattendue : " + ignored.getMessage());
		} catch (ProduitService.ValidationDonneesException ignored) {

		}
	}

	@Test
	public void testDeleteProduit() {
		produitService.getProduits().clear();
		Produit produitExistant = new Produit(6L, "Imprimante", 199.99, 5);
		produitService.getProduits().add(produitExistant);

		try {
			produitService.deleteProduit(6L);
		} catch (ProduitService.ProduitInexistantException e) {
			fail("Exception inattendue de ProduitInexistantException : " + e.getMessage());
		}
		assertEquals(0, produitService.getProduits().size());

		// Ajout d'une impression pour voir le contenu de la liste avant l'appel de
		// deleteProduit
		System.out.println("Contenu de la liste produits avant deleteProduit : " + produitService.getProduits());

		try {
			produitService.deleteProduit(7L);
			fail("ProduitInexistantException attendue, mais elle n'a pas été levée");
		} catch (ProduitService.ProduitInexistantException ignored) {
			// Ajout d'une impression pour indiquer que l'exception a été levée (comme
			// prévu)
			System.out.println("ProduitInexistantException a été levée comme prévu");
		}

		// Ajout d'une impression pour voir le contenu de la liste après l'appel de
		// deleteProduit
		System.out.println("Contenu de la liste produits après deleteProduit : " + produitService.getProduits());
	}

	@Test
	public void testGetProduits() {
		produitService.getProduits().clear();
		Produit produit1 = new Produit(8L, "Souris", 19.99, 30);
		Produit produit2 = new Produit(9L, "Clavier", 49.99, 15);
		produitService.getProduits().add(produit1);
		produitService.getProduits().add(produit2);

		List<Produit> produits = new ArrayList<>(produitService.getProduits());

		System.out.println("Taille de la liste produits : " + produits.size());
		System.out.println("Contenu de la liste produits : " + produits);

		System.out.println(produits.size());
		assertEquals(2, produits.size());
		assertTrue(produits.contains(produit1));
		assertTrue(produits.contains(produit2));
	}

}
