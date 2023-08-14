package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;



public class ServiceMessage {

    //create the MessageDAO in service
    public MessageDAO messageDAO;

    public ServiceMessage(){
        messageDAO = new MessageDAO();
    }

    


    public List<Message> getAllMessages(){
        return messageDAO
    }
    




}
