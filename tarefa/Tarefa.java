package tarefa;

public class ServicoBackup implements Runnable {

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {

            System.out.println("Executando etapa " + i + " do backup...");

            try {
                // Simula um processamento pesado de 1 segundo
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                System.out.println(
                    "-> CRÍTICO: O backup foi cancelado pelo usuário! " +
                    "Limpando arquivos temporários..."
                );

                return;
            }
        }

        System.out.println(
            "-> SUCESSO: Backup concluído e salvo no servidor!"
        );
    }
}