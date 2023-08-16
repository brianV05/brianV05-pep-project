package Service;

import Model.Message;
//import Model.Account;
import DAO.AccountDAO;
import DAO.MessageDAO;
//import Service.ServiceAccount;



import java.util.List;



public class ServiceMessage {

    //create the MessageDAO in service
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public ServiceMessage(){
        messageDAO = new MessageDAO();
        //accountDAO = new AccountDAO();
    }

    public ServiceMessage(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }



    public Message postNewMessage(Message message){
        if(message.getMessage_text().isEmpty() || message.getMessage_text().length()< 255){
            return null;
        }else{
            return messageDAO.inserMessage(message);

        }
    }
    


    public List<Message> getAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    public Message getAllMessagesByID(int id){
        return messageDAO.retrieveAllMessagesByid(id);

    }
    




}
