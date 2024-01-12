package com.ehei;

public class App {
	public static void main(String[] args) {
		// Créer une instance de ProduitService
		ProduitService produitService = new ProduitService();

		// Exemple : Ajout de produits
		try {
			Produit produit1 = new Produit(1L, "Ordinateur portable", 999.99, 5);
			produitService.createProduit(produit1);

			Produit produit2 = new Produit(2L, "Smartphone", 799.99, 10);
			produitService.createProduit(produit2);

			Produit produit3 = new Produit(3L, "Tablette", 299.99, 8);
			produitService.createProduit(produit3);
		} catch (ProduitService.UniciteProduitException | ProduitService.ValidationDonneesException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		// Exemple : Récupération de produits
		try {
			Produit produitRecupere = produitService.readProduit(1L);
			System.out.println("Produit récupéré : " + produitRecupere);

			// Tentative de récupérer un produit qui n'existe pas
			produitService.readProduit(4L); // Cela devrait générer ProduitInexistantException
		} catch (ProduitService.ProduitInexistantException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		// Exemple : Mise à jour d'un produit
		try {
			Produit produitMisAJour = new Produit(1L, "Ordinateur portable gaming", 1299.99, 3);
			produitService.updateProduit(produitMisAJour);
			System.out.println("Produit mis à jour : " + produitService.readProduit(1L));
		} catch (ProduitService.ProduitInexistantException | ProduitService.ValidationDonneesException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		// Exemple : Suppression d'un produit
		try {
			produitService.deleteProduit(2L);
			System.out.println("Produit supprimé. Total des produits : " + produitService.getProduits().size());

			// Tentative de supprimer un produit qui n'existe pas
			produitService.deleteProduit(4L); // Cela devrait générer ProduitInexistantException
		} catch (ProduitService.ProduitInexistantException e) {
			System.err.println("Erreur : " + e.getMessage());
		}

		// Exemple : Liste de tous les produits
		System.out.println("Tous les produits : " + produitService.getProduits());
	}
}
