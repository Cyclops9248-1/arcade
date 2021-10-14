package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArcadeDAO {
	
	public static void insertUtilisateur(String Nom, String Surnom, int Credits, int Points) throws ClassNotFoundException, SQLException
	{
		String sql="insert into arcade(Nom,Surnom,Credits,Points) values('"+Nom+"','"+Surnom+"','"+Credits+"','"+Points+"')";
		try
		{ 
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur à l'insertion de données " + e);
			e.printStackTrace();
			throw e;
		}
	}
	public static void updateUtilisateur(int ID, String Nom, String Surnom, int Credits, int Points) throws ClassNotFoundException, SQLException
	{
		String sql="update arcade set Nom='"+Nom+"', Surnom='"+Surnom+"', Credits='"+Credits+"', Points='"+Points+"' where ID= "+ ID;
		
		try
		{
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la mise à jour");
			e.printStackTrace();
			throw e;
		}
		
	}
	public static void deleteUtilisateurById(int id) throws ClassNotFoundException, SQLException
	{
		String sql="delete from arcade where ID= "+ id;
		try
		{
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la suppression de données");
			e.printStackTrace();
			throw e;
		}
	}
	public static ObservableList<Utilisateur> getAllRecords() throws ClassNotFoundException, SQLException
	{
		String sql="select * from etudiant";
		try
		{
			ResultSet rsSet=DBUtilitaires.dbExecute(sql);
			
			
			ObservableList<Utilisateur> EtudiantList=getUtilisateurObjects(rsSet);
			return EtudiantList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la recupération de données à afficher"+e);
			e.printStackTrace();
			throw e;
		}
		
	}
	private static ObservableList<Utilisateur> getUtilisateurObjects(ResultSet rsSet) throws ClassNotFoundException, SQLException
	{
		try
		{
			
			ObservableList<Utilisateur> UtilisateurList=FXCollections.observableArrayList();
			while(rsSet.next())
			{
				Utilisateur utilisateur=new Utilisateur();
				utilisateur.setID(rsSet.getInt("ID"));
				utilisateur.setNom(rsSet.getString("FirstName"));
				utilisateur.setSurnom(rsSet.getString("LastName"));
				utilisateur.setCredits(rsSet.getInt("Department"));
				utilisateur.setPoints(rsSet.getInt("Age"));
				UtilisateurList.add(utilisateur);
			}
			return UtilisateurList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur au moment de l'affichage de données "+ e);
			e.printStackTrace();
			throw e;
		}
	}
	public static ObservableList<Utilisateur> searchEtudiant(String etudiantId) throws ClassNotFoundException, SQLException
	{
		String sql="select * from etudiant where id="+etudiantId;
		try
		{
		 ResultSet rsSet=DBUtilitaires.dbExecute(sql)	;
		 ObservableList<Utilisateur> list=getUtilisateurObjects(rsSet);
		 return list;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur pendant la recherche de données " +e);
			e.printStackTrace();
			throw e;
		}
	}	
	
	
	
	
	
	
	

}
