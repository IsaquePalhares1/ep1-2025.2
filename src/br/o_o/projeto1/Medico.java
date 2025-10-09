package br.o_o.projeto1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Medico{

    /*    ATRIBUTOS    */
    private String nome;
    private String crm;
    private double custoConsulta;
    private Especialidade especialidade;
    private ArrayList<String> agendaHorarios;


    /*  CONSTRUTORES */
    public Medico(String nome, String crm, double custoConsulta, Especialidade especialidade, ArrayList<String> agendaHorarios){
        this.nome= nome;
        this.crm = crm;
        this.custoConsulta = custoConsulta;
        this.especialidade = especialidade;
        this.agendaHorarios = agendaHorarios;
    }

    public Medico(){
        this.nome= " ";
        this.crm = " ";
        this.custoConsulta = 0.0;
        this.especialidade = Especialidade.PADRAO;
        this.agendaHorarios = new ArrayList<>();
    }


    /* MÉTODOS GET */
    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }

    public double getCustoConsulta() {
        return custoConsulta;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public ArrayList<String> getAgendaHorarios() {
        return agendaHorarios;
    }

    /*  MÉTODOS SET  */

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void setCustoConsulta(double custoConsulta) {
        this.custoConsulta = custoConsulta;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    /* METODO ADICIORNAR DESCRICAO DE CONSULTA A AGENDA HORARIOS */
    public void addConsultaMedico(String descricao){
        if (descricao != null && !descricao.trim().isEmpty()){
            agendaHorarios.add(descricao);
        }
    }

    /* METODO ADICIORNAR DESCRICAO DE INTERNACAO A AGENDA HORARIOS */
    public void addInternacaoMedico(String descricao){
        if (descricao != null && !descricao.trim().isEmpty()){
            agendaHorarios.add(descricao);
        }
    }

    /* TO STRING */
    @Override
    public String toString(){
        return    "Nome: " + nome + "\n"
                + "Crm: " + crm + "\n"
                + "Custo da Consulta: " + custoConsulta + "\n"
                + "Especialidade: " + especialidade + "\n"
                + "Agenda de horários: " + String.join("|", agendaHorarios);

    }

    /* METODO CADASTRAR MEDICO */
    public void cadastrarMedico(Scanner scanner) throws IOException, InterruptedException {
        Medico medico = new Medico();

        System.out.print("Nome do médico: ");
        String nome = scanner.nextLine();
        medico.setNome(nome);

        System.out.print("Crm do médico: ");
        String crm = scanner.nextLine();
        medico.setCrm(crm);

        System.out.print("Custo das consultas do médico: ");
        double custoConsulta = scanner.nextDouble();
        medico.setCustoConsulta(custoConsulta);
        scanner.nextLine();

        System.out.print("Especialidade do médico: ");
        Especialidade especialidade = Especialidade.parseEspecialidade(scanner.nextLine());
        medico.setEspecialidade(especialidade);


        /*ADIÇAO NO ARQUIVO*/
        try(PrintWriter escrever = new PrintWriter(new FileWriter("cadastro_medicos.txt", true))) {
            escrever.println(medico.getNome() + ";" +
                    medico.getCrm() + ";" +
                    medico.getCustoConsulta() + ";" +
                    medico.getEspecialidade() + ";" +
                    String.join("|", medico.getAgendaHorarios()));



        }

        System.out.println();
        Thread.sleep(1000);
        System.out.println("Médico Cadastrado com sucesso!");
        System.out.println();
        Thread.sleep(2000);

    }


    /* METODO LISTA DE MEDICOS */
    public ArrayList<Medico> listaMedicos() throws IOException {
        ArrayList<Medico> medicos = new ArrayList<>();

        File arquivo = new File("cadastro_medicos.txt");


        try (Scanner leitor = new Scanner(arquivo)) {

            while (leitor.hasNextLine()) {

                String m = leitor.nextLine();
                String[] partes = m.split(";");
                String nome = partes[0];
                String crm = partes[1];
                double custoConsulta = Double.parseDouble(partes[2]);
                Especialidade especialidade = Especialidade.parseEspecialidade(partes[3]);

                ArrayList<String> agendaHorarios = new ArrayList<>();


                if (partes.length > 4 && !partes[4].isEmpty())
                    agendaHorarios = new ArrayList<>(java.util.Arrays.asList(partes[4].split("\\|")));


                Medico medico = new Medico(nome, crm, custoConsulta, especialidade, agendaHorarios);
                medicos.add(medico);
            }

            return medicos;
        }
    }


    /* METODO ATUALIZAR MEDICO NO ARQUIVO */
    public void atualizarMedicoArquivo(Medico medico) throws IOException {
        ArrayList<Medico> medicos = medico.listaMedicos();
        ArrayList<String> linhas = new ArrayList<>();

        for (Medico m : medicos) {
            if (m.getCrm().equals(medico.getCrm())) {
                linhas.add(medico.getNome() + ";" +
                        medico.getCrm() + ";" +
                        medico.getCustoConsulta() + ";" +
                        medico.getEspecialidade() + ";" +
                        String.join("|", medico.getAgendaHorarios()));
            } else {
                linhas.add(m.getNome() + ";" +
                        m.getCrm() + ";" +
                        m.getCustoConsulta() + ";" +
                        m.getEspecialidade() + ";" +
                        String.join("|", m.getAgendaHorarios()));
            }
        }

        try (PrintWriter escrever = new PrintWriter(new FileWriter("cadastro_medicos.txt"))) {
            for (String linha : linhas) {
                escrever.println(linha);
            }
        }
    }

}