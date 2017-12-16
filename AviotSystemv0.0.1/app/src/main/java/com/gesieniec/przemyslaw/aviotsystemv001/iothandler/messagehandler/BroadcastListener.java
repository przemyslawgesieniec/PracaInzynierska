package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler;

import android.os.AsyncTask;
import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by przem on 16.11.2017.
 */

public class BroadcastListener extends AsyncTask<String, DatagramPacket, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        int port = 11000;
        try{
            DatagramSocket socket = new DatagramSocket(null);
            socket.bind(new InetSocketAddress(port));
            socket.setBroadcast(true);
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                String msg = new String(buffer, 0, packet.getLength());
                if(msg.equals("AttachRequest")){
                   publishProgress(packet);
                }
                Log.d("BroadcastListener",packet.getAddress().getHostName() + ": "+ msg);
                packet.setLength(buffer.length);
                //if(isCancelled()) break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(DatagramPacket... progress) {
        TaskDispatcher.newTask(TaskDispatcher.IoTTaskContext.ATTACH_REQUEST,progress[0]);
    }

    protected void onPostExecute(Void result) {

    }

}
