package br.o_o.projeto1;

import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;

public class Paciente {

 /*    ATRIBUTOS    */
    private String nome;
    private String cpf;
    private int idade;
    private ArrayList<String> histConsulta; //Histórico de consultas
    private ArrayList<String> histInternacao; //Histórico de internações


    /*  CONSTRUTORES */
    public Paciente(String nome, String cpf, int idade, ArrayList<String> histConsulta, ArrayList<String> histInternacao){
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.histConsulta = histConsulta;
        this.histInternacao = histInternacao;
    }

    public Paciente(){
        this.nome = " ";
        this.cpf = " ";
        this.idade = 0;
        this.histConsulta = new ArrayList<>(0);
        this.histInternacao = new ArrayList<>(0);
    }


    /* MÉTODOS GET */
    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public int getIdade(){
        return idade;
    }

    public ArrayList<String> getHistConsultas(){
        return histConsulta;
    }

    public ArrayList<String> getHistInternacoes(){
        return histInternacao;
    }



    /*  MÉTODOS SET  */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    /* METODO ADICIORNAR DESCRICAO A HIST CONSULTAS */
    public void addConsulta(String descricao){
        if (descricao != null && !descricao.trim().isEmpty()){
            histConsulta.add(descricao);
        }
    }

    /* METODO ADICIORNAR INTERNACAO A HIST INTERNACAO */
    public void addInternacao(String descricao){
        if (descricao != null && !descricao.trim().isEmpty()){
            histInternacao.add(descricao);
        }
    }


    /* TO STRING */
    @Override
    public String toString(){
        return    "Nome: " + nome + "\n"
                + "Cpf: " + cpf + "\n"
                + "Idade: " + idade + "\n"
                + "Histórico de Consultas: " + String.join(" | ", histConsulta) + "\n"
                + "Histórico de Internações: " + String.join(" | ", histInternacao);
    }


    /* MÉTODOS VALOR CONSULTA */
    public double valorConsulta(Medico medico){
        return medico.getCustoConsulta();

    }

    /* METODO CADASTRAR PACIENTE */
    public void cadastrarPaciente(Scanner scanner) throws IOException, InterruptedException {
        Paciente paciente = new Paciente();

        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        paciente.setNome(nome);

        System.out.print("Cpf do paciente: ");
        String cpf = scanner.nextLine();
        paciente.setCpf(cpf);

        System.out.print("Idade do paciente: ");
        int idade = scanner.nextInt();
        paciente.setIdade(idade);
        scanner.nextLine();


        /*ADIÇAO NO ARQUIVO*/
        try(PrintWriter escrever = new PrintWriter(new FileWriter("cadastro_pacientes.txt", true))) {
            escrever.println(paciente.getNome() + ";" +
                    paciente.getCpf() + ";" +
                    paciente.getIdade() + ";" +
                    String.join("|", paciente.getHistConsultas()) + ";" +
                    String.join("|", paciente.getHistInternacoes()));

        }

        System.out.println();
        Thread.sleep(1000);
        System.out.println("Paciente Cadastrado com sucesso!");
        System.out.println();
        Thread.sleep(2000);

    }

    /* METODO LISTA DE PACIENTES */
    public ArrayList<Paciente> listaPacientes() throws IOException {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        File arquivo = new File("cadastro_pacientes.txt");

        try (Scanner leitor = new Scanner(arquivo)) {
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");

                if (partes.length < 3) continue;

                String nome = partes[0];
                String cpf = partes[1];
                int idade = Integer.parseInt(partes[2]);

                ArrayList<String> histConsultas = new ArrayList<>();
                ArrayList<String> histInternacoes = new ArrayList<>();

                if (partes.length > 3 && !partes[3].isEmpty())
                    histConsultas = new ArrayList<>(java.util.Arrays.asList(partes[3].split("\\|")));

                if (partes.length > 4 && !partes[4].isEmpty())
                    histInternacoes = new ArrayList<>(java.util.Arrays.asList(partes[4].split("\\|")));

                Paciente paciente = new Paciente(nome, cpf, idade, histConsultas, histInternacoes);
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }


    /* METODO ATUALIZAR PACIENTE NO ARQUIVO */
    public void atualizarPacienteArquivo(Paciente paciente) throws IOException {
        ArrayList<Paciente> pacientes = paciente.listaPacientes();
        ArrayList<String> linhas = new ArrayList<>();

        for (Paciente p : pacientes) {
            if (p.getCpf().equals(paciente.getCpf())) {
                linhas.add(paciente.getNome() + ";" +
                        paciente.getCpf() + ";" +
                        paciente.getIdade() + ";" +
                        String.join("|", paciente.getHistConsultas()) + ";" +
                        String.join("|", paciente.getHistInternacoes()));
            } else {
                linhas.add(p.getNome() + ";" +
                        p.getCpf() + ";" +
                        p.getIdade() + ";" +
                        String.join("|", p.getHistConsultas()) + ";" +
                        String.join("|", p.getHistInternacoes()));
            }
        }

        try (PrintWriter escrever = new PrintWriter(new FileWriter("cadastro_pacientes.txt"))) {
            for (String linha : linhas) {
                escrever.println(linha);
            }
        }
    }
}
