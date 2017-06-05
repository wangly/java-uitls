// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/3/3 上午12:17
 **/
public class Service {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream;
        PrintWriter printWriter = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                System.out.println(info);
            }
            socket.shutdownInput();
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write("收到!");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                printWriter.close();
                outputStream.close();
                bufferedReader.close();
                inputStreamReader.close();
                socket.close();
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}