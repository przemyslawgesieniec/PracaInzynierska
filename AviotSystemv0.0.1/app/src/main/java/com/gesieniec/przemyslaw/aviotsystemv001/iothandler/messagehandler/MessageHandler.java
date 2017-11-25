package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler;

import android.os.AsyncTask;
import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by przem on 09.11.2017.
 */

public class MessageHandler {

    private DatagramSocket socket;

    public void sendAndReceiveUDPMessage(String message, InetAddress address) {
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 2390);
        new SendAndReceive().execute(packet);
        Log.d("MessgageHandler","new task started");
    }

    private class SendAndReceive extends AsyncTask<DatagramPacket, Void, String> {

        @Override
        protected String doInBackground(DatagramPacket... datagramPackets) {
            Log.d("SendAndReceive","doInBackground started");
            String enchantedCaps = null;
            try {
                /**
                 * send message
                 */
                socket = new DatagramSocket();
                socket.send(datagramPackets[0]);
                /**
                 * w8 4 message from device
                 */

                byte[] buf = new byte[512];
                DatagramPacket receivedPacket = new DatagramPacket(buf,buf.length);
                socket.receive(receivedPacket);
                String reveivedMessage = new String(buf,0,receivedPacket.getLength());
                Log.d("MessgageHandler","MESSAGE BACK: "+ reveivedMessage);
                enchantedCaps = reveivedMessage +";"+ receivedPacket.getAddress().getHostAddress();
                socket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return enchantedCaps;
        }
        protected void onProgressUpdate(Integer... progress) {
            //TODO DISABLE BUTTON IN GUI
        }
        protected void onPostExecute(String esp8266Capabilities) {
            Log.d("MessgageHandler","onPostExecute  "+ esp8266Capabilities);
            TaskDispatcher.newTask(TaskDispatcher.IoTTaskContext.ATTACH_COMPLETE,esp8266Capabilities);
            //task with rcved capabilities as string
        }
    }
}
