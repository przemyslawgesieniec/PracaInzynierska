package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
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
        Log.d("MessgageHandler","address: "+address.toString());
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 2390);
        new SendAndReceive().execute(packet);
        Log.d("MessgageHandler","new task started");
    }

    private class SendAndReceive extends AsyncTask<DatagramPacket, Void, String> {

        @Override
        protected String doInBackground(DatagramPacket... datagramPackets) {
            Log.d("SendAndReceive","doInBackground started");
            String enchantedCaps = "";
            try {
                /**
                 * send message
                 */
                Log.d("MessageHandler","sendoig message");
                socket = new DatagramSocket();
                socket.send(datagramPackets[0]);
                /**
                 * w8 4 message from device
                 */

                //TODO : TIMER Z retransmisjami !!!!
                byte[] buf = new byte[512];
                DatagramPacket receivedPacket = new DatagramPacket(buf,buf.length);
                socket.setSoTimeout(300);
                socket.receive(receivedPacket);
                String receivedMessage = new String(buf,0,receivedPacket.getLength());
                Log.d("MessageHandler","MESSAGE BACK: "+ receivedMessage);
                enchantedCaps = receivedMessage;
                socket.close();
            } catch (SocketException e) {
                Log.d("SocketException"," message timeout");
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
        protected void onPostExecute(String messageBack) {
            Log.d("MessageHandler","onPostExecute  "+ messageBack);
            if(messageBack.contains("stateupdate")){
                Log.d("MessageHandler","UPDATE_DEVICE_DATA  "+ messageBack);
                TaskDispatcher.newTask(TaskDispatcher.IoTTaskContext.UPDATE_DEVICE_DATA,new DeviceCapabilities(messageBack));
            }
            else if(messageBack.contains("capabilities")){
                Log.d("MessageHandler","ATTACH_COMPLETE  "+ messageBack);
                TaskDispatcher.newTask(TaskDispatcher.IoTTaskContext.ATTACH_COMPLETE,messageBack);
            }
            else if(messageBack.contains("connectioncfm")){
                TaskDispatcher.newTask(TaskDispatcher.IoTTaskContext.CONSISTENCY_MESSGE_RECEIVED,new DeviceCapabilities(messageBack));
            }
        }
    }
}
