package br.o_o.projeto1;


import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Relatorios{

    /* RELATÓRIO DE PACIENTES CADASTRADOS */
    public void relatorioPacientes() throws IOException, InterruptedException {
        File file = new File("cadastro_pacientes.txt");
        if (!file.exists()) {
            System.out.println("Nenhum paciente cadastrado ainda.");
            System.out.println();
            Thread.sleep(1000);
            return;
        }

        Scanner leitor = new Scanner(file);
        System.out.println();
        System.out.println("=== RELATÓRIO DE PACIENTES CADASTRADOS ===");
        while (leitor.hasNextLine()) {
            String[] dados = leitor.nextLine().split(";");
            System.out.println("Nome: " + dados[0] + " | CPF: " + dados[1] + " | Idade: " + dados[2]);
            System.out.println("Histórico de Consultas: " + (dados.length > 3 ? dados[3] : "Nenhum"));
            System.out.println("Histórico de Internações: " + (dados.length > 4 ? dados[4] : "Nenhuma"));
            System.out.println("--------------------------------------------");
            System.out.println();
            Thread.sleep(1000);
        }
        leitor.close();
    }

    /* RELATÓRIO DE MÉDICOS CADASTRADOS */
    public void relatorioMedicos() throws IOException, InterruptedException {
        File file = new File("cadastro_medicos.txt");
        if (!file.exists()) {
            System.out.println("Nenhum médico cadastrado ainda.");
            System.out.println();
            Thread.sleep(1000);
            return;
        }

        Scanner leitor = new Scanner(file);
        System.out.println();
        System.out.println("=== RELATÓRIO DE MÉDICOS CADASTRADOS ===");
        while (leitor.hasNextLine()) {
            String[] dados = leitor.nextLine().split(";");
            System.out.println("Nome: " + dados[0] + " | Crm: " + dados[1] + " | Especialidade: " + dados[3]);
            System.out.println("Custo da Consulta: R$" + dados[2]);
            System.out.println("--------------------------------------------");
            System.out.println();
            Thread.sleep(1000);
        }
        leitor.close();
    }

    /* RELATORIO DE COSULTAS FUTURAS E PASSADAS */
    public void relatorioConsultas() throws IOException, InterruptedException {
        File file = new File("agenda_consultas.txt");
        if (!file.exists()) {
            System.out.println("Nenhuma consulta registrada.");
            System.out.println();
            Thread.sleep(1000);
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        Scanner leitor = new Scanner(file);

        System.out.println();
        System.out.println("=== RELATÓRIO DE CONSULTAS ===");


        while (leitor.hasNextLine()) {
            String[] dados = leitor.nextLine().split(";");
            String paciente = dados[0];
            String medico = dados[1];
            LocalDateTime dataHora = LocalDateTime.parse(dados[2]);
            String local = dados[3];


            String tipo = dataHora.isBefore(agora) ? "PASSADA" : "FUTURA";
            System.out.println(tipo + " → Paciente: " + paciente + " | Médico: " + medico + " | Data/Hora: " + dataHora + " | Local: " + local);
            System.out.println();
            Thread.sleep(1000);
        }

        leitor.close();
    }

    /* PACIENTES INTERNADOS NO MOMENTO */
    public void relatorioPacientesInternados() throws IOException, InterruptedException {
        File file = new File("marcacoes_internacoes.txt");
        if (!file.exists()) {
            System.out.println("Nenhuma internação registrada.");
            System.out.println();
            Thread.sleep(1000);
            return;
        }

        LocalDate hoje = LocalDate.now();
        Scanner leitor = new Scanner(file);
        System.out.println();
        System.out.println("=== PACIENTES INTERNADOS NO MOMENTO ===");

        while (leitor.hasNextLine()) {
            String[] dados = leitor.nextLine().split(";");

            if (dados.length < 3) continue;

            String paciente = dados[0];
            String entradaStr = dados[2].trim();
            String saidaStr = (dados.length > 3) ? dados[3].trim() : "";

            // Se a data de entrada estiver vazia, ignora a linha
            if (entradaStr.isEmpty()) continue;

            LocalDate entrada = LocalDate.parse(entradaStr);

            // Paciente ainda internado → saída vazia
            if (saidaStr.isEmpty()) {
                long dias = ChronoUnit.DAYS.between(entrada, hoje);
                System.out.println("Paciente: " + paciente + " | Dias internado: " + dias);
                System.out.println();
                Thread.sleep(500);
            }
        }

        leitor.close();
    }

    /* ESTATÍSTICAS GERAIS */
    public void relatorioEstatisticas() throws IOException, InterruptedException {
        File fileConsultas = new File("agenda_consultas.txt");
        File fileMedicos = new File("cadastro_medicos.txt");

        if (!fileConsultas.exists()) {
            System.out.println("Sem dados de consultas para gerar estatísticas.");
            System.out.println();
            Thread.sleep(1000);
            return;
        }

        Map<String, String> especialidadePorMedico = new HashMap<>();

        if (fileMedicos.exists()) {
            Scanner leitorMed = new Scanner(fileMedicos);
            while (leitorMed.hasNextLine()) {
                String linha = leitorMed.nextLine();
                String[] dados = linha.split(";");
                if (dados.length >= 3) {
                    String nomeMedico = dados[0].trim();
                    String especialidade = dados[3].trim();
                    especialidadePorMedico.put(nomeMedico, especialidade);
                }
            }
            leitorMed.close();
        }

        Map<String, Integer> consultasPorMedico = new HashMap<>();
        Map<String, Integer> consultasPorEspecialidade = new HashMap<>();

        Scanner leitor = new Scanner(fileConsultas);

        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine();
            String[] dados = linha.split(";");

            if (dados.length < 2) continue;

            String medico = dados[1].trim();
            String especialidade = especialidadePorMedico.getOrDefault(medico, "NÃO INFORMADA");

            consultasPorMedico.put(medico, consultasPorMedico.getOrDefault(medico, 0) + 1);

            consultasPorEspecialidade.put(especialidade, consultasPorEspecialidade.getOrDefault(especialidade, 0) + 1);
        }

        leitor.close();

        String medicoMaisAtendeu = "";
        int maxMedico = 0;
        for (String m : consultasPorMedico.keySet()) {
            if (consultasPorMedico.get(m) > maxMedico) {
                maxMedico = consultasPorMedico.get(m);
                medicoMaisAtendeu = m;
            }
        }

        String especialidadeMaisProcurada = "";
        int maxEspecialidade = 0;
        for (String e : consultasPorEspecialidade.keySet()) {
            if (consultasPorEspecialidade.get(e) > maxEspecialidade) {
                maxEspecialidade = consultasPorEspecialidade.get(e);
                especialidadeMaisProcurada = e;
            }
        }

        System.out.println();
        System.out.println("=== ESTATÍSTICAS GERAIS ===");
        System.out.println(" Médico que mais atendeu: " + medicoMaisAtendeu + " (" + maxMedico + " consultas)");
        System.out.println(" Especialidade mais procurada: " + especialidadeMaisProcurada + " (" + maxEspecialidade + " consultas)");
        System.out.println();
        Thread.sleep(1000);
    }

}