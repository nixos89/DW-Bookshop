package com.nikolas.master_thesis.resources;

import com.nikolas.master_thesis.api.AccountDTO;
import com.nikolas.master_thesis.api.UserADTO;
import com.nikolas.master_thesis.core.Account;
import com.nikolas.master_thesis.db.AccountDAO;
import com.nikolas.master_thesis.db.UserADAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountDAO accountDAO;
    private final UserADAO userADAO;

    public AccountResource(Jdbi jdbi) {
        accountDAO = jdbi.onDemand(AccountDAO.class);
        userADAO = jdbi.onDemand(UserADAO.class);
        accountDAO.createAccountTable();
        userADAO.createUserATable();
    }

//    @GET
//    @Path("/{accountId}")
//    public Response getAccount(@PathParam("accountId") Integer accountId) {
//        Account account = accountDAO.getAccountById(accountId);
//        List<UserADTO> accountUsersDTO = userADAO.getUsersForAccount(accountId);
//        List<UserA> accountUsers = new ArrayList<>();
//        if (!accountUsers.isEmpty()) {
//            accountUsers = accountUsersDTO.stream().map(dto -> new UserA(dto.getId(), dto.getName(), accountId)).collect(Collectors.toList());
//            account.setUserAS(accountUsers);
//        }
//        return Response.ok(account).build();
//    }



    @GET
    @Path("/{accountId}")
    public Response getAccount(@PathParam("accountId") Integer accountId) {
        Account account = accountDAO.getAccount(accountId);
        if (account != null) {
            return Response.ok(account).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response getAllAccounts() {
        List<AccountDTO> accounts = accountDAO.getAllAccounts();
        return Response.ok(accounts).build();
    }


    @POST
    public Response createAccount(AccountDTO accountDTO) {
        AccountDTO savedAccountDTO = accountDAO.saveAccount(accountDTO.getName());
        if (savedAccountDTO != null) {
            return Response.ok(savedAccountDTO).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /* Following methods ALSO deletes not just Account but Users of THAT Account */
    @DELETE
    @Path("{id}")
    public Response deleteAccount(@PathParam("id") int id) {
        Account account = accountDAO.getAccountById(id);

        if (account != null) {
            List<UserADTO> usersOfAccount = userADAO.getUsersForAccount(id);
            if (!usersOfAccount.isEmpty()) {
                usersOfAccount.stream().forEach(user -> userADAO.deleteUser(user.getId()));
            }
            boolean isDeleted = accountDAO.deleteAccount(id);
            if (isDeleted) {
                return Response.ok(isDeleted).build();
            } else {
                return Response.status(Response.Status.NOT_IMPLEMENTED).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
