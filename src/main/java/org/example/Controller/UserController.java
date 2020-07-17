package org.example.Controller;

import org.example.Entity.HolidayRequest;
import org.example.Entity.User;
import org.example.Exception.NoSearchResultException;
import org.example.Exception.NotFoundException;
import org.example.Exception.PayloadException;
import org.example.Repo.HolidayRequestRepo;
import org.example.Repo.UserRepo;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/Users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

   @Inject
   private UserRepo userRepo;

    private User user;

    //Shows all Users
    @GET
    @Path("/")
    public  List<User> allUsers() throws NoSearchResultException {
        List<User> userList = userRepo.listAll();

       if (userList.isEmpty()){
            throw new NoSearchResultException("No Users available!");
        }
        return userList;
    }



    //Returns specific User
    @GET
    @Path("/{id}")
    public User findUser(@PathParam("id") Long id) throws org.example.Exception.NotFoundException {
        user = userRepo.findById(id);
        if (user == null){
            throw new NotFoundException("User not found!");
        }else{
            return user;
        }
    }

    //Creating user
    @POST
    @Path("/create")
    @Transactional
    public String createUser(User newUser) throws PayloadException {
        if (newUser == null || newUser.getName() == null){
            throw new PayloadException("Payload incomplete!");
        }
        userRepo.persist(newUser);
        return "User created";
    }

}
