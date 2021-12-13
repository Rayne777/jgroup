package frontend;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.View;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;

import api.API;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import utility.GroupUtils;

public class Frontend extends UnicastRemoteObject implements API, MembershipListener {

  public static final long serialVersionUID = 42069;
  public final int REGISTRY_PORT = 1099;
  private final String SERVER_NAME = "myserver";
  private JChannel groupChannel;
  private RpcDispatcher dispatcher;

  private final int DISPATCHER_TIMEOUT = 1000;

  public Frontend() throws RemoteException {
    // Connect to the group (channel)
    this.groupChannel = GroupUtils.connect();
    if (this.groupChannel == null) {
      System.exit(1); // error to be printed by the 'connect' function
    }

    // Bind this server instance to the RMI Registry
    this.bind(this.SERVER_NAME);

    // Make this instance of Frontend a dispatcher in the channel (group)
    this.dispatcher = new RpcDispatcher(this.groupChannel, this);
    this.dispatcher.setMembershipListener(this);

  }

  private void bind(String serverName) {
    try {
      Registry registry = LocateRegistry.createRegistry(this.REGISTRY_PORT);
      registry.rebind(serverName, this);
      System.out.println("✅    rmi server running...");
    } catch (Exception e) {
      System.err.println("🆘    exception:");
      e.printStackTrace();
      System.exit(1);
    }
  }
  @Override
  public String registerSeller(int sellerID, String sellerinfo) throws Exception{
      RspList<String> responses = this.dispatcher.callRemoteMethods(null, "registerSeller", new Object[] { sellerID, sellerinfo }, new Class[] { int.class, String.class },new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return null;
      }
      return responses.getFirst();
  }

  @Override
  public int loginCheckSeller(String sellerName, String sellerEmail) throws Exception {
      RspList<Integer> responses = this.dispatcher.callRemoteMethods(null, "loginCheckSeller",
          new Object[] { sellerName, sellerEmail }, new Class[] { String.class, String.class },
          new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return 0;
      }
      return responses.getFirst();
   
  }

  @Override
  public String registerBuyer(int buyerID,String buyerInfo) throws Exception {
    
      RspList<String> responses = this.dispatcher.callRemoteMethods(null, "registerBuyer",
          new Object[] { buyerID, buyerInfo }, new Class[] { int.class, String.class },
          new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return "There's no response";
      }
      return responses.getFirst();
  }

  @Override
  public int loginCheckBuyer(String buyerName,String buyerEmail) throws Exception {
      RspList<Integer> responses = this.dispatcher.callRemoteMethods(null, "loginCheckBuyer",
          new Object[] { buyerName, buyerEmail }, new Class[] { String.class, String.class },
          new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return 0;
      }
      return responses.getFirst();
  }

  @Override
  public int getSellerID(String sellerEmail)throws Exception{
    RspList<Integer> responses = this.dispatcher.callRemoteMethods(null, "getSellerID",
    new Object[] { sellerEmail }, new Class[] { String.class},
    new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

    System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
    if (responses.isEmpty()) {
      return 0;
    }
    return responses.getFirst();
  }

  @Override
  public int getBuyerID(String buyerEmail)throws Exception{
    RspList<Integer> responses = this.dispatcher.callRemoteMethods(null, "getBuyerID",
    new Object[] { buyerEmail }, new Class[] { String.class},
    new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

    System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
    if (responses.isEmpty()) {
      return 0;
    }
    return responses.getFirst();
  }

  @Override
  public String getAuctionItems() throws Exception {
    RspList<String> responses = this.dispatcher.callRemoteMethods(null, "getAuctionItems",new Object[] {}, new Class[] {},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
    System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
    System.out.println(responses);

    if (responses.isEmpty()) {
      return null;
      }
    return responses.getFirst();
  }
  @Override
  public  String uploadAuction(String sellerAuction, String sellerReq) throws Exception{
    RspList<String> responses = this.dispatcher.callRemoteMethods(null, "uploadAuction",new Object[] {sellerAuction,sellerReq}, new Class[] {String.class,String.class},new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));
    System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
    if (responses.isEmpty()) {
      return null;
      }
    return responses.getFirst();
  }

  @Override
  public String stopAuction(int sellerID, int auctionID) throws Exception{
      RspList<String> responses = this.dispatcher.callRemoteMethods(null, "stopAuction",new Object[] {sellerID,auctionID}, new Class[] {int.class,int.class},
          new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return "The auction is not exist or has already been stoped";
      }
      return responses.getFirst();
    

  }

  @Override
  public String setBid(int buyerID, String bidinfo) throws Exception {
      RspList<String> responses = this.dispatcher.callRemoteMethods(null, "setBid",new Object[] {buyerID,bidinfo}, new Class[] {int.class,String.class},
          new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return "The auction is not exist or has already been stoped";
      }
      return responses.getFirst();

  }

  public void viewAccepted(View newView) {
    System.out.printf("👀    jgroups view changed\n✨    new view: %s\n", newView.toString());
  }

  public void suspect(Address suspectedMember) {
    System.out.printf("👀    jgroups view suspected member crash: %s\n", suspectedMember.toString());
  }

  public void block() {
    System.out.printf("👀    jgroups view block indicator\n");
  }

  public void unblock() {
    System.out.printf("👀    jgroups view unblock indicator\n");
  }

  public static void main(String args[]) {
    try {
      new Frontend();
    } catch (RemoteException e) {
      System.err.println("🆘    remote exception:");
      e.printStackTrace();
      System.exit(1);
    }
  }

}
