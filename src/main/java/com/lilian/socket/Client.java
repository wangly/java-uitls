// Copyright (C) 2017 Meituan
// All rights reserved
package com.lilian.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/3/3 上午12:17
 **/
public class Client {

    public static void main(String[] args) {
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        OutputStream outputStream = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            String inputStr = scanner.nextLine();
            printWriter = new PrintWriter(outputStream);
            printWriter.write(inputStr);
            printWriter.flush();
            socket.shutdownOutput();
            InputStream intputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(intputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String info;
            while ((info = bufferedReader.readLine()) != null) {
                System.out.println(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                printWriter.close();
                outputStream.close();
                bufferedReader.close();
                inputStreamReader.close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}