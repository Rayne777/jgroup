package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface API extends Remote{
  String registerSeller(int sellerID,String sellerInfo) throws Exception;
  String registerBuyer(int buyerID, String buyerInfo) throws Exception;
  int loginCheckSeller(String sellerName,String sellerEmail) throws Exception;
  int loginCheckBuyer(String buyerName, String buyerEmail) throws Exception;
  int getSellerID(String sellerEmail)throws Exception;
  int getBuyerID(String buyerEmail) throws Exception;
  String getAuctionItems() throws Exception;
  String uploadAuction(String sellerAuction, String sellerReq) throws Exception;
  String stopAuction(int sellerID,int auctionID) throws Exception;
  String setBid(int buyerID, String bidinfo) throws Exception;
}
