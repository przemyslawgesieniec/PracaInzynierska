package com.gesieniec.przemyslaw.aviotsystemv001.iothandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by przem on 09.11.2017.
 */

public class MessageHandler  {

    private static DatagramSocket socket;

    public static void sendUDPMessage(String message, InetAddress address) {
        try {
            //byte[] addressBytes = new byte[] {(byte)192, (byte) 168, (byte) 1,(byte) 128};
            socket = new DatagramSocket();
            DatagramPacket packet = stringToDatagramPacket(message);
            packet.setAddress(address);
            //packet.setAddress(Inet4Address.getByAddress(addressBytes));
            packet.setPort(11000);
            packet.setData(message.getBytes());
            socket.send(packet);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static DatagramPacket stringToDatagramPacket(String s) {
        byte[] bytes = s.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
        return packet;
    }
}
