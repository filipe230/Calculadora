package com.example.calculadora;

import java.util.ArrayList;
import java.lang.*;

public class Calculadora {
    String calc(String exp) {
        String stringaux = "";
        String resultaux;
        double result = 0;
        double resultado = 0;

        ArrayList<Integer> posi = new ArrayList<Integer>();

        exp = verificaPorcentagem(exp);

        if (!verificaexiste(exp)) {
            return Double.toString(soma(exp));
        }
        if (!verificaparenteses(exp)) {
            return null;
        }

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') {
                posi.add(i);
            }
        }

        int posiatual = posi.remove(posi.size() - 1);
        int parada = exp.length();

        for (int i = posiatual; i <= parada; i++) {

            if (exp.charAt(i) == ')') {
                stringaux = exp.substring(posiatual + 1, i);

                result = soma(stringaux);

                stringaux = exp.substring(0, posiatual);

                stringaux = stringaux + result + exp.substring(i + 1, parada);

                if (result < 0 && posiatual > 0) {
                    stringaux = verificasinal(stringaux, posiatual);
                }
                exp = stringaux;

                if (posi.size() == 0) {
                    break;
                }
                if (posi.size() >= 0) {
                    parada = stringaux.length();
                    posiatual = posi.remove(posi.size() - 1);
                    i = posiatual;
                }
            }
        }
        result = soma(stringaux);
        return Double.toString(result);
    }

    String verificasinal(String exp, int posi) {
        String str = "";

        char sinal = exp.charAt(posi - 1);

        if (exp.charAt(posi - 1) == '+') {
            str = exp.substring(0, posi - 1);
            str = str + exp.substring(posi, exp.length());
            return str;
        }
        if (exp.charAt(posi - 1) == '-') {
            str = exp.substring(0, posi - 1);
            str = str + '+' + exp.substring(posi + 1, exp.length());
            return str;
        }
        for (int i = posi - 1; i >= 0; i--) {
            if (i == 0) {
                str = exp.substring(0, posi);
                str = str + exp.substring(posi + 1, exp.length());
                str = '-' + str;

                return str;
            }

            if (exp.charAt(i) == '+') {
                str = exp.substring(0, i);
                str = str + '-' + exp.substring(i + 1, exp.length());
                exp = str;
                str = exp.substring(0, posi);
                str = str + exp.substring(posi + 1, exp.length());

                return str;
            }

            if (exp.charAt(posi - 1) == '-') {
                str = exp.substring(0, i - 1);
                str = str + '+' + exp.substring(i + 1, exp.length());
                exp = str;
                str = exp.substring(0, posi - 1);
                str = str + exp.substring(posi + 1, exp.length());

                return str;
            }
        }
        return str;
    }

    boolean verificaparenteses(String exp) {
        int quant = 0;

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (exp.charAt(i) == '(') {
                quant++;
            }
            if (exp.charAt(i) == ')') {
                quant--;
            }
        }
        if (quant == 0) {
            return true;
        } else {
            return false;
        }
    }

    double soma(String exp) {
        double result = 0;

        if (exp.charAt(0) == '-' || exp.charAt(0) == '+') {
            exp = '0' + exp;
        }

        String num[] = exp.split("\\+");

        for (int i = 0; i < num.length; i++) {
            result = result + sub(num[i]);
        }

        return result;
    }

    double sub(String exp) {
        double result = 0;
        String num[] = exp.split("\\-");

        if (num.length == 1) {
            result = mult(num[0]);
            return result;
        }

        result = mult(num[0]);

        for (int i = 1; i < num.length; i++) {
            result = result - mult(num[i]);
        }

        return result;
    }

    double mult(String exp) {
        double result = 1;
        String num[] = exp.split("\\*");

        if (num.length == 1) {
            result = div(num[0]);
            return result;
        }

        //System.out.println(num[0] + "mult");

        for (int i = 0; i < num.length; i++) {
            result = result * div(num[i]);
        }

        return result;
    }

    double div(String exp) {
        double result = 1;
        double aux;
        String num[] = exp.split("\\/");

        if (num.length == 1) {
            result = esp(num[0]);
            return result;
        }

        aux = esp(num[0]);
        result = aux;

        for (int i = 1; i < num.length; i++) {
            result = result / esp(num[i]);
        }

        return result;
    }

    double fatorial(String exp) {
        int aux = Integer.parseInt(exp.substring(0,exp.length()-1));
        double resultado =  Double.valueOf(exp.substring(0,exp.length()-1)).doubleValue();

        for(int i = aux-1 ;i>1;i--) {
            resultado *= i;
        }

        return resultado;


    }

    double raiz(String exp){
        double result = 0;
        double aux = Double.valueOf(exp).doubleValue();

        result = Math.sqrt(aux);

        return result;

    }

    double potencia(String exp) {
        double result;
        double aux;
        String num[] = exp.split("\\^");

        result = Double.valueOf(num[0]).doubleValue();

        for (int x = 1; x < num.length; x++) {
            result = Math.pow(result,esp(num[x])); //especial(exp);
        }

        return result;
    }

    double esp(String exp){
        int tamanho = exp.length();

        for(int i = 0; i < tamanho; i++){
            char c = exp.charAt(i);
            if (exp.charAt(i) == '^') {
                return potencia(exp);
            }
            if (exp.charAt(i) == '!') {
                return fatorial(exp);
            }
            if (exp.charAt(i) == 'v') {
                return raiz(exp);
            }
        }

        return Double.valueOf(exp).doubleValue();
    }

    boolean verificaexiste(String exp) {
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (exp.charAt(i) == '(') {
                return true;
            }
        }
        return false;
    }

    String verificaPorcentagem(String exp) {

        String aux = exp;
        String resultado = "";
        int auxoperador = 0;
        int tamanho = aux.length();
        double percent;
        boolean prts = false;
        int i;

        for ( i = 0; i < tamanho; i++) {
            char c = exp.charAt(i);
            if (aux.charAt(i) == '%') {
                aux = aux_parenteses(aux,1,i);
                System.out.println("verifica porcentagem antes da :   " + aux);
                tamanho = aux.length();
            }
        }
        return exp;
    }

    String aux_parenteses(String exp,int tipo,int posi) {
        String aux = exp;
        String resultado="";
        double porcent;
        boolean prts = false;
        int quant=0;


        for (int i = posi; i > 0; i--) {

            if (exp.charAt(i) == '+' || exp.charAt(i) == '-') {
                if(tipo == 1) {
                    porcent = (1 + Double.valueOf(exp.substring(i, posi)).doubleValue() / 100);
                    System.out.println("verifica parenteses   3   " + porcent);

                    resultado = exp.substring(0, i)+ "*" + porcent + ")" + exp.substring(posi+1, aux.length());
                    System.out.println("verifica parenteses   RESULT PARCIAL :   " + resultado);

                    for(int j = i;j>=0;j--) {
                        if (j == 0) {
                            resultado = "(" +resultado;
                            System.out.println("verifica parenteses   resultado 33     " + resultado);

                            return resultado;
                        }

                        if ( resultado.charAt(j) == ')' ) {
                            prts = true;
                            quant++;
                        }

                        if (resultado.charAt(j) == '(' && quant == 1) {
                            resultado = resultado.substring(0,j) + "(" + resultado.substring(i,resultado.length());

                            System.out.println("verifica parenteses   resultado   " + resultado);

                            return resultado;
                        }
                        else{
                            quant--;
                        }

                        if ((resultado.charAt(j) == '+' || resultado.charAt(j) == '-' ) &&  prts == false ){
                            resultado = resultado.substring(0,j) + "(" + resultado.substring(j,resultado.length());

                            System.out.println("verifica parenteses 4  ---   " + resultado);

                            return resultado;
                        }
                    }
                }

                if(tipo == 2) {
                    porcent = (1 - Double.valueOf(exp.substring(i, posi)).doubleValue() / 100);
                    System.out.println("verifica parenteses   3   " + porcent);

                    resultado = exp.substring(0, i)+ "*" + porcent + ")" + exp.substring(posi+1, aux.length());
                    System.out.println("verifica parenteses   RESULT PARCIAL :   " + resultado);

                    for(int j = i;j>=0;j--) {
                        if (j == 0) {
                            resultado = "(" +resultado;
                            System.out.println("verifica parenteses   resultado 33     " + resultado);

                            return resultado;
                        }

                        if ( resultado.charAt(j) == ')' ) {
                            prts = true;
                            quant++;
                        }

                        if (resultado.charAt(j) == '(' && quant == 1) {
                            resultado = resultado.substring(0,j) + "(" + resultado.substring(i,resultado.length());

                            System.out.println("verifica parenteses   resultado   " + resultado);
                            return resultado;
                        }
                        else{
                            quant--;
                        }

                        if ((resultado.charAt(j) == '+' || resultado.charAt(j) == '-' ) &&  prts == false ){
                            resultado = resultado.substring(0,j) + "(" + resultado.substring(j,resultado.length());
                            System.out.println("verifica parenteses 4  ---   " + resultado);

                            return resultado;
                        }
                    }
                }
            }
        }
        return exp;
    }

}

