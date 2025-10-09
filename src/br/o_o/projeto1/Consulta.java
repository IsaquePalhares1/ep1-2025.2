package br.o_o.projeto1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Consulta {

    /* ATRIBUTOS */
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private String status;

    /* CONSTRUTORES */
    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local, String status) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = status;
    }

    public Consulta() {
        this.paciente = new Paciente();
        this.medico = new Medico();
        this.dataHora = null;
        this.local = "";
        this.status = "";
    }

    /* MÉTODOS GET */
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getLocal() { return local; }
    public String getStatus() { return status; }

    /* MÉTODOS SET */
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setLocal(String local) { this.local = local; }
    public void setStatus(String status) { this.status = status; }

    /* TO STRING */
    @Override
    public String toString() {
        return "Paciente: " + paciente.getNome() + "\n" +
                "Médico: " + medico.getNome() + "\n" +
                "Data e Hora: " + dataHora + "\n" +
                "Local: " + local + "\n" +
                "Status: " + status;
    }

    /* METODO AGENDAR CONSULTA */
    public void agendarConsulta(Scanner scanner) throws IOException, InterruptedException {
        System.out.print("Nome do Paciente: ");
        String nomeP = scanner.nextLine();

        System.out.print("Cpf do Paciente: ");
        String cpf = scanner.nextLine();

        System.out.print("Nome do Médico: ");
        String nomeM = scanner.nextLine();

        System.out.print("Crm do Médico: ");
        String crm = scanner.nextLine();

        System.out.print("Local da consulta (formato: 'Consultório x'): ");
        String local = scanner.nextLine();

        System.out.print("Data e hora (formato: AAAA-MM-DDTHH:MM): ");
        String dataHoraStr = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        // Buscar paciente cadastrado
        Paciente pacienteEncontrado = null;
        Paciente cadastroP = new Paciente();
        for (Paciente p : cadastroP.listaPacientes()) {
            if (p.getNome().equalsIgnoreCase(nomeP) && p.getCpf().equals(cpf)) {
                pacienteEncontrado = p;
                break;
            }
        }
        if (pacienteEncontrado == null) {
            System.out.println();
            Thread.sleep(1000);
            System.out.println("Paciente não encontrado!");
            System.out.println();
            Thread.sleep(2000);
            return;
        }

        // Buscar médico cadastrado
        Medico medicoEncontrado = null;
        Medico cadastroM = new Medico();
        for (Medico m : cadastroM.listaMedicos()) {
            if (m.getNome().equalsIgnoreCase(nomeM) && m.getCrm().equals(crm)) {
                medicoEncontrado = m;
                break;
            }
        }
        if (medicoEncontrado == null) {
            System.out.println();
            Thread.sleep(1000);
            System.out.println("Médico não encontrado!");
            System.out.println();
            Thread.sleep(2000);
            return;
        }

        /* ADICAO NO ARQUIVO */
        Consulta consulta = new Consulta(pacienteEncontrado, medicoEncontrado, dataHora, local, "AGENDADA");

        try (PrintWriter escrever = new PrintWriter(new FileWriter("agenda_consultas.txt", true))) {
            escrever.println(pacienteEncontrado.getNome() + ";" +
                    medicoEncontrado.getNome() + ";" +
                    consulta.getDataHora().format(formatter) + ";" +
                    consulta.getLocal() + ";" +
                    consulta.getStatus());
        }

        System.out.println();
        Thread.sleep(1000);
        System.out.println("Consulta agendada com sucesso!");
        System.out.println();
        Thread.sleep(2000);



        /* ADICAO EM MEDICO E PACIENTE */
        String descricaoP = "Consulta do paciente com o Dr/Dra. " + medicoEncontrado.getNome() +
                " em " + dataHora + " - AGENDADA";
        pacienteEncontrado.addConsulta(descricaoP);
        pacienteEncontrado.atualizarPacienteArquivo(pacienteEncontrado);

        String descricaoM = "Consulta: Paciente " + pacienteEncontrado.getNome() + " - em " +
                dataHora;
        medicoEncontrado.addConsultaMedico(descricaoM);
        medicoEncontrado.atualizarMedicoArquivo(medicoEncontrado);

    }


    /* METODO AGENDA DE CONSULTAS */
    public ArrayList<Consulta> agendaDeConsultas() throws IOException {
        ArrayList<Consulta> consultas = new ArrayList<>();
        File arquivo = new File("agenda_consultas.txt");

        if (!arquivo.exists()) {
            return consultas;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try (Scanner leitor = new Scanner(arquivo)) {
            while (leitor.hasNextLine()) {
                String c = leitor.nextLine();
                String[] partes = c.split(";");

                if (partes.length < 5) continue;

                String nomeP = partes[0];
                String nomeM = partes[1];
                LocalDateTime dataHora = LocalDateTime.parse(partes[2], formatter);
                String local = partes[3];
                String status = partes[4];

                Paciente paciente = new Paciente();
                for (Paciente p : paciente.listaPacientes()) {
                    if (p.getNome().equalsIgnoreCase(nomeP)) {
                        paciente = p;
                        break;
                    }
                }

                Medico medico = new Medico();
                for (Medico m : medico.listaMedicos()) {
                    if (m.getNome().equalsIgnoreCase(nomeM)) {
                        medico = m;
                        break;
                    }
                }

                consultas.add(new Consulta(paciente, medico, dataHora, local, status));
            }
        }
        return consultas;
    }

    /* METODO ATUALIXAR ARQUIVO CONSULTAS */
    public void atualizarArquivoConsultas(ArrayList<Consulta> consultas) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        try (PrintWriter escrever = new PrintWriter(new FileWriter("agenda_consultas.txt"))) {
            for (Consulta c : consultas) {
                escrever.println(c.getPaciente().getNome() + ";" +
                        c.getMedico().getNome() + ";" +
                        c.getDataHora().format(formatter) + ";" +
                        c.getLocal() + ";" +
                        c.getStatus());
            }
        }
    }


    /* METODO CONCLUIR CONSULTA */
    public void concluirConsulta(Scanner scanner, ArrayList<Consulta> consultas) throws IOException, InterruptedException {
        if (consultas.isEmpty()) {
            System.out.println();
            Thread.sleep(1000);
            System.out.println("Não há consultas cadastradas!");
            System.out.println();
            Thread.sleep(2000);
            return;
        }

        //Escolher consulta
        System.out.print("Nome do Paciente: ");
        String nomeP = scanner.nextLine();

        System.out.print("Nome do Médico: ");
        String nomeM = scanner.nextLine();

        System.out.print("Data e hora da consulta (AAAA-MM-DDTHH:MM): ");
        String dataStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataStr, formatter);

        Consulta consultaSelecionada = null;
        for (Consulta c : consultas) {
            if (c.getPaciente().getNome().equalsIgnoreCase(nomeP) &&
                    c.getMedico().getNome().equalsIgnoreCase(nomeM) &&
                    c.getDataHora().equals(dataHora)) {
                consultaSelecionada = c;
                break;
            }
        }

        if (consultaSelecionada == null) {
            System.out.println();
            Thread.sleep(1000);
            System.out.println("Consulta não encontrada!");
            System.out.println();
            Thread.sleep(2000);
            return;
        }

        //Inserir diagnóstico e prescrição
        System.out.print("Digite o diagnóstico: ");
        String diagnostico = scanner.nextLine();

        System.out.print("Digite a prescrição: ");
        String prescricao = scanner.nextLine();

        String descricaoPaciente = "Consulta concluída: " + diagnostico + " | Prescrição: " + prescricao;
        consultaSelecionada.getPaciente().addConsulta(descricaoPaciente);
        consultaSelecionada.getPaciente().atualizarPacienteArquivo(consultaSelecionada.getPaciente());

        String descricaoMedico = "Consulta concluída: Paciente " + consultaSelecionada.getPaciente().getNome() +
                " - " + diagnostico + " | Prescrição: " + prescricao;
        consultaSelecionada.getMedico().addConsultaMedico(descricaoMedico);
        consultaSelecionada.getMedico().atualizarMedicoArquivo(consultaSelecionada.getMedico());

        //Atualizar status e arquivo
        consultaSelecionada.setStatus("CONCLUIDA");
        new Consulta().atualizarArquivoConsultas(consultas);

        System.out.println();
        Thread.sleep(1000);
        System.out.println("Consulta concluída com sucesso!");
        System.out.println();
        Thread.sleep(2000);
    }
}