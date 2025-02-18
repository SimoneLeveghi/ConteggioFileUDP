import javax.xml.crypto.Data;
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
                String recvMsg = new String(recvPacket.getData(), 0, recvPacket.getLength());
                recvMsg += ".txt";

                if(recvMsg.equalsIgnoreCase("fine")) {
                    System.out.println("Server terminato");
                    System.exit(0);
                }

                String sendMsg;
                File file = new File(recvMsg);
                if(file.exists()) {
                    int letCount = countLettersInFile(recvMsg);
                    int numCount = countNumbersInFile(recvMsg);

                    sendMsg = "Lettere=" + letCount + ";Cifre=" + numCount;
                }
                else {
                    sendMsg = "File non trovato";
                }

                sendBuf = sendMsg.getBytes();
                DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, recvPacket.getAddress(), recvPacket.getPort());
                socket.send(packet);
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

    private static int countLettersInFile(String path) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for(int i = 0; i < line.length(); ++i) {
                    if(Character.isLetter(line.charAt(i))) {
                        count++;
                    }
                }
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int countNumbersInFile(String path) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String output = "";
            while ((line = reader.readLine()) != null) {
                for(int i = 0; i < line.length(); ++i) {
                    if(Character.isDigit(line.charAt(i))) {
                        count++;
                    }
                }
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
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
