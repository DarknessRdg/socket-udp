package main.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    DatagramSocket serverSocket;
    DatagramPacket clienteRecievePack;

    int inputSize;
    int porta;

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.run();
    }

    Servidor() throws SocketException {
        this(Config.PORT, Config.INPUT_SIZE);
    }

    Servidor(int port, int inputSize) throws SocketException {
        serverSocket = new DatagramSocket(port);
        this.inputSize = inputSize;
        this.porta = port;
    }

    public void run() throws IOException {
        logInicializarServidor();

        while (true) {
            esperarConexao();

            String body = getResponseBody();
            String response = simularAplicacaoWeb(body);

            enviarResposta(response);
        }
    }

    private void esperarConexao() throws IOException {
        clienteRecievePack = new DatagramPacket(new byte[inputSize], inputSize);
        serverSocket.receive(clienteRecievePack);
        logConexaoAberta();
    }

    private String getResponseBody() {
        String body = new String(clienteRecievePack.getData());
        logCorpoRequisicaoRecebido(body);

        return body;
    }

    private void enviarResposta(String response) throws IOException {
        logEnviarResposta(response);

        byte[] sendData = response.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(
                sendData, inputSize,
                clienteRecievePack.getAddress(),
                clienteRecievePack.getPort()
        );

        serverSocket.send(sendPacket);
    }

    private String simularAplicacaoWeb(String body) {
        return body.toUpperCase();
    }

    private void logInicializarServidor() {
        System.out.println("Servidor rodando ...");
        System.out.println("Esperando uma requisição");
        System.out.println("Porta: " + porta);
    }

    private void logConexaoAberta() {
        System.out.println("Conexão aberta com: ");
    }

    private void logCorpoRequisicaoRecebido(String body) {
        System.out.println("Requisição recebida: " + body);
    }

    private void logEnviarResposta(String response) {
        System.out.println("Enviando resposta: " + response);
    }
}
