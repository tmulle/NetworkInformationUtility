package com.mycompany.network.util;

import java.net.*;
import java.util.*;
import static java.lang.System.out;

/**
 * Utility to print out all the information about Network Interfaces found on
 * the system.
 * 
 * @author tmulle
 */
public class PrintNetworkInfo {

    /**
     * Main entry point
     * 
     * @param args
     * @throws SocketException 
     */
    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            displayInterfaceInformation(netint);
        }
    }

    /**
     * Print all the information know
     * 
     * @param netint Network Interface to print information
     * @throws SocketException 
     */
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        out.printf("Display name: %s\n", netint.getDisplayName());
        out.printf("Name: %s\n", netint.getName());
        out.printf("Index: %s\n", netint.getIndex());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            out.printf("InetAddress: %s\n", inetAddress);
            out.printf("\tis AnyLocal: %s\n", inetAddress.isAnyLocalAddress());
            out.printf("\tis LinkLocal: %s\n", inetAddress.isLinkLocalAddress());
            out.printf("\tis SiteLocal: %s\n", inetAddress.isSiteLocalAddress());
            out.printf("\tis Multicast Global: %s\n", inetAddress.isMCGlobal());
            out.printf("\tis Multicast Link Local: %s\n", inetAddress.isMCLinkLocal());
            out.printf("\tis Multicast Node Local: %s\n", inetAddress.isMCNodeLocal());
            out.printf("\tis Multicast Org Local: %s\n", inetAddress.isMCOrgLocal());
            out.printf("\tis Multicast Site Local: %s\n", inetAddress.isMCSiteLocal());
            out.printf("\tis Loopback: %s\n", inetAddress.isLoopbackAddress());
            
            if (inetAddress instanceof Inet6Address) {
                out.printf("\tScopeId: %s\n", ((Inet6Address) inetAddress).getScopeId());
                out.printf("\tScoped Interface: %s\n", ((Inet6Address) inetAddress).getScopedInterface());
            }
        }


        out.printf("Up? %s\n", netint.isUp());
        out.printf("Loopback? %s\n", netint.isLoopback());
        out.printf("PointToPoint? %s\n", netint.isPointToPoint());
        out.printf("Supports multicast? %s\n", netint.supportsMulticast());
        out.printf("Virtual? %s\n", netint.isVirtual());
        out.printf("Hardware address: %s\n",
                macString(netint.getHardwareAddress()).orElse("No Hardware Address"));
        out.printf("MTU: %s\n", netint.getMTU());
        out.printf("\n");

        Enumeration<NetworkInterface> subInterfaces = netint.getSubInterfaces();
        for (NetworkInterface nic : Collections.list(subInterfaces)) {
            displayInterfaceInformation(nic);
        }
        
        out.println("-------------------------------------------");
    }

    /**
     * Gets the hardware address of a {@link NetworkInterface} in the format
     * {@code AA:BB:CC:DD:EE:FF}.
     *
     * @param mac The byte array containing the mac address information
     * @return A optional containing the string representation. This optional will
     * be empty if the network interface does not have a hardware address (virtual adapters like
     * loopback devices).
     */
    static Optional<String> macString(byte[] mac) {
        if (mac == null) {
            return Optional.empty();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
        }
        return Optional.of(sb.toString());
    }
}
