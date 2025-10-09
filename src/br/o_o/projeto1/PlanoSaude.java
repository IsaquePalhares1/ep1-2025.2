package br.o_o.projeto1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PlanoSaude {

    /*  ATRIBUTOS */
    private String nome;
    private double descontoCardiologia;
    private double descontoDermatologia;
    private double descontoPediatria;
    private double descontoOrtopedia;
    private double descontoNeurologia;
    private double descontoGinecologia;
    private double descontoPsiquiatria;
    private double descontoOftalmologia;
    private boolean internacaoGratuitaSemana;
    private double descontoIdoso;


    /*  CONSTRUTORES */
    public PlanoSaude(String nome){
        this.nome = nome;
        this.descontoCardiologia = 0.0;
        this.descontoDermatologia = 0.0;
        this.descontoPediatria = 0.0;
        this.descontoOrtopedia = 0.0;
        this.descontoNeurologia = 0.0;
        this.descontoGinecologia = 0.0;
        this.descontoPsiquiatria = 0.0;
        this.descontoOftalmologia = 0.0;
        this.internacaoGratuitaSemana= false;
        this.descontoIdoso = 0.0;
    }

    public PlanoSaude(String nome, double dCardiologia, double dDermatologia, double dPediatria,
                      double dOrtopedia, double dNeurologia, double dGinecologia, double dPsiquiatria,
                      double dOftalmologia, boolean isSemanaGratuita, double dIdoso) {

        this.nome = nome;
        this.descontoCardiologia = dCardiologia;
        this.descontoDermatologia = dDermatologia;
        this.descontoPediatria = dPediatria;
        this.descontoOrtopedia = dOrtopedia;
        this.descontoNeurologia = dNeurologia;
        this.descontoGinecologia = dGinecologia;
        this.descontoPsiquiatria = dPsiquiatria;
        this.descontoOftalmologia = dOftalmologia;
        this.internacaoGratuitaSemana = isSemanaGratuita;
        this.descontoIdoso = dIdoso;
    }

    public PlanoSaude(){
        this.nome = " ";
        this.descontoCardiologia = 0.0;
        this.descontoDermatologia = 0.0;
        this.descontoPediatria = 0.0;
        this.descontoOrtopedia = 0.0;
        this.descontoNeurologia = 0.0;
        this.descontoGinecologia = 0.0;
        this.descontoPsiquiatria = 0.0;
        this.descontoOftalmologia = 0.0;
        this.internacaoGratuitaSemana= false;
        this.descontoIdoso = 0.0;
    }


    /* MÉTODOS GET */
    public String getNome() {
        return nome;
    }

    public String isInternacaoGratuitaSemana(){

        if (internacaoGratuitaSemana == true){
            return "Sim";
        }
        else {
            return "Não";
        }

    }

    public double getDescontoIdoso() {
        return descontoIdoso;
    }

    public double getDescontoCardiologia() {
        return descontoCardiologia;
    }

    public double getDescontoDermatologia() {
        return descontoDermatologia;
    }

    public double getDescontoPediatria() {
        return descontoPediatria;
    }

    public double getDescontoOrtopedia() {
        return descontoOrtopedia;
    }

    public double getDescontoNeurologia() {
        return descontoNeurologia;
    }

    public double getDescontoGinecologia() {
        return descontoGinecologia;
    }

    public double getDescontoPsiquiatria() {
        return descontoPsiquiatria;
    }

    public double getDescontoOftalmologia() {
        return descontoOftalmologia;
    }


    /*  MÉTODOS SET  */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInternacaoGratuitaSemana(boolean internacaoGratuitaSemana) {
        this.internacaoGratuitaSemana = internacaoGratuitaSemana;
    }

    public void setDescontoIdoso(double descontoIdoso) {
        this.descontoIdoso = descontoIdoso;
    }

    public void setDescontoCardiologia(double descontoCardiologia) {
        this.descontoCardiologia = descontoCardiologia;
    }

    public void setDescontoDermatologia(double descontoDermatologia) {
        this.descontoDermatologia = descontoDermatologia;
    }

    public void setDescontoPediatria(double descontoPediatria) {
        this.descontoPediatria = descontoPediatria;
    }

    public void setDescontoOrtopedia(double descontoOrtopedia) {
        this.descontoOrtopedia = descontoOrtopedia;
    }

    public void setDescontoNeurologia(double descontoNeurologia) {
        this.descontoNeurologia = descontoNeurologia;
    }

    public void setDescontoGinecologia(double descontoGinecologia) {
        this.descontoGinecologia = descontoGinecologia;
    }

    public void setDescontoPsiquiatria(double descontoPsiquiatria) {
        this.descontoPsiquiatria = descontoPsiquiatria;
    }

    public void setDescontoOftalmologia(double descontoOftalmologia) {
        this.descontoOftalmologia = descontoOftalmologia;
    }


    /*  TO STRING */
    @Override
    public String toString(){
        return    "Nome do Plano: " + nome + "\n"
                + "Desconto Cardiologia: " + descontoCardiologia + "\n"
                + "Desconto Dematologia: " + descontoDermatologia + "\n"
                + "Desconto Pediatria: " + descontoPediatria + "\n"
                + "Desconto Ortopedia: " + descontoOrtopedia + "\n"
                + "Desconto Neurologia: " + descontoNeurologia + "\n"
                + "Desconto Ginecologia: " + descontoGinecologia + "\n"
                + "Desconto Psiquiatria: " + descontoPsiquiatria + "\n"
                + "Desconto Oftalmologia: " + descontoOftalmologia + "\n"
                + "Tem internação gratuita: " + isInternacaoGratuitaSemana() + "\n"
                + "Desconto para idoso: " + descontoIdoso;
    }


    /* ADICIONAR PLANO SAUDE */
    public void cadastrarPlano(Scanner scanner) throws IOException, InterruptedException {
        PlanoSaude planoSaude = new PlanoSaude();

        System.out.print("Nome do plano de saúde: ");
        String nome = scanner.nextLine();
        planoSaude.setNome(nome);

        System.out.print("Desconto para Cardiologia(%): ");
        double dCardiologia = scanner.nextDouble();
        planoSaude.setDescontoCardiologia(dCardiologia);

        System.out.print("Desconto para Dermatologia(%): ");
        double dDermatologia = scanner.nextDouble();
        planoSaude.setDescontoDermatologia(dDermatologia);

        System.out.print("Desconto para Pediatria(%): ");
        double dPediatria = scanner.nextDouble();
        planoSaude.setDescontoPediatria(dPediatria);

        System.out.print("Desconto para Ortopedia(%): ");
        double dOrtopedia = scanner.nextDouble();
        planoSaude.setDescontoOrtopedia(dOrtopedia);

        System.out.print("Desconto para Neurologia(%): ");
        double dNeurologia = scanner.nextDouble();
        planoSaude.setDescontoNeurologia(dNeurologia);

        System.out.print("Desconto para Ginecologia(%): ");
        double dGinecologia = scanner.nextDouble();
        planoSaude.setDescontoGinecologia(dGinecologia);

        System.out.print("Desconto para Psiquiatria(%): ");
        double dPsiquiatria = scanner.nextDouble();
        planoSaude.setDescontoPsiquiatria(dPsiquiatria);

        System.out.print("Desconto para Oftalmologia(%): ");
        double dOftalmologia = scanner.nextDouble();
        planoSaude.setDescontoOftalmologia(dOftalmologia);

        System.out.print("Desconto para Idoso(%): ");
        double dIdoso = scanner.nextDouble();
        planoSaude.setDescontoIdoso(dIdoso);

        System.out.print("Gratuiddae para uma semana de internação(true/false): ");
        boolean isSemanaGratuita = scanner.nextBoolean();
        planoSaude.setInternacaoGratuitaSemana(isSemanaGratuita);


        /*ADIÇAO NO ARQUIVO*/
        try(PrintWriter escrever = new PrintWriter(new FileWriter("cadastro_planos_saude.txt", true))) {
            escrever.println(planoSaude.getNome() + ";" +
                    planoSaude.getDescontoCardiologia() + ";" +
                    planoSaude.getDescontoDermatologia() + ";" +
                    planoSaude.getDescontoPediatria() + ";" +
                    planoSaude.getDescontoOrtopedia() + ";" +
                    planoSaude.getDescontoNeurologia() + ";" +
                    planoSaude.getDescontoGinecologia() + ";" +
                    planoSaude.getDescontoPsiquiatria() + ";" +
                    planoSaude.getDescontoOftalmologia() + ";" +
                    planoSaude.getDescontoIdoso() + ";" +
                    planoSaude.isInternacaoGratuitaSemana());


        }

        System.out.println();
        Thread.sleep(1000);
        System.out.println("Plano cadastrado com sucesso!");
        System.out.println();
        Thread.sleep(2000);

    }

    /* METODO LISTA DE PACIENTES */
    public ArrayList<PlanoSaude> listaPlanoSaude() throws IOException {
        ArrayList<PlanoSaude> planos = new ArrayList<>();

        File arquivo = new File("cadastro_planos_saude.txt");


        try (Scanner leitor = new Scanner(arquivo)) {

            while (leitor.hasNextLine()) {

                String linha = leitor.nextLine();

                if (linha.trim().isEmpty()) {
                    continue;
                }

                String p = leitor.nextLine();
                String[] partes = p.split(";");


                if (partes.length < 11) {
                    continue;
                }


                String nome = partes[0];
                double dCardiologia = Double.parseDouble(partes[1]);
                double dDermatologia = Double.parseDouble(partes[2]);
                double dPediatria = Double.parseDouble(partes[3]);
                double dOrtopedia = Double.parseDouble(partes[4]);
                double dNeurologia = Double.parseDouble(partes[5]);
                double dGinecologia = Double.parseDouble(partes[6]);
                double dPsiquiatria = Double.parseDouble(partes[7]);
                double dOftalmologia = Double.parseDouble(partes[8]);
                double dIdoso = Double.parseDouble(partes[9]);
                boolean isSemanaGratuita = Boolean.parseBoolean(partes[10]);




                PlanoSaude planoSaude = new PlanoSaude(nome, dCardiologia, dDermatologia, dPediatria, dOrtopedia, dNeurologia, dGinecologia, dPsiquiatria, dOftalmologia, isSemanaGratuita, dIdoso);
                planos.add(planoSaude);
            }

            return planos;
        }
    }


}



