import tarefa.ServicoBackup;

public class Main {

    public static void main(String[] args) {

        ServicoBackup backup = new ServicoBackup();

        Thread threadBackup = new Thread(backup);

        // Inicia a thread do backup
        threadBackup.start();

        try {

            // A main espera 2 segundos
            Thread.sleep(2000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        // Solicita a interrupção do backup
        System.out.println("[Main] Tempo de tolerância excedido. Cancelando backup...");

        threadBackup.interrupt();
    }
}