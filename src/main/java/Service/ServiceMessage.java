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

/*

    public Message postNewMessage(Message message){
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length()< 255 || message.getPosted_by() != accountDAO.getAccountById(message.getPosted_by())){
            return null;
        }else{
            return messageDAO.inserMessage(message);

        }
    }
  */  


    public List<Message> getAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    public Message getAllMessagesByID(int id){
        return messageDAO.retrieveAllMessagesByid(id);

    }

    public Message updateMessageByID(int id, String message){
       
        if(message.length() == 0 || message.length() > 255){
            return messageDAO.updateMessage(id, message);
        }else{
            return null;
        }
      
        
    }








    
/* 

    if(message != null && !message.trim().isEmpty()){
        Message exitMessage = messageDAO.retrieveAllMessagesByid(id);
        
        if(exitMessage != null){
            exitMessage.setMessage_text(message);
            return messageDAO.updateMessage(exitMessage);
        }
    }
    return null;
    */
  
    
}
    





