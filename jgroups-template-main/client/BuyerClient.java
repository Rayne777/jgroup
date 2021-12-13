package client;
import javax.crypto.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import api.API;



public class BuyerClient {
    private final String SERVER_NAME = "myserver";
    public final int REGISTRY_PORT = 1099;
    public BuyerClient(){
        try {
            int buyerId=0;
            Registry registry = LocateRegistry.getRegistry();
            API server = (API) registry.lookup(SERVER_NAME);
           
            while (true) {
                System.out.println("Do you already have an account?"+"\nIf yes,plz choose 1. \nIf no, regist a new account, plz choose 2. \nIf you want to exit, plz choose 3" );
                Scanner order = new Scanner(System.in);
                int cm = Integer.parseInt(order.nextLine());
                if (cm == 1) {
                    Scanner command = new Scanner(System.in);
                    System.out.println("Welcome to the Auction. Please insert your usename:");
                    String userName = command.nextLine();
                    System.out.println("Please insert your email:");
                    String email = command.nextLine();
                    int vertify = server.loginCheckBuyer(userName,email);
                    while (vertify==1) {
                        System.out.println("Which operation do you want?");
                        Scanner operation = new Scanner(System.in);
                        System.out.println("\n1.Bid for the auction\n" + "2.Auction Details\n" + "3.Exit\n" + "Plz enter the key for operation:\n");
                        int ordering = Integer.parseInt(operation.nextLine());
                        if(ordering ==1){
                            Scanner input = new Scanner(System.in);
                            System.out.println("Please enter the auctionID you want to bid:");
                            int id =  Integer.parseInt(input.nextLine());
                            System.out.println("Please enter the price:");
                            int bidPrice = Integer.parseInt(input.nextLine());
                            String  auctioninfo = id + "," + bidPrice;
                            String setBid = server.setBid(buyerId,auctioninfo);
                            System.out.println(setBid);
                        }
                        else if(ordering ==2){
                            String items = server.getAuctionItems();
                            System.out.println(items);
                        }
                        else if(ordering == 3){
                            break;
                        }

                    }

                } else if (cm == 2) {
                    Scanner command = new Scanner(System.in);
                    System.out.println("If you want to take part in the auction. Please insert your usename:");
                    String userNewName = command.nextLine();
                    System.out.println("Please insert your email:");
                    String emailNew = command.nextLine();
                    String buyer = userNewName+","+emailNew;
                    String loginInfo = server.registerBuyer(buyerId, buyer);
                    buyerId++;
                    
                    System.out.println(loginInfo);
                } else if (cm == 3) {
                    break;
                }
            }
        }  catch (Exception e) {
            System.out.println(e);
          }
    }
    
    public static void main(String[] args) {
       new BuyerClient();
    }
}
