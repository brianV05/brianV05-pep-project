package Service;

import Model.Message;
import Model.Account;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Service.ServiceAccount;



import java.util.List;



public class ServiceMessage {

    //create the MessageDAO in service
    public MessageDAO messageDAO;
    //public AccountDAO accountDAO;

    public ServiceMessage(){
        messageDAO = new MessageDAO();
        //accountDAO = new AccountDAO();
    }
/* 
    public ServiceMessage(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }
*/

/* 
    public Message postNewMessage(Message message){
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length()< 255 || message.getPosted_by() != accountDAO.getAccountById(message.getPosted_by())){
            return null;
        }else{
            return messageDAO.inserMessage(message);

        }
    }
  //*/  


    public List<Message> getAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    public Message getAllMessagesByID(int id){
        return messageDAO.retrieveAllMessagesByid(id);

    }

    public Message deleteMessage(int messageid){
        Message deleteMess = messageDAO.retrieveAllMessagesByid(messageid);

        if(deleteMess != null){
            boolean success = messageDAO.deleteMessageByID(messageid);
            if(success){
                return deleteMess;
            }
        }
        return null;
    }

















    public Message updateMessageByID(int id, Message messages){

        Message existing = messageDAO.retrieveAllMessagesByid(id);
        if(messages.getMessage_text().isEmpty()|| messages.getMessage_text().length() > 255 || existing != null){
            return messageDAO.updateMessage(id, messages);
        }else{
            return existing;
        }

      

        
        //this.messageDAO.updateMessage(id, existing);
        //return existing;
        /* 
        if(messages.length() == 0 || messages.length() > 255  ){
            return messageDAO.updateMessage(id, messages);
        }else{
            return null;
        }
        */
      
        
    }








    

    
}
    





