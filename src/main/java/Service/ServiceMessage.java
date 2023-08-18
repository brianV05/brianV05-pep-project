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

        Message existing = messageDAO.retrieveAllMessagesByid(id);                                               
        if(messages.getMessage_text().isEmpty()|| messages.getMessage_text().length() > 254 || existing == null){
            return messageDAO.updateMessage(id, messages);
        }else{
            return null;
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
    





