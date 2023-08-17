package Controller;

//the controller will require the use of the Service layer and the Model layer
import Model.Account;  //model
import Model.Message;   //model
import Service.ServiceAccount;  //service
import Service.ServiceMessage;  //service 

//import DAO.AccountDAO;

import java.sql.*;
import java.util.List;
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

     //initialize serviceAccount and sericeAccount to the Controller
     //AccountDAO accountDAO = new AccountDAO();

     ServiceAccount serviceAccount = new ServiceAccount();
     ServiceMessage serviceMessage = new ServiceMessage();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postNewRegisterHandler);
        app.post("/login", this::postUserLoginHandler);
        //app.post("/messages", this::postNewMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAllMessageByIdHandler);
        //app.delete("/messaged/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        //app.get("/accounts/{account_id}/messages", this::getAllMessageWrittenByParticularUserHandler);


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
        //ObjectMapper om = new ObjectMapper();
        //Account acc = om.readValue(context.body(), Account.class);

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




/* 
  
    private void postNewMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message newMess = om.readValue(context.body(), Message.class);
        Message addMess = serviceMessage.postNewMessage(newMess);

        if(addMess != null){
            context.json(om.writeValueAsString(addMess));
            context.status(200);
        }else{
            context.status(400);
        }

        
    }
*/





    private void getAllMessagesHandler(Context context){
        List<Message> messages = serviceMessage.getAllMessages();
        context.json(messages);
        
    }

    private void getAllMessageByIdHandler(Context context)throws JsonProcessingException{
        int messageid = Integer.parseInt(context.pathParam("message_id"));  //storing the "message_id" from the message to the variable messageid
        Message message = serviceMessage.getAllMessagesByID(messageid);          //we calling serviceMessage getAllMessagesByID method passing the parameter of messageid

        if(message != null){                                                     //if message is not empty, create it. Else print status
            context.json(message);
        }else{
            context.status(200);
        }

        
    }
  /*
    private void deleteMessageByIdHandler(Context context){   

    }
*/



    private void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        /* 
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);

        int id = Integer.parseInt(context.pathParam("message_id"));
        Message update = serviceMessage.updateMessageByID(id, message);

        if(update == null){
            context.status(400);
            

        }else{
            context.json(om.writeValueAsString(update));
        }
        */
        

        
        String updatedText = context.body();
        int getID = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = serviceMessage.updateMessageByID(getID, updatedText);
        if(updatedMessage != null){
            context.json(updatedMessage);
        }else{
            context.status(400);
        }
        

/* 

        int getID = Integer.parseInt(context.pathParam("message_id"));
        String updatedText = context.body();

        if(updatedText == null || updatedText.trim().isEmpty()){
            context.status(400);
        }

        Message upMessage = serviceMessage.updateMessageByID(getID,updatedText);
        if(upMessage != null){
            context.status(200);
        }else{
            context.status(400);
        }
*/

/* 
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        int getID = Integer.parseInt(context.pathParam("message_id"));


        if(getID == null){
            context.status(400);
            

        }else{
            context.json(om.writeValueAsString(getID));
        }
*/





    }





  /* 
    private void getAllMessageWrittenByParticularUserHandler(Context context){

        
    }
  */



}