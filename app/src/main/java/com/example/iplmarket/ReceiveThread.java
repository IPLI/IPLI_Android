package com.example.iplmarket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.*;
import java.net.Socket;

public class ReceiveThread extends Thread { //서버에서 정보를 받아옴
    private Socket socket;
    private Handler handler;
    private String ip;

    public ReceiveThread(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run()
    {
        super.run();
        try
        {
            BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sendWriter = new PrintWriter(socket.getOutputStream());
            String recivString ;
            String[] item_list, item_info;

            sendWriter.print(ip);
            sendWriter.flush();

            while(true)
            {
                Log.d("junjun wait", "waiting");
                recivString = tmpbuf.readLine();   //서버에서 보낼때 마지막에 개행 필요(readLine이라 한줄 입력임)
                Log.d("junjun receiv", recivString);

                if(recivString == null || recivString.equals("-1")) {
                    System.out.println("서버와 연결이 끊겼습니다.");
                    break;
                }
                else if(recivString.equals("bad request")) {
                    Log.d("junjun bad request", "bad request");
                    Message msg = handler.obtainMessage();
                    msg.what=2;
                    msg.obj = "bad request";
                    handler.sendMessage(msg);
                    break;
                }
                else if(recivString.equals("fail to connect")){
                    Log.d("junjun bad request", "fail to connect");
                    break;
                }
                else if(recivString.equals("+;") || recivString.equals("-;")) {
                    Log.d("item error", "상품을 다시 입력");
                    Message msg = handler.obtainMessage();
                    msg.what=4;
                    msg.obj = "no item";
                    handler.sendMessage(msg);
                }
                else    //정상적인 정보일 경우
                {
                    item_list = recivString.split(";");
                    String pm = item_list[item_list.length-1];

                    for(int i=0; i<item_list.length-1; i++) {
                        Message msg = handler.obtainMessage();

                        item_info = item_list[i].split("@");

                        if (pm.equals("+")) {
                            msg.what = 1;
                            msg.arg1 = Integer.parseInt(item_info[1]);
                        } else if (pm.equals("-")) {
                            msg.what = 0;
                            msg.arg1 = Integer.parseInt(item_info[1]);
                        } else
                            msg.what = 3;

                        msg.obj = item_info[0];
                        handler.sendMessage(msg);
                        Log.d("junjun sendMSG: ", item_info[0]);

                    }
                }
            }
            Log.d("junjun thread", "closed");
            tmpbuf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d("junjun exception", "exception");
        }
        Log.d("junjun thread end", "thread end");
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }
    public void setIp(String ip) {this.ip = ip;}
}


