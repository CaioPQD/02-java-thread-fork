# 02-java-thread

## Compilação:
```bash
javac *.java
```
## Execução:
```bash
java Main
```


# Exercícios:

Em Java, implemente a aplicação abaixo.
Em Markdown, explique sua solução.

## 1) Serviço de Backup

Escreva um programa em Java que simule um processo de backup dividido em 5 etapas. Cada etapa simula um processamento pesado demorando 1 segundo (Thread.sleep(1000)).

A thread principal (main) deve iniciar o backup e dar ao usuário a chance de interrompê-lo caso o tempo limite de tolerância seja estourado.

# Requisitos de Implementação:

A classe do Backup: crie uma classe chamada ServicoBackup que implementa Runnable.

O loop de etapas: dentro do método run(), faça um loop que vai do passo 1 até o 5. A cada passo, printe qual etapa está sendo executada.

Tratamento de interrupção: se o processo de backup sofrer um .interrupt(), capture a exceção no bloco catch, imprimindo a mensagem "-> CRÍTICO: O backup foi cancelado pelo usuário! Limpando arquivos temporários..." e encerre a thread com return.

Mensagem de sucesso: se o loop terminar todos os 5 passos sem ser interrompido, exiba "-> SUCESSO: Backup concluído e salvo no servidor!".

# Cenários de Teste na classe Principal (main):

Modifique o tempo que a thread main espera no método Thread.sleep(...) para testar os dois comportamentos abaixo:

Cenário A (Cancelamento): faça a main esperar apenas 2 segundos (2000 ms) antes de chamar o .interrupt(). O programa deve parar no meio (provavelmente no passo 2 ou 3) e acionar a mensagem de erro crítico.

Cenário B (Sucesso): altere a main para esperar 7 segundos (7000 ms) antes de disparar o .interrupt(). Como 7 segundos é tempo suficiente para o backup rodar os seus 5 segundos totais, a tarefa deve terminar com sucesso antes mesmo de a main tentar interrompê-la.

# Solução:

## 1. Classe `ServicoBackup`

A classe implementa `Runnable`, permitindo que o backup seja executado por uma `Thread`.

```java
package tarefa;

public class ServicoBackup implements Runnable {

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {

            System.out.println("Executando etapa " + i + " do backup...");

            try {
                // Simula 1 segundo de processamento
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
```

O `for` executa as 5 etapas do backup. Em cada etapa, `Thread.sleep(1000)` simula um processamento de 1 segundo.

Caso a thread seja interrompida durante o `sleep()`, ocorre uma `InterruptedException`. O `catch` exibe a mensagem de cancelamento e o `return` encerra o método `run()`.

Se nenhuma interrupção acontecer, o loop termina e a mensagem de sucesso é exibida.

---

## 2. Classe `Main`

### Cenário A — Cancelamento

```java
import tarefa.ServicoBackup;

public class Main {

    public static void main(String[] args) {

        ServicoBackup backup = new ServicoBackup();
        Thread threadBackup = new Thread(backup);

        threadBackup.start();

        try {
            // Main espera 2 segundos
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
            "[Main] Tempo de tolerância excedido. Cancelando backup..."
        );

        threadBackup.interrupt();
    }
}
```

A `main` inicia o backup com:

```java
threadBackup.start();
```

Depois espera 2 segundos. Como o backup leva aproximadamente 5 segundos para concluir, a `main` chama:

```java
threadBackup.interrupt();
```

Isso interrompe o backup e ativa o `catch`.

**Fluxo:**

```text
start()
   ↓
Backup inicia
   ↓
Etapas do backup
   ↓
2 segundos
   ↓
main chama interrupt()
   ↓
InterruptedException
   ↓
catch → cancelamento
   ↓
return → thread encerrada
```

---

## 3. Cenário B — Sucesso

Para testar o sucesso, basta trocar:

```java
Thread.sleep(2000);
```

por:

```java
Thread.sleep(7000);
```

Como o backup leva aproximadamente 5 segundos:

```text
5 segundos → backup termina
7 segundos → main acorda
```

Portanto, quando a `main` chamar `interrupt()`, o backup já terá terminado.

A mensagem exibida será:

```text
-> SUCESSO: Backup concluído e salvo no servidor!
```

---

## 4. Conceitos principais

* `Runnable` → representa a tarefa que será executada pela thread.
* `Thread` → executa a tarefa em uma thread separada.
* `start()` → inicia a nova thread.
* `sleep(1000)` → pausa a thread por 1 segundo.
* `interrupt()` → solicita a interrupção da thread.
* `InterruptedException` → exceção gerada quando o `sleep()` é interrompido.
* `return` → encerra o método `run()` e finaliza a execução da tarefa.

