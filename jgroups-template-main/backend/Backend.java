package backend;

import org.jgroups.JChannel;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import java.util.*;
import utility.GroupUtils;
import org.jgroups.util.RspList;
import java.rmi.RemoteException;
import java.security.KeyStore.Entry;

public class Backend {
    private JChannel groupChannel;
    private RpcDispatcher dispatcher;
    private int requestCount;
    private final int DISPATCHER_TIMEOUT = 1000;
    public Map<Integer,String> sellersName = new HashMap<>(); //<sellerID,sellerName>
    public Map<Integer,String> sellersEmail = new HashMap<>();//<sellersID,sellerEmail>
    public Map<Integer,String> buyersName = new HashMap<>();//<buyerID,buyersName>
    public Map<Integer,String> buyersEmail = new HashMap<>(); //<buyerID,buyersEmail>
    public Map<Integer,Integer> sellerItems = new HashMap<>();//<sellerID,auctionID>
    public Map<Integer,String> itemDetails = new HashMap<>(); //<auctionID,auctionInfo>
    public Map<Integer,String> itemState = new HashMap<>();//<auctionID,state>
    public Map<Integer,Integer> itemNowPrice = new HashMap<>();//<auctionID, auctionNowPrice>
    public Map<Integer,Integer> itemReservePrice = new HashMap<>();//<auctionID, auctionReservePrice>
    public Map<Integer,Integer> buyersBid= new HashMap<>(); //<buyerID,auctionID>

    public Map<Integer,String> getMap1(){
        return sellersName;
    }
    public Map<Integer,String> getMap2(){
        return sellersEmail;
    }
    public Map<Integer,String> getMap3(){
        return buyersName;
    }
    public Map<Integer,String> getMap4(){
        return buyersEmail;
    }
    public Map<Integer,Integer> getMap5(){
        return sellerItems;
    }
    public Map<Integer,String> getMap6(){
        return itemDetails;
    }
    public Map<Integer,String> getMap7(){
        return itemState;
    }
    public Map<Integer,Integer> getMap8(){
        return itemNowPrice;
    }
    public Map<Integer,Integer> getMap9(){
        return buyersBid;
    }

    public void setMap1() throws Exception{
        RspList< Map<Integer,String>> responses = this.dispatcher.callRemoteMethods(null, "getMap1", 
        new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
        if(responses.isEmpty()){
            System.out.println("There's no responses content");
        }
        this.sellersName.putAll(responses.getFirst());

    }
    public void setMap2() throws Exception{
      RspList< Map<Integer,String>> responses = this.dispatcher.callRemoteMethods(null, "getMap2", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
      if(responses.isEmpty()){
        System.out.println("There's no content");
    }
      this.sellersEmail.putAll(responses.getFirst());

  }
  public void setMap3() throws Exception{
    RspList< Map<Integer,String>> responses = this.dispatcher.callRemoteMethods(null, "getMap3", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
    if(responses.isEmpty()){
        System.out.println("There's no content");
    }
    this.buyersName.putAll(responses.getFirst());

}
public void setMap4() throws Exception{
  RspList< Map<Integer,String>> responses = this.dispatcher.callRemoteMethods(null, "getMap4", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
  if(responses.isEmpty()){
    System.out.println("There's no content");
}
  this.buyersEmail.putAll(responses.getFirst());

}
public void setMap5() throws Exception{
  RspList< Map<Integer,Integer>> responses = this.dispatcher.callRemoteMethods(null, "getMap5", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
  if(responses.isEmpty()){
    System.out.println("There's no content");
}
  this.sellerItems.putAll(responses.getFirst());

}
public void setMap6() throws Exception{
  RspList< Map<Integer,String>> responses = this.dispatcher.callRemoteMethods(null, "getMap6", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
  if(responses.isEmpty()){
    System.out.println("There's no content");
}
  this.itemDetails.putAll(responses.getFirst());

}
public void setMap7() throws Exception{
  RspList< Map<Integer,String>> responses = this.dispatcher.callRemoteMethods(null, "getMap7", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
  if(responses.isEmpty()){
    System.out.println("There's no content");
}
  this.itemState.putAll(responses.getFirst());

}
public void setMap8() throws Exception{
  RspList< Map<Integer,Integer>> responses = this.dispatcher.callRemoteMethods(null, "getMap8", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
  if(responses.isEmpty()){
    System.out.println("There's no content");
}
  this.itemNowPrice.putAll(responses.getFirst());

}
public void setMap9() throws Exception{
  RspList<Map<Integer,Integer>> responses = this.dispatcher.callRemoteMethods(null, "getMap9", new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
  if(responses.isEmpty()){
    System.out.println("There's no content");
}
  this.buyersBid.putAll(responses.getFirst());

}
public Backend() throws Exception{
  this.requestCount = 0;
  // Connect to the group (channel)
  this.groupChannel = GroupUtils.connect();
  if (this.groupChannel == null) {
    System.exit(1); // error to be printed by the 'connect' function
  }
  // Make this instance of Backend a dispatcher in the channel (group)
  this.dispatcher = new RpcDispatcher(this.groupChannel, this);
  setMap1();
  setMap2();
  setMap3();
  setMap4();
  setMap5();
  setMap6();
  setMap7();
  setMap8();
  setMap9();

}
    public String registerSeller(int sellerID,String sellerinfo) throws Exception {
        if(sellersName.containsValue(sellerinfo.split(",")[0])||sellersEmail.containsValue(sellerinfo.split(",")[1])){
            return "The input info has already been registed";
        }
        else if(sellerinfo.split(",")[0]==null||sellerinfo.split(",")[1]==null){
                return "The userName or email can not be null";
            }
        else{
            sellersName.put(sellerID,sellerinfo.split(",")[0]);
            sellersEmail.put(sellerID,sellerinfo.split(",")[1]);
            return "Successful register";
        }
    }

    public String registerBuyer(int buyerID, String buyerinfo)throws Exception{
        if(buyersName.containsValue(buyerinfo.split(",")[0])||sellersEmail.containsValue(buyerinfo.split(",")[1])){
            return "The input info has already been registed";
        }
        else if(buyerinfo.split(",")[0]==null||buyerinfo.split(",")[1]==null){
            return "The userName or email can not be null";
        }
        else{
            buyersName.put(buyerID,buyerinfo.split(",")[0]);
            buyersEmail.put(buyerID,buyerinfo.split(",")[1]);
            return "Successful register";
        }
    }

    public int loginCheckSeller(String sellerName,String sellerEmail) throws Exception {
        int key1 = 0, key2=0;
        for (Map.Entry<Integer, String> entry : sellersName.entrySet()) {
            if (entry.getValue().equals(sellerName)) {
                key1 = entry.getKey();
            }
        }
        for (Map.Entry<Integer, String> entry : sellersEmail.entrySet()) {
            if (entry.getValue().equals(sellerEmail)) {
                key2 = entry.getKey();
            }
        }
        if(sellerName==null||sellerEmail==null){
            System.out.println("The userName or email can not be null");
            return 0;
        }
        else if(sellersName.containsValue(sellerName)&&sellersEmail.containsValue(sellerEmail)&&(key1==key2)){
            System.out.println("Login successfully!");
            return 1;
        }
        else {
            System.out.println("The input inf doesn't match");
            return 0;
        }
    }
    public int loginCheckBuyer(String buyerName,String buyerEmail) throws Exception {
        int key1 = 0, key2=0;
        for (Map.Entry<Integer, String> entry : buyersName.entrySet()) {
            if (entry.getValue().equals(buyerName)) {
                key1 = entry.getKey();
            }
        }
        for (Map.Entry<Integer, String> entry : buyersEmail.entrySet()) {
            if (entry.getValue().equals(buyerEmail)) {
                key2 = entry.getKey();
            }
        }
        if(buyerName==null||buyerEmail==null){
            System.out.println("The userName or email can not be null");
            return 0;
        }
        else if(buyersName.containsValue(buyerName)&&buyersEmail.containsValue(buyerEmail)&&(key1==key2)){
            System.out.println("Login successfully!");
            return 1;
        }
        else {
            System.out.println("The input inf doesn't match");
            return 0;
        }
    }
    
    public int getSellerID(String sellerEmail)throws Exception{
        int idSeller = 0;
        for (Map.Entry<Integer, String> entry : sellersEmail.entrySet()) {
            if (entry.getValue().equals(sellerEmail)) {
                 idSeller = entry.getKey();
            }
        }
        return idSeller;
    }
    public int getBuyerID(String buyerEmail)throws Exception{
        int idBuyer = 0;
        for (Map.Entry<Integer, String> entry : buyersEmail.entrySet()) {
            if (entry.getValue().equals(buyerEmail)) {
                idBuyer = entry.getKey();
            }
        }
        return idBuyer;
    }

    public String getAuctionItems() throws Exception {
        if(sellerItems.isEmpty()){
            return "There's no auctions";
        }
        List<Integer> auctionIDList = new ArrayList();
        sellerItems.values().forEach(value->{auctionIDList.add(value);});
        int listSize = auctionIDList.size();
        String[] name = new String[listSize];
        String[] detail = new String[listSize];
        int[] price = new int[listSize];
        String[] state = new String[listSize];
        for(int i = 0; i <= listSize; i++){
            name[i] = itemDetails.get(auctionIDList.get(i)).split(",")[0];
            detail[i] = itemDetails.get(auctionIDList.get(i)).split(",")[1];
            price[i] = itemNowPrice.get(auctionIDList.get(i));
            state[i] = itemState.get(auctionIDList.get(i));
        }
        StringBuilder r = new StringBuilder();
        for(int i = 0;i < listSize;i++){
            r.append("\nAuction Name:").append(name[i]).append("\nAuction Description:").append(detail[i]).append("\nCurrent Price:").append(price[i]).append("\nState:").append(state[i]).append("\n");
        }
        return r.toString();
        
       
       
    }

    public String uploadAuction(String sellerAuction,String auctionInfo) throws Exception{
        int sellerID = Integer.parseInt((sellerAuction.split(",")[0]));
        int auctionID =  Integer.parseInt((sellerAuction.split(",")[1]));
        String title = auctionInfo.split(",")[0];
        String description = auctionInfo.split(",")[1];
        String state="Bidding";
        String itemInfo = title+","+description+","+state;
        int start_price = Integer.parseInt(auctionInfo.split(",")[2]);
        int reserve_price = Integer.parseInt(auctionInfo.split(",")[3]);
        if(title==null||description==null){
            return "The title or description can't be null";
        }
        else if(start_price==0||reserve_price==0||auctionInfo.split(",")[2]==null||auctionInfo.split(",")[3]==null){
            return "The price can't be 0 or null";
        }
        else {
            sellerItems.put(sellerID,auctionID);
            itemDetails.put(auctionID,itemInfo);
            itemState.put(auctionID,state);
            itemNowPrice.put(auctionID,start_price);
            itemReservePrice.put(auctionID,reserve_price);
            return "Item id:"+auctionID+"\nAuction Title:"+title+"\nAuction Description:"+description+"\nStarting Price:"+start_price+"\nReserve Price:"+reserve_price+"\nState:"+state;

        }
    }

    public String stopAuction(int sellerID, int auctionID) throws Exception{
        if(!sellerItems.containsValue(auctionID)){
           return "The auction doesn't exist or has been closed";
       }
       else{
            String auctionDetail = null;
            for (Map.Entry<Integer, String> entry : itemDetails.entrySet()) {
                if (entry.getKey().equals(auctionID)) {
                    auctionDetail = entry.getValue();
                }
            }
            String aTitle = auctionDetail.split(",")[0];
            String aDescription = auctionDetail.split(",")[1];
            String state = "closed";
            itemState.put(auctionID,state);
            int itemNow = itemNowPrice.get(auctionID);
            int itemReservePrice = itemNowPrice.get(auctionID);
            int buyerID = 0;
            for (Map.Entry<Integer, Integer> entry : buyersBid.entrySet()) {
                if (entry.getValue().equals(auctionID)) {
                    buyerID = entry.getKey();
                }
            }
            String buyerName = null;
            for (Map.Entry<Integer, String> entry : buyersName.entrySet()) {
                if (entry.getKey().equals(buyerID)) {
                    buyerName = entry.getValue();
                }
            }
            if(itemNow>=itemReservePrice){
                for (Map.Entry<Integer, Integer> entry : sellerItems.entrySet()) {
                    if (entry.getValue().equals(auctionID)) {
                        sellerItems.remove(auctionID);
                    }
                }
            
                return "Title:"+aTitle+"\nDescription:"+aDescription+"\nState:"+state+"\nSuccessfully bid by"+buyerName+"with the price"+itemNow;
            }
            else{
                return "Title:"+aTitle+"\nDescription:"+aDescription+"\nState:"+state+"\nBid unsuccessfully!";
            }
        }
    }

    public String setBid(int buyerID, String bidinfo) throws Exception {
        int auctionID = Integer.parseInt(bidinfo.split(",")[0]);
        int auctionPrice = Integer.parseInt(bidinfo.split(",")[1]);
        int priceNow = 0;
        for (Map.Entry<Integer, Integer> entry : itemNowPrice.entrySet()) {
            if (entry.getKey().equals(auctionID)) {
                priceNow = entry.getValue();
            }
        }
        if(priceNow < auctionPrice){
            itemNowPrice.put(auctionID,auctionPrice);
            buyersBid.put(buyerID,auctionID);
            return "Bid successfully";
        }
        else{
            return "Bid unsuccessfully, your price need to be bigger than now price";
        }

    }


  public static void main(String args[]) throws Exception{
    new Backend();
  }

}
