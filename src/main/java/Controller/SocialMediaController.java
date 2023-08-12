package Controller;

//the controller will require the use of the Service layer and the Model layer
import Model.Account;
import Service.ServiceAccount;
import DAO.AccountDAO;
//import java.sql.*;
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


     //initialize serviceAccount
     AccountDAO accountDAO = new AccountDAO();
     ServiceAccount serviceAccount = new ServiceAccount(accountDAO);

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postNewRegisterHandler);
        //app.post("/login", this::postUserLoginHandler);
        //app.post("/messages", this::postNewMessagesHandler);
        //app.get("/messages", this::getAllMessagesHandler);
       // app.get("/messages/{message_id}", this::getAllMessageByIdHandler);
        //app.delete("/messaged/{message_id}", this::deleteMessageByIdHandler);
        //app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
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

    private void postNewRegisterHandler(Context context) throws JsonProcessingException{

        //converting JSON to java language
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(context.body(), Account.class);
        //calling createNewUser to from Service layer to add layer
        Account addAcc = serviceAccount.createNewUser(acc);

        System.out.println(addAcc);
        System.out.println("before if/else statement");

        if(addAcc != null){
            //convert back to client language(json)
            context.json(addAcc);
        }
        else{
            //not successful
            System.out.println("in else statement");
            context.status(400);
        }
    }

/* 
    private void postUserLoginHandler(Context context){
        
    }

    private void postNewMessagesHandler(Context context){
        
    }

    private void getAllMessagesHandler(Context context){
        
    }

    private void getAllMessageByIdHandler(Context context){
        
    }

    private void deleteMessageByIdHandler(Context context){
        
    }
    
    private void updateMessageByIdHandler(Context context){
        
    }

    private void getAllMessageWrittenByParticularUserHandler(Context context){
        
    }

*/


}