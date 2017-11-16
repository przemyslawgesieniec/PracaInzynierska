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

    private DatagramSocket socket;

    public void sendUDPMessage(String message, InetAddress address) {
        try {
            socket = new DatagramSocket();
            byte[] buf = message.getBytes();
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length, address, 2390);
            socket.send(packet);
            socket.close();
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
    private DatagramPacket stringToDatagramPacket(String s) {
        byte[] bytes = s.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
        return packet;
    }
}
