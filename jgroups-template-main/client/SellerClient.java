package client;
import javax.crypto.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import api.API;


public class SellerClient {
    private final String SERVER_NAME = "myserver";
    public final int REGISTRY_PORT = 1099;

    public SellerClient(){
        try {
            int countId=0;
            int numAuction=0;
            Registry registry = LocateRegistry.getRegistry();
            API server = (API) registry.lookup(this.SERVER_NAME);
            while (true) {
                Scanner com = new Scanner(System.in);
                System.out.println("Do you already have an account?"+"\nIf yes,plz choose 1. \nIf no, regist a new account, plz choose 2. \nIf you want to exit, plz choose 3" );
                int cm = Integer.parseInt(com.nextLine());
                if (cm == 1) {
                    Scanner command = new Scanner(System.in);
                    System.out.println("Welcome to the Auction. Please insert your usename:");
                    String userName = command.nextLine();
                    System.out.println("Please insert your email:");
                    String email = command.nextLine();
                    int vertify = server.loginCheckSeller(userName, email);
                    while (vertify==1) {
                        int sellerID = server.getSellerID(email);
                        System.out.println("\nLogin successfully");
                        System.out.println("Which operation do you want?");
                        Scanner operation = new Scanner(System.in);
                        System.out.println("\n1.Create an auction\n" + "2.Stop the auction\n" + "3.Exit\n" + "Plz enter the key for operation:\n");
                        int order =Integer.parseInt(operation.nextLine());
                        if(order==1){
                            Scanner input = new Scanner(System.in);
                            System.out.println("Please enter the title of your item:");
                            String title = input.nextLine();
                            System.out.println("Please enter the description of your item:");
                            String description = input.nextLine();
                            System.out.println("Please enter the starting_price of your item:");
                            int starting_price = Integer.parseInt(input.nextLine());
                            System.out.println("Please enter the reserve_price of your item:");
                            int reserve_price = Integer.parseInt(input.nextLine());
                            System.out.println("Once you upload the item, the bid state will be true");
                            String auction = title+","+description+","+starting_price+","+reserve_price;
                            String sellerAuction = sellerID+","+numAuction;
                            String regist_info = server.uploadAuction(sellerAuction,auction);
                            numAuction++;
                            System.out.println(regist_info);

                        }else if(order ==2) {
                            System.out.println("Please enter the auction ID which you want to stop it:");
                            Scanner stopAuc = new Scanner(System.in);
                            int auctionID = Integer.parseInt(stopAuc.nextLine());
                            String askRequest = server.stopAuction(sellerID,auctionID);
                            System.out.println(askRequest);
                        }
                        else if(order ==3){
                            break;
                        }
                    }

                } else if (cm == 2) {
                    Scanner command = new Scanner(System.in);
                    System.out.println("If you want to take part in the auction. Please insert your usename:");
                    String userNewName = command.nextLine();
                    System.out.println("Please insert your email:");
                    String emailNew = command.nextLine();
                    String user = userNewName+","+emailNew;
                    String loginInfo = server.registerSeller(countId, user);
                    countId++;
                    System.out.println(loginInfo);
                } else if (cm == 3) {
                    return;
                }
            }
            }  catch (Exception e) {
                System.out.println(e);
              }
    }
    public static void main(String[] args) {
        new SellerClient();
    }
}
