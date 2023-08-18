package Controller;

//the controller will require the use of the Service layer and the Model layer
import Model.Account;  //model
import Model.Message;   //model
import Service.ServiceAccount;  //service
import Service.ServiceMessage;  //service 

import static org.mockito.ArgumentMatchers.contains;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     ServiceAccount serviceAccount = new ServiceAccount();
     ServiceMessage serviceMessage = new ServiceMessage();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postNewRegisterHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/messages", this::postNewMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAllMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessageWrittenByParticularUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    */

// Our API should be able to process new User registrations.
    private void postNewRegisterHandler(Context context) throws JsonProcessingException{
        // using Javalin, use this way
        //username and password from user input / website
        Account account = context.bodyAsClass(Account.class);  

        //query already created to be passed to the database
        //createNewUser is coming from the service class
        Account addAcc = serviceAccount.createNewUser(account);    
        
        if(addAcc != null){
            context.status(200);
            context.json(account);
        }else{
           context.status(400);  
        }    
    }

    
//Our API should be able to process User logins.
    private void postUserLoginHandler(Context context) throws JsonProcessingException{

        ObjectMapper om = new ObjectMapper();
        Account checkAcc = om.readValue(context.body(), Account.class );
        Account getAcc = serviceAccount.getAccount(checkAcc.getUsername(), checkAcc.getPassword());

        if(getAcc != null){
            context.json(om.writeValueAsString(getAcc));
            context.status(200);
        }else{
            context.status(401);
        }
        
    }

    
    //Our API should be able to process the creation of new messages.
    private void postNewMessagesHandler(Context context) {
        // Extract the message from the request body and convert it to a Message object
        Message message = context.bodyAsClass(Message.class);
        // Call a service method to post the new message and store the result
        Message creatMess = serviceMessage.postNewMessage(message);

        // Retrieve the account of the user who posted the message
        Account postedByAccount = serviceAccount.getAccountById(message.getPosted_by());

        // Check if the account of the user who posted the message exists
        if(postedByAccount == null){
            context.status(400).json("");
            return;
        }

        //validate the length of the text
        if(message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 254){
            context.status(400).json("");
            return;
        }

         // Check if the message was successfully created
        if(creatMess != null){
            context.status(200).json(creatMess);
        }else{
            context.status(400).json("");
        }
    }

//Our API should be able to retrieve all messages.
    private void getAllMessagesHandler(Context context){
        List<Message> messages = serviceMessage.getAllMessages();
        context.json(messages);
        
    }

//Our API should be able to retrieve a message by its ID.
    private void getAllMessageByIdHandler(Context context)throws JsonProcessingException{
        int messageid = Integer.parseInt(context.pathParam("message_id"));  //storing the "message_id" from the message to the variable messageid
        Message message = serviceMessage.getAllMessagesByID(messageid);          //we calling serviceMessage getAllMessagesByID method passing the parameter of messageid

        if(message != null){                                                     //if message is not empty, create it. Else print status
            context.json(message);
        }else{
            context.status(200);
        }
    }

//Our API should be able to delete a message identified by a message ID.
    private void deleteMessageByIdHandler(Context context){  
        //This line extracts the value of a path parameter named "message_id" from the context.body() and converts into Integer
        //Path parameters are typically used to pass dynamic values in a URL, such as /messages/{message_id}.
        int messageId = Integer.parseInt(context.pathParam("message_id"));

        //This method seems to handle the actual deletion of the message based on its ID. 
        Message delMessage = serviceMessage.deleteMessage(messageId);

        // If the delMessage is not null, meaning the deletion was successful 
        // and a message was returned, the code within this block will execute. 
        // the deleted message in JSON format using the context.json(delMessage) line.
        if(delMessage != null){
            context.json(delMessage);
        }else{
            
            //indicating that the deletion didn't yield a message 
            context.status(200);
        }
    }

//Our API should be able to update a message text identified by a message ID.
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int id = Integer.parseInt(context.pathParam("message_id"));

        Message updatedMessage = serviceMessage.updateMessageByID(id, message);

        if(updatedMessage == null){
            context.status(400);
        }else{
            context.json(mapper.writeValueAsString(updatedMessage));
        }

    }
     /*       
        //ObjectMapper om = new ObjectMapper();
        //Message message = om.readValue(context.body(), Message.class);

        String mess = context.body();
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = serviceMessage.updateMessageByID(id, mess);
        if(updatedMessage == null){
            context.status(400);
        }else{
            context.json(updatedMessage);
            context.status(400);
        }
 */    










//Our API should be able to retrieve all messages written by a particular user(account).
    private void getAllMessageWrittenByParticularUserHandler(Context context){              

        int userId = Integer.parseInt(context.pathParam("account_id"));                         // Extract user ID from the URL path parameter(ACCOUNT)

        // Call a service method to get all messages written by the specified user
        List<Message> MessagesFromUser = serviceMessage.getAllMessageBYUserId(userId);

        // Check if the list of messages from the user is not empty
        if(!MessagesFromUser.isEmpty()){
            context.status(200).json(MessagesFromUser);                 // Return a 200 OK response with the list of messages
        }else{
            context.status(200).json(new ArrayList<Message>());         // Return an empty list with a 200 OK response
        }
    }
  



}