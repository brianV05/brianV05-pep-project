package Service;

//Service layer will require the Model and DAO
import Model.Account;
import DAO.AccountDAO;

//inserting the list library
//import java.util.List;


public class ServiceAccount {

    //Create the AccountDAO
    public AccountDAO accountDAO;

    //no-args constructor for creating a new AuthorService with a new AuthorDAO.
    // (creating a DAO object for account implementation)
    public ServiceAccount(){
        accountDAO = new AccountDAO();
    }
     /**
     * Constructor for a ServiceAccount when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of ServiceAccount independently of AccountDAO .
     * ( instantiating the object for a DAO)
     */
    public ServiceAccount(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }



    //finish writing in the doa class\
    //we need to check if the new account already exist or not 
    public boolean accountExist(String userName){
        return accountDAO.accountExist(userName);
    }

    // This method is for processing a new User registration
    public Account createNewUser(Account account){
        //we are checking if the username is blank, the password is AT LEAST 4 charachters long, return null
        if(account.getUsername().isEmpty() || account.getPassword().length() < 4 || accountExist(account.getUsername())){
            return null;
        }
        else{
            //look back at DAO class for the name of the method you would be using
            //create the new Account
            //InsertnewUser comes from DAO class
            return accountDAO.InsertnewUser(account); 

        } 
    }
    

    public Account getAccount(Account account){
        if(accountDAO.verifyAccount(account) != null ) {
            return accountDAO.verifyAccount(account);
        }else{
            return null;

        }
       
    }


    

 
    



    
    
    
    
}
