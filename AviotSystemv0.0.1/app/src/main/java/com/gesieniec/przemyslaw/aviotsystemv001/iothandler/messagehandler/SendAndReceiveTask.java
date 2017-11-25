//package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//
///**
// * Created by przem on 17.11.2017.
// */
//
//public class SendAndReceiveTask extends AsyncTask<DatagramPacket, Void, String> {
//
//    private DatagramSocket socket;
//
//    @Override
//    protected String doInBackground(DatagramPacket... datagramPackets) {
//        Log.d("SendAndReceiveTask","doInBackground started");
//        String reveicedMessage = null;
//        try {
//            /**
//             * send message
//             */
//            socket = new DatagramSocket();
//            socket.send(datagramPackets[0]);
//            /**
//             * w8 4 message from device
//             */
//            byte[] buf = new byte[512];
//            DatagramPacket receivedPacket = new DatagramPacket(buf,buf.length);
//            socket.receive(receivedPacket);
//            reveicedMessage = new String(buf,0,receivedPacket.getLength());
//            Log.d("MessgageHandler","MESSAGE BACK from esp8266: "+ reveicedMessage);
//            //new task with complete and all capabilities included
//            socket.close();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return reveicedMessage;
//    }
//    protected void onProgressUpdate(Integer... progress) {
//        //TODO DISABLE BUTTON IN GUI
//    }
//    protected void onPostExecute(String result) {
//        Log.d("MessgageHandler","onPostExecute  "+ result);
//    }
//}