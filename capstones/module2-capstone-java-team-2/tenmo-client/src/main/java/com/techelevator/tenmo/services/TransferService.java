package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferDTO;
import com.techelevator.tenmo.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

public class TransferService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    public TransferService(String url){this.baseUrl = url;};

    private HttpEntity<?> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return entity;
    }
    public void getTransfers(AuthenticatedUser currentUser){
        HttpEntity<?> entity = createRequestEntity(currentUser.getToken());
        Transfer[] transfers = restTemplate.exchange(baseUrl + "users/transfer", HttpMethod.GET, entity, Transfer[].class).getBody();
        System.out.println("-------------------------------------------");
        System.out.println("ID \t\t From/to \t\t Amount");
        System.out.println("-------------------------------------------");
        for(Transfer transfer : transfers){
            if (currentUser.getUser().getUsername().equals(transfer.getFromUsername())) {
                System.out.println(transfer.getTransferId() + " To: " + transfer.getToUsername() + " $ " + transfer.getAmount()
                );
            } else if (currentUser.getUser().getUsername().equals(transfer.getToUsername())) {
                System.out.println(transfer.getTransferId() + " From: " + transfer.getFromUsername() + " $ " + transfer.getAmount()
                );
            }else{
                System.out.println("no transfers");
            }
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter transfer ID to view details (0 to cancel): ");
        Long in = input.nextLong();
        if(in != 0){
            viewTransferDetails(currentUser, in);
        }



    }
    public void getUsers(AuthenticatedUser currentUser){
        HttpEntity<?> entity = createRequestEntity(currentUser.getToken());
        HashMap<String, Long> users = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, entity, HashMap.class).getBody();
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID\t\tName");
        System.out.println("-------------------------------------------");
        for(Map.Entry<String, Long> user : users.entrySet()){
            if (!currentUser.getUser().getUsername().equals(user.getKey())){


                System.out.println(user.getKey() + "\t\t"+ user.getValue());
            }
        }
        System.out.println("-------------------------------------------");
    }
    public void viewTransferDetails(AuthenticatedUser currentUser, Long input){
        System.out.println("-------------------------------------------");

        HttpEntity<?> entity = createRequestEntity(currentUser.getToken());
        Transfer transfer = restTemplate.exchange(baseUrl + "transfer/" + input, HttpMethod.GET, entity, Transfer.class).getBody();
        System.out.println(transfer.getTransferId());
        System.out.println("From: " + transfer.getFromUsername());
        System.out.println("To: " + transfer.getToUsername());
        System.out.println("type: " + transfer.getTransferTypeId());
        System.out.println("Status:" + transfer.getTransferStatusId());
        System.out.println("Amount: " + transfer.getAmount());
    }
    public void sendTeBucks(AuthenticatedUser currentUser) throws NumberFormatException{
//        HttpEntity<?> entity = createRequestEntity(currentUser.getToken());

        Scanner scanner = new Scanner(System.in);
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setIdFrom(currentUser.getUser().getId());

        try{
            System.out.print("Enter ID of user you are sending to (0 to cancel): ");
            Long id = new Long(scanner.nextLine());
            transferDTO.setIdTo(id);

            if( id > 0){


                System.out.print("Enter amount: ");
                BigDecimal input = new BigDecimal(scanner.nextLine());
                transferDTO.setAmount(input);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(currentUser.getToken());
                HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, headers);
                restTemplate.exchange(baseUrl + "transfer", HttpMethod.POST, entity, TransferDTO.class);




            }
        }catch (NumberFormatException e){
            System.out.println("*** INVALID INPUT ***");
        }






    }
}
