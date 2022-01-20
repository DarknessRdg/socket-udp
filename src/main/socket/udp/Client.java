package main.socket.udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    Scanner input = new Scanner(System.in);

    DatagramSocket clientSocket;
    InetAddress serverAddress;

    String serverHost;
    int serverPort;
    int inputSize;

    Client(int serverPort, String serverHost, int maxInputSize) throws UnknownHostException {
        this.serverPort = serverPort;
        this.serverHost = serverHost;
        this.inputSize = maxInputSize;

        serverAddress = InetAddress.getByName(serverHost);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(Config.PORT, Config.SERVIDOR_HOST, Config.INPUT_SIZE);
        client.run();
    }

    public void run() throws IOException {
        logInitSocket();
        clientSocket = new DatagramSocket();

        String mensagem = getUserMessage();
        enviarMensagem(mensagem);

        String resposta = getMensagemServidor();
        logRespostaRecebida(resposta);
    }

    private void enviarMensagem(String message) throws IOException {
        logEnviandoMensagem(message);

        byte[] buffer = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(
                buffer,
                buffer.length,
                serverAddress,
                serverPort
        );

        clientSocket.send(sendPacket);
    }

    private String getMensagemServidor() throws IOException {
        logEsperandoRespostaDoServidor();

        byte[] buffer = getBuffer();
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

        clientSocket.receive(receivePacket);

        return StringUtils.fromBytes(receivePacket.getData());
    }

    private byte[] getBuffer() { return new byte[inputSize]; }

    private String getUserMessage() {
        System.out.print("Digite uma mensagem: ");
        return input.nextLine();
    }

    private void logInitSocket() {
        System.out.println("Iniciando socket do clinete ...");
    }

    private void logEnviandoMensagem(String mensagem) {
        System.out.println("Enviando mensagem ao servidor: " + mensagem);
    }

    private void logEsperandoRespostaDoServidor() {
        System.out.println("Esperando resposta do servidor ...");
    }

    private void logRespostaRecebida(String resposta) {
        System.out.println("Resposta recebida: " + resposta);
    }

}
