package br.o_o.projeto1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Internacao{

    /*    ATRIBUTOS    */
    private Paciente paciente;
    private Medico medicoResponsavel;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private int quarto;
    private double custo;


    /*  CONSTRUTOR */
    public Internacao(Paciente paciente, Medico medicoResponsavel, LocalDate dataEntrada, LocalDate dataSaida, int quarto, double custo){
        this.paciente= paciente;
        this.medicoResponsavel = medicoResponsavel;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.quarto = quarto;
        this.custo = custo;
    }

    public Internacao(){
        this.paciente= new Paciente();
        this.medicoResponsavel = new Medico();
        this.dataEntrada = null;
        this.dataSaida = null;
        this.quarto = 0;
        this.custo = 0.0;
    }


    /* MÉTODOS GET */
    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public int getQuarto() {
        return quarto;
    }

    public double getCusto() {
        return custo;
    }


    /*  MÉTODOS SET  */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medicoResponsavel) {
        this.medicoResponsavel = medicoResponsavel;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setQuarto(int quarto) {
        this.quarto = quarto;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }


    /* TO STRING */
    @Override
    public String toString(){
        return    "Paciente: " + paciente + "\n"
                + "Médico Responsável: " + medicoResponsavel + "\n"
                + "Data de Entrada: " + dataEntrada + "\n"
                + "Data de Saída: " + dataSaida + "\n"
                + "Quarto: " + quarto + "\n"
                + "Custo: " + custo;

    }



    /* METODO MARCAR INTERNACAO */
    public void marcarInternacao(Scanner scanner) throws IOException, InterruptedException {

        System.out.print("Nome do Paciente: ");
        String nomeP = scanner.nextLine();

        System.out.print("Cpf do Paciente: ");
        String cpf = scanner.nextLine();

        System.out.print("Nome do Médico: ");
        String nomeM = scanner.nextLine();

        System.out.print("Crm do Médico: ");
        String crm = scanner.nextLine();

        System.out.print("Data de Entrada (formato: AAAA-MM-DD): ");
        String dataEntradaStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataEntrada = LocalDate.parse(dataEntradaStr, formatter);

        String dataSaidaStr = null;

        System.out.print("Quarto: ");
        int quarto = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Custo: ");
        double custo = scanner.nextDouble();
        scanner.nextLine();



        Paciente cadastroP = new Paciente();
        cadastroP.setNome(nomeP);
        cadastroP.setCpf(cpf);
        ArrayList<Paciente> listaPaciente = cadastroP.listaPacientes();
        for (Paciente p : listaPaciente) {
            if (p.getNome().equals(cadastroP.getNome()) && p.getCpf().equals(cadastroP.getCpf())) {
                cadastroP = p;
                break;
            }
        }

        Medico cadastroM = new Medico();
        cadastroM.setNome(nomeM);
        cadastroM.setCrm(crm);
        ArrayList<Medico> listaMedico = cadastroM.listaMedicos();
        for (Medico m : listaMedico) {
            if (m.getNome().equals(cadastroM.getNome()) && m.getCrm().equals(cadastroM.getCrm())) {
                cadastroM = m;
                break;
            }
        }

        Internacao internacao = new Internacao(cadastroP, cadastroM, dataEntrada, dataSaida, quarto, custo);

        /* ADICAO EM MEDICO E PACIENTE */
        String descricaoP = "Internação na data " + dataEntrada + " com o médico responsável " +
                cadastroM.getNome() + " - " + quarto;
        cadastroP.addInternacao(descricaoP);
        cadastroP.atualizarPacienteArquivo(cadastroP);

        String descricaoM = "Internação: Paciente " + cadastroP.getNome() + " - " + quarto + " - em " +
                dataEntrada;
        cadastroM.addInternacaoMedico(descricaoM);
        cadastroM.atualizarMedicoArquivo(cadastroM);

        /* ADIÇÃO NO ARQUIVO */
        try (PrintWriter escrever = new PrintWriter(new FileWriter("marcacoes_internacoes.txt", true))) {
            escrever.println(cadastroP.getNome() + ";" +
                    cadastroM.getNome() + ";" +
                    internacao.getDataEntrada().format(formatter) + ";" +
                    (internacao.getDataSaida() != null ? internacao.getDataSaida().format(formatter) : "") + ";" +
                    internacao.getQuarto() + ";" +
                    internacao.getCusto());
        }

        System.out.println();
        Thread.sleep(1000);
        System.out.println("Internação marcada com sucesso!");
        System.out.println();
        Thread.sleep(2000);
    }




    /* METODO HISTORICO DE INTERNACAO */
    public ArrayList<Internacao> historicoDeInternacoes() throws IOException {
        ArrayList<Internacao> internacoes = new ArrayList<>();

        File arquivo = new File("marcacoes_internacoes.txt");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (Scanner leitor = new Scanner(arquivo)) {
            while (leitor.hasNextLine()) {
                String c = leitor.nextLine();
                String[] partes = c.split(";");
                String nomeP = partes[0];
                String nomeM = partes[1];
                LocalDate dataEntrada = LocalDate.parse(partes[2], formatter);
                LocalDate dataSaida = partes[3].isEmpty() ? null : LocalDate.parse(partes[3], formatter1);
                int quarto = Integer.parseInt(partes[4]);
                double custo = Double.parseDouble(partes[5]);

                Paciente paciente = new Paciente();
                for (Paciente p : paciente.listaPacientes()) {
                    if (p.getNome().equals(nomeP)) {
                        paciente = p;
                        break;
                    }
                }

                Medico medico = new Medico();
                for (Medico m : medico.listaMedicos()) {
                    if (m.getNome().equals(nomeM)) {
                        medico = m;
                        break;
                    }
                }

                Internacao internacao = new Internacao(paciente, medico, dataEntrada, dataSaida, quarto, custo);
                internacoes.add(internacao);
            }
        }

        return internacoes;
    }


    /* METODO ATUALIZAR ARQUIVO INTERNACOES */
    public static void atualizarArquivoInternacoes(ArrayList<Internacao> internacoes) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (PrintWriter escrever = new PrintWriter(new FileWriter("marcacoes_internacoes.txt"))) {
            for (Internacao i : internacoes) {
                escrever.println(i.getPaciente().getNome() + ";" +
                        i.getMedicoResponsavel().getNome() + ";" +
                        i.getDataEntrada().format(formatter) + ";" +
                        (i.getDataSaida() != null ? i.getDataSaida().format(formatter) : "") + ";" +
                        i.getQuarto() + ";" +
                        i.getCusto());
            }
        }
    }


    /* METODO CANCELAR INTERNACAO */
    public void cancelarInternacao(Scanner scanner, ArrayList<Internacao> internacoes) throws IOException, InterruptedException {
        if (internacoes.isEmpty()) {
            System.out.println();
            Thread.sleep(1000);
            System.out.println("Não há internações cadastradas!");
            System.out.println();
            Thread.sleep(2000);
            return;
        }

        //Selecionar internação
        System.out.print("Nome do Paciente: ");
        String nomeP = scanner.nextLine();

        System.out.print("Nome do Médico Responsável: ");
        String nomeM = scanner.nextLine();

        System.out.print("Data de Entrada da internação (AAAA-MM-DD): ");
        String dataStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataEntrada = LocalDate.parse(dataStr, formatter);

        Internacao internacaoSelecionada = null;
        for (Internacao i : internacoes) {
            if (i.getPaciente().getNome().equalsIgnoreCase(nomeP) &&
                    i.getMedicoResponsavel().getNome().equalsIgnoreCase(nomeM) &&
                    i.getDataEntrada().equals(dataEntrada)) {
                internacaoSelecionada = i;
                break;
            }
        }

        if (internacaoSelecionada == null) {
            System.out.println();
            Thread.sleep(1000);
            System.out.println("Internação não encontrada!");
            System.out.println();
            Thread.sleep(2000);
            return;
        }

        //Atualizar data de saída e histórico
        internacaoSelecionada.setDataSaida(LocalDate.now());

        String descricaoP = "Internação iniciada em " + internacaoSelecionada.getDataEntrada() + " e encerrada em " + internacaoSelecionada.getDataSaida();
        internacaoSelecionada.getPaciente().addInternacao(descricaoP);
        internacaoSelecionada.getPaciente().atualizarPacienteArquivo(internacaoSelecionada.getPaciente());

        String descricaoM = "Paciente: " + internacaoSelecionada.getPaciente().getNome() + " -- Internação iniciada em: " + internacaoSelecionada.getDataEntrada() + " -- Internação encerrada em: " + internacaoSelecionada.getDataSaida();
        internacaoSelecionada.getMedicoResponsavel().addInternacaoMedico(descricaoM);
        internacaoSelecionada.getMedicoResponsavel().atualizarMedicoArquivo(internacaoSelecionada.getMedicoResponsavel());

        //Atualizar arquivo de internações
        Internacao.atualizarArquivoInternacoes(internacoes);


        System.out.println();
        Thread.sleep(1000);
        System.out.println("Internação cancelada/encerrada com sucesso!");
        System.out.println();
        Thread.sleep(2000);
    }
}