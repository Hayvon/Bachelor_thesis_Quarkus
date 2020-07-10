package org.example.Controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
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
import java.util.HashMap;
import java.util.List;

@Path("api/HolidayRequests")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HolidayRequestController {

    @Inject
    private HolidayRequestRepo holidayRequestRepo;
    @Inject
    private RuntimeService runtimeService;
    @Inject
    private RepositoryService repositoryService;
    @Inject
    private UserRepo userRepo;
    @Inject
    private ProcessEngine processEngine;
    @Inject
    private TaskService taskService;

    String taskid = null;
    String taskAssigne = null;
    String userid = null;
    List<Task> allTasks = null;
    HashMap<String, Object> variables = new HashMap<>();
    HolidayRequest holidayRequest = null;
    User user;


    //Creates HolidayRequest, saves it to DB and starts new ProcessInstance of "Urlaubsantrag"
    @POST
    @Path("/create")
    @Transactional
    public String createNewHolidayRequest(HolidayRequest newHolidayRequest) throws PayloadException {

        if (newHolidayRequest == null || newHolidayRequest.getEndDate() == null || newHolidayRequest.getStartDate() == null || newHolidayRequest.getFullName() == null || newHolidayRequest.getVorgesetzter() == null){
            throw new PayloadException("Payload incomplete!");
        }

        newHolidayRequest.setStatus("Pending. Waiting for Interaction");

        holidayRequestRepo.persist(newHolidayRequest);

        runtimeService.createProcessInstanceByKey("urlaubsantrag")
                .setVariable("request_id", newHolidayRequest.getId())
                .setVariable("fullName", newHolidayRequest.getFullName())
                .setVariable("vorgesetzter", newHolidayRequest.getVorgesetzter())
                .setVariable("startDate", newHolidayRequest.getStartDate())
                .setVariable("endDate", newHolidayRequest.getEndDate())
                .setVariable("status", newHolidayRequest.getStatus())
                .executeWithVariablesInReturn();
        return "Holidayrequest created!";
    }

    //Shows all HolidayRequests
    @GET
    @Path("/")
     public List<HolidayRequest> allHolidayRequests() throws NoSearchResultException {
        List<HolidayRequest> allHolidayRequests = holidayRequestRepo.listAll();

        if (allHolidayRequests.isEmpty()){
            throw new NoSearchResultException("No Holidayrequests available!");
        }

        return  allHolidayRequests;
    }

    //Show Tasks for specific Holidayrequest
    @GET
    @Path("/{id}")
    public String showHolidayRequestTask(@PathParam("id") long id) throws org.example.Exception.NotFoundException {

        checkExistingHolidayRequest(id);

        String taskString = "Keine Tasks zu diesem Urlaubsantrag verfügbar!";

        List<Task> allTasks = taskService.createTaskQuery().processVariableValueEquals("request_id",id).list();

        for (Task task: allTasks) {
            taskString = "Task: " + task.getName() + " ID: " + task.getId() + " Assignee: " + task.getAssignee();
        }
        return taskString;
    }

    //Assigning holidayrequests
    @POST
    @Path("/{id}/assign")
    @Transactional
    public String assignTask(User newUser , @PathParam("id") long id) throws PayloadException, org.example.Exception.NotFoundException {
        checkExistingHolidayRequest(id);

        if (newUser == null || newUser.getName() == null || newUser.getUserId() == 0){
            throw new PayloadException("Payload incomplete!");
        }

        user = userRepo.findById(newUser.getUserId());
        if (user == null){throw new PayloadException("Payload incomplete!");}


        taskid = null;
        userid = Long.toString(newUser.getUserId());
        allTasks = getAllTasksForSpecificRequest(id);


        if (allTasks == null){
            return "No tasks available";
        }else {
            for (Task task: allTasks) {
                taskid = task.getId();
            }
            taskService.setAssignee(taskid, userid);
            updateStatus(id, newUser.getName(), "Assigned");
            return "Task Assigned";
        }
    }

    //Approving holidayrequests
    @POST
    @Path("/{id}/approve")
    @Transactional
    public String approveRequest(User newUser , @PathParam("id") long id) throws PayloadException, org.example.Exception.NotFoundException {

        checkExistingHolidayRequest(id);

        if (newUser == null || newUser.getName() == null || newUser.getUserId() == 0){
            throw new PayloadException("Payload incomplete!");
        }

        user = userRepo.findById(newUser.getUserId());
        if (user == null){throw new PayloadException("Payload incomplete!");}

        taskAssigne = null;
        taskid = null;
        userid = Long.toString(newUser.getUserId());
        variables.put("approved", "true");
        allTasks = getAllTasksForSpecificRequest(id);

        if (allTasks == null){
            return "No tasks available";
        }else {
            for (Task task : allTasks) {
                taskAssigne = task.getAssignee();
                System.out.println(taskAssigne);
                taskid = task.getId();
            }
            if (!(userid.equals(taskAssigne))){
                System.out.println(userid);
                System.out.println(taskAssigne);
                return "You are not assigned for this Task!";
            }else{
                taskService.complete(taskid, variables);
                updateStatus(id, newUser.getName(), "Approved");
                return "Task completed!";
            }
        }
    }

    //Rejecting holidayrequest
    @POST
    @Path("/{id}/reject")
    @Transactional
    public String rejectRequest(User newUser , @PathParam("id") long id) throws org.example.Exception.NotFoundException, PayloadException {

        checkExistingHolidayRequest(id);

        if (newUser == null || newUser.getName() == null || newUser.getUserId() == 0){
            throw new PayloadException("Payload incomplete!");
        }

        user = userRepo.findById(newUser.getUserId());
        if (user == null){throw new PayloadException("Payload incomplete!");}

        taskAssigne = null;
        taskid = null;
        userid = Long.toString(newUser.getUserId());
        variables.put("approved", "false");
        allTasks = getAllTasksForSpecificRequest(id);

        if (allTasks == null){
            return "No tasks available";
        }else {
            for (Task task: allTasks) {
                taskAssigne = task.getAssignee();
                taskid = task.getId();
            }
            if (!(userid.equals(taskAssigne))){
                return "You are not assigned for this Task!";
            }else{
                taskService.complete(taskid, variables);
                updateStatus(id,newUser.getName(), "Rejected");
                return "Task completed!";
            }
        }
    }

    //Check if Holidayrequest with ID exists
    void checkExistingHolidayRequest(long id) throws org.example.Exception.NotFoundException {
        holidayRequest =  holidayRequestRepo.findById(id);
        if (holidayRequest == null) {throw new NotFoundException("Holidayrequest not found!");}
    }


    //Query all Tasks for a specific holidayrequest
    List<Task> getAllTasksForSpecificRequest(long id){
        allTasks = taskService.createTaskQuery().processVariableValueEquals("request_id",id).list();

        if (allTasks.size() == 0){
            return null;
        } else {
            return allTasks;
        }
    }

    //Updates status of holidayrequest
    void updateStatus(long id, String name, String status){
        String status2 = status + " by " + name;
        holidayRequestRepo.update("status = " + "'" + status2 + "'" + " where id = ?1", id);
    }


}

