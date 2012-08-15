package net.tigerclan.KingLinkTiger.setgmailstatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.commons.lang.StringEscapeUtils;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;


public class setgmailstatus {
    public static String apikey;
    private static String username;
    private static String password;
    
    public static void main(String[] args) throws Exception {
    	username = args[0];
    	password = args[1];
    	apikey = args[2];
    	
    	statuschange();
    }

	private static void statuschange() throws Exception{
        URL oracle;
		oracle = new URL("http://tigerclan.net/BTC.php?apikey=" + apikey);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
        	String line = inputLine;
        	line = line + " MHash/s!";
        	setStatus(line);
        }
        in.close(); 
	}

	
	

	private static void setStatus(final String status) throws Exception {
	        ConnectionConfiguration cc = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
	        XMPPConnection connection = new XMPPConnection(cc);
	
	        connection.connect();
	        SASLAuthentication.supportSASLMechanism("PLAIN", 0);
	        connection.login(username, password);
	
	        connection.sendPacket(new IQ() {
	                @Override
	                public String getChildElementXML() {
	                        return "<query xmlns='http://jabber.org/protocol/disco#info'/>";
	                }
	                @Override
	                public String getTo() {
	                        return "gmail.com";
	                }
	                @Override
	                public Type getType() {
	                        return Type.GET;
	                }
	        });
	        Thread.sleep(2000);
	        connection.sendPacket(new IQ() {
	                @Override
	                public String getChildElementXML() {
	                        return "<query xmlns='google:shared-status' version='2'/>";
	                }
	                @Override
	                public String getTo() {
	                        return username;
	                }
	                @Override
	                public Type getType() {
	                        return Type.GET;
	                }
	        });
	        Thread.sleep(2000);
	        Presence presence = new Presence(Presence.Type.available, status, 24, Presence.Mode.available);
	        connection.sendPacket(presence);
	        connection.sendPacket(new IQ() {
	                @Override
	                public String getChildElementXML() {
	                        return "<query xmlns='google:shared-status' version='2'><status>" + StringEscapeUtils.escapeXml(status)
	                                        + "</status><invisible value='false'/></query>";
	                }
	                @Override
	                public String getTo() {
	                        return username;
	                }
	                @Override
	                public Type getType() {
	                        return Type.SET;
	                }
	        });
	        Thread.sleep(2000);
	        connection.disconnect();
	}
}
