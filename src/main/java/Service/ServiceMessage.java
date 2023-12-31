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

    public Message postNewMessage(Message message){
        return messageDAO.inserMessage(message);
    }
  
    public List<Message> getAllMessages(){
        return messageDAO.retrieveAllMessages();
    }

    public Message getAllMessagesByID(int id){
        return messageDAO.retrieveAllMessagesByid(id);

    }

    public Message deleteMessage(int messageid){
        Message deleteMess = messageDAO.retrieveAllMessagesByid(messageid);                 //retrieves the message with the specified messageid

        if(deleteMess != null){                                                             // If a message was successfully retrieved with the given messageid
            boolean success = messageDAO.deleteMessageByID(messageid);                      // handle the actual deletion of the message. The result of this deletion operation is stored in the success variable.
            if(success){                                                                    // If the deletion was successful                                                              
                return deleteMess;                                                          // This line returns the deleted message that was stored in the deleteMess variable. 
            }
        }
        return null;                                                                        //  the deletion operation was not successful
    }





//Message messages, that represents the new version of the message.
    public Message updateMessageByID(int id, Message messages){

        // f the message_text in the messages object is blank || f the length of the message_text is greater than or equal to 255 characters || If there is no message with the specified id found in the data store
        if(messages.getMessage_text().isBlank() || messages.getMessage_text().length() >= 255 || messageDAO.retrieveAllMessagesByid(id)== null){
            return null;
        }else{
            this.messageDAO.updateMessage(id, messages.getMessage_text());                      // it update the message text associated with the given id.
            return messageDAO.retrieveAllMessagesByid(id);                                      // After updating the message text, it retrieve the updated message from the data base.
        }
    }
    


    public List<Message> getAllMessageBYUserId(int id){
        return messageDAO.getUserID(id);
    }    
}
    





