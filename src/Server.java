import java.io.*;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Server {
    public static void main(String[] args) {
        try(MulticastSocket socket = new MulticastSocket(1234)) {
            byte[] recvBuf = new byte[1024];
            byte[] sendBuf;

            while(true) {
                DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(recvPacket);
                String msg = new String(recvPacket.getData(), 0, recvPacket.getLength());

                if(msg.equalsIgnoreCase("fine")) {
                    System.out.println("Server terminato");
                    System.exit(0);
                }

                File file = new File(msg);
                if(file.exists()) {

                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFromFile(String path) {
        return searchInFile(path, "");
    }

    private static String readNFromFile(String path, int lines) {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(path))) {
            String line;
            String output = "";
            while (((line = reader.readLine()) != null) && reader.getLineNumber() <= lines) {
                output += line + "\n";
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int countOccurrencesInFile(String path, String keyword) {
        return searchInFile(path, keyword).split("\n").length;
    }

    private static String searchInFile(String path, String keyword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String output = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains(keyword)) {
                    output += line + "\n";
                }
            }
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String searchArrayInFile(String path, String[] keywords) {
        int[] keywordsOccurrences = new int[keywords.length];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String output = "";
            while ((line = reader.readLine()) != null) {
                for(int i = 0; i < keywords.length; ++i) {
                    if(line.contains(keywords[i]))
                        keywordsOccurrences[i]++;
                }
            }
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void appendToFile(String path, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura nel file: " + e.getMessage());
        }
    }
}
