package Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server 
{
    public static List<PrintWriter> clients = new ArrayList<>();
    
    public static void main(String[] args) 
    {
        new Server().Baslat();
    }
    
    private class ClientYonetici implements Runnable 
    {
        private Socket clientSocket;
        private Scanner input;

        //Her bağlanan Client için ayrı bir Thread yapısı oluşturur.
        public ClientYonetici(Socket socket) 
        {
            this.clientSocket = socket;
            try 
            {
                this.input = new Scanner(socket.getInputStream());
            }
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }

        //Server Konsol kısmında Kullanıcı ve Mesajını görebilir, Gelen mesajları diğer Client'lere iletmek için de MesajYayini fonksiyonunu kullanır.
        @Override
        public void run() 
        {
            try 
            {
                while (true) 
                {
                    if (input.hasNextLine()) 
                    {
                        String mesaj = input.nextLine();
                        System.out.println("Mesaj: " + mesaj);
                        MesajYayini(mesaj);
                    }
                }
            }
            finally 
            {
                input.close();
            }
        }

        //Server'a gelen Mesajların iletimini diğer Client'lere de yapar.
        private void MesajYayini(String mesaj) 
        {
            for (PrintWriter client : clients) 
            {
                try 
                {
                    client.println(mesaj);
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //Server'i 2953 Port'undan başlatır.
    public void Baslat() 
    {
        try (ServerSocket serverSocket = new ServerSocket(2953))
        {
            System.out.println("Server Baslatildi... Aktif Port: 2953");

            while (true) 
            {
                //PrintWriter objesi (UTF protokolü gibi) Mesaj iletimini sağlar. 
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                Thread clientThread = new Thread(new ClientYonetici(clientSocket));
                //Aktif Kullanıcıları ArrayList'e kaydeder.
                clients.add(writer);
                clientThread.start();
                System.out.println("Bagli Kullanici Sayisi: " + clients.size());
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}