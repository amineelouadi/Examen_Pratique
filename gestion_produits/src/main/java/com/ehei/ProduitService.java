package com.ehei;
import java.util.*;


public class ProduitService 
{
	private static List<Produit> produits = new ArrayList<>();
	public List<Produit> getProduits() 
	{
        return produits;
    }
	//Méthode pour vérifier l'unicité du produit par ID
    private boolean produitExiste(Long id) 
    {
        for (Produit produit : produits) 
        {
            if (produit.getId().equals(id)) 
            {
                return true;
            }
        }
        return false;
    }

    //Méthode pour vérifier l'unicité du nom d'un produit
    private boolean produitExiste(String nom) 
    {
        for (Produit produit : produits) 
        {
            if (produit.getNom().equals(nom)) 
            {
                return true;
            }
        }
        return false;
    }

    //exception pour produit inexistant
    public static class ProduitInexistantException extends Exception
    {
        public ProduitInexistantException(String message) 
        {
            super(message);
        }
    }

    //exception pour unicite produit
    public static class UniciteProduitException extends Exception 
    {
        public UniciteProduitException(String message) 
        {
            super(message);
        }
    }

    //exception pour validation donnees
    public static class ValidationDonneesException extends Exception 
    {
        public ValidationDonneesException(String message) 
        {
            super(message);
        }
    }


    // Method pour créer un produit
    public void createProduit(Produit produit) throws UniciteProduitException, ValidationDonneesException 
    {
        // Verification d'unicité par ID
        if (produitExiste(produit.getId()) || produitExiste(produit.getNom())) 
        {
            throw new UniciteProduitException("Produit avec le même ID ou nom déjà existant.");
        } else if (produit.getPrix() <= 0 || produit.getQuantite() <= 0) 
        {
            throw new ValidationDonneesException("Le prix et la quantité doivent être positifs.");
        } else {
            produits.add(produit);
        }
    }
<<<<<<< HEAD
 // Méthode pour lire un produit
    public Produit readProduit(Long id) throws ProduitInexistantException 
    {
        Produit foundProduit = null;
        for (Produit produit : produits) 
        {
            if (produit.getId().equals(id)) 
            {
                foundProduit = produit;
                break;
            }
        }
        if (foundProduit == null) 
        {
            throw new ProduitInexistantException("Produit inexistant.");
        }
        return foundProduit;
    }
=======
>>>>>>> develop
}
