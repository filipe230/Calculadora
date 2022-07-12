package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Calculadora calculadora = new Calculadora();

    private TextView resultado ,expressao ,limparmain ,abrirparent, fecharparent, dividir,mult, sub,soma,ponto,calcular,txtParenteses,
            btesp,porcentagem,raiz,potencia,fatorial;
    private ImageView back;
    private TextView num0,num1,num2,num3,num4,num5,num6,num7,num8,num9;
    private boolean expval = false;
    boolean ver = false;
    private LinearLayout especial;
    String strparenteses = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        expressao  = findViewById(R.id.txtExpressao);
        resultado =  findViewById(R.id.txtresultado);

        num0  =  findViewById(R.id.txtnum0);
        num1  =  findViewById(R.id.Num1);
        num2  =  findViewById(R.id.Num2);
        num3  =  findViewById(R.id.Num3);
        num4  =  findViewById(R.id.Num4);
        num5  =  findViewById(R.id.Num5);
        num6  =  findViewById(R.id.Num6);
        num7  =  findViewById(R.id.Num7);
        num8  =  findViewById(R.id.Num8);
        num9  =  findViewById(R.id.Num9);
        limparmain =  findViewById(R.id.Limpar);
        abrirparent =  findViewById(R.id.Abrirparenteses);
        fecharparent =  findViewById(R.id.Fecharparenteses);
        dividir =  findViewById(R.id.Dividir);
        mult =  findViewById(R.id.Multiplicaçao);
        sub =  findViewById(R.id.Subtraçao);
        soma =  findViewById(R.id.Soma);
        ponto =  findViewById(R.id.Ponto);
        back =  findViewById(R.id.Back);
        calcular =  findViewById(R.id.Calcular);
        txtParenteses = findViewById(R.id.txtParenteses);

        potencia = findViewById(R.id.potencia);
        fatorial = findViewById(R.id.fatorial);
        porcentagem = findViewById(R.id.porcentagem);
        raiz = findViewById(R.id.raiz);
        btesp = findViewById(R.id.btnesp);
        especial = findViewById(R.id.especial);

        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);

        limparmain.setOnClickListener(this);
        abrirparent.setOnClickListener(this);
        fecharparent.setOnClickListener(this);
        dividir.setOnClickListener(this);
        mult.setOnClickListener(this);
        sub.setOnClickListener(this);
        soma.setOnClickListener(this);
        ponto.setOnClickListener(this);
        back.setOnClickListener(this);
        calcular.setOnClickListener(this);

        btesp.setOnClickListener(this);
        potencia.setOnClickListener(this);
        raiz.setOnClickListener(this);
        fatorial.setOnClickListener(this);
        porcentagem.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = (int)view.getId();
        if(id == R.id.txtnum0){AddExp("0");}
        if(id == R.id.Num1){AddExp("1"); calcularExpAux();}
        if(id == R.id.Num2){AddExp("2"); calcularExpAux();}
        if(id == R.id.Num3){AddExp("3"); calcularExpAux();}
        if(id == R.id.Num4){AddExp("4"); calcularExpAux();}
        if(id == R.id.Num5){AddExp("5"); calcularExpAux();}
        if(id == R.id.Num6){AddExp("6"); calcularExpAux();}
        if(id == R.id.Num7){AddExp("7"); calcularExpAux();}
        if(id == R.id.Num8){AddExp("8"); calcularExpAux();}
        if(id == R.id.Num9){AddExp("9"); calcularExpAux();}


        if(id == R.id.Limpar){
            resultado.setText("");
            expressao.setText("");
            txtParenteses.setText("");
        }
        if(id == R.id.Back){backspace();}

        if(id == R.id.Abrirparenteses){AddExp("(");}
        if(id == R.id.Fecharparenteses){AddExp(")");}
        if(id == R.id.Dividir){AddExp("/");}
        if(id == R.id.Multiplicaçao){AddExp("*");}
        if(id == R.id.Subtraçao){AddExp("-");}
        if(id == R.id.Soma){AddExp("+");}
        if(id == R.id.Ponto){AddExp(".");}
        if(id == R.id.Calcular){calculaexp();}

        if(id == R.id.potencia){AddExp("^");}
        if(id == R.id.fatorial){AddExp("!");}
        if(id == R.id.porcentagem){AddExp("%");}
        if(id == R.id.raiz){AddExp("v");}

        if(id == R.id.btnesp){
            if(ver){
                especial.setVisibility(especial.GONE);
                ver = false;
            }
            else{
                especial.setVisibility(view.VISIBLE);
                ver = true;
            }
        }

    }

    void calcularExpAux(){
        String expre = expressao.getText().toString();

        if(calculadora.verificaparenteses(expre)){
            if(Character.isDigit(expre.charAt(expre.length()-1  )) || expre.charAt(expre.length()-1) == ')' ) {
                resultado.setText(calculadora.calc(expre));
            }
        }
        strparenteses ="";
        if(calculadora.verificaexiste(expre) && expre.charAt(expre.length()-1) != '(' && !calculadora.verificaparenteses(expre)){
            while (!calculadora.verificaparenteses(expre)){
                expre += ')';
                strparenteses += ')';
            }
            txtParenteses.setText(strparenteses);
            resultado.setText(calculadora.calc(expre));
        }else{
            txtParenteses.setText("");
        }
    }

    void verificaOperando() {
        String exp = (String) expressao.getText().toString();
        char signal = exp.charAt(exp.length() - 1);

        if (exp.length() == 1) {
            if (signal == '-')
                expressao.setText(exp);
            else if ((signal == '+' || signal== '*' || signal == '/'))
                expressao.setText("");
        } else {
            String str = "";
            char ant= exp.charAt(exp.length()-2);
            if ((ant == '+' || ant == '-' || ant == '*' || ant == '/')) {
                str += exp.substring(0, exp.length() - 2);
                str += signal;
                expressao.setText(str);
            }
        }
    }

    boolean verificaQuantParents(String exp){
        int quant = 0;

        for(int i=0;i<exp.length();i++) {
            char c = exp.charAt(i);
            if(exp.charAt(i) == '(') {
                quant++;
            }
            if(exp.charAt(i) == ')'){
                quant--;
            }
        }

        if(quant <= 0){
            return false;
        }
        return true;
    }

    String verifica_parenteses(String exp, String operador){
        char ultimovalor = exp.charAt(exp.length()-1);

        if(operador == ")") {

            if (calculadora.verificaexiste(exp) && verificaQuantParents(exp)) {
                if ((Character.isDigit(ultimovalor) || ultimovalor == ')')) {
                    return exp+operador ;
                }
            } else {
                Toast.makeText(this, "ERRO nao foi posivivel fechar parenteses", 1000).show();
                return exp;
            }
        }

        if(operador == "(" && (ultimovalor == '*' || ultimovalor  == '/' || ultimovalor  == '+' || ultimovalor  == '-' || ultimovalor  == '(')){
            return exp += operador;
        }
        return exp;
    }

    String verifica_sinal(String exp , String opera){

        char aux1 = exp.charAt(exp.length()-1);
        char aux2 = opera.charAt(0) ;
        //Character.isDigit(aux.charAt(exp.length()-1))

        if(opera == "(" || opera == ")") {
            exp = verifica_parenteses(exp , opera);
            return exp ;
        }
        if(exp.charAt(0) == '-' && (!Character.isDigit(aux2) && exp.length()<=1)){
            Toast.makeText(this, "ERRO valor inicial negativo", 1000).show();
            return exp;
        }else {
            if( (aux1 == '*' || aux1 == '/' || aux1 == '+' || aux1 == '-')  && (aux2 == '*' || aux2 == '/' || aux2 == '+' || aux2 == '-')){
                return exp.substring(0,exp.length()-1) + aux2;
                //expressao.setText();
            }
        }
        return exp+opera;
    }

    void AddExp(String exp){
        String expre = expressao.getText().toString();
        String resul = "";

        if(expre.equals("") ){
            if(exp == ")" || (exp == "*" || exp == "/" || exp == "+")){
                expressao.setText("");
                Toast.makeText(this, "ERRO valor inicial invalido", 100 ).show();
            }else {
                expressao.append(exp);
            }
        }else{
            resul = verifica_sinal(expre , exp);
            expressao.setText(resul);
        }
        //snackbar
        if(!expre.equals("")){
            calcularExpAux();
        }
    }

    void backspace(){
        String expre = expressao.getText().toString();

        if(!expre.isEmpty()){
            if(expre.length() == 1){
                expressao.setText("");
                resultado.setText("");
            }else{
                expressao.setText(expre.substring(0,expre.length()-1));
                calcularExpAux();
            }

        }
    }

    void calculaexp(){
        String exp =(String) expressao.getText().toString();
        if(calculadora.verificaexiste(exp)){
            if (!verificaQuantParents(exp)){
                resultado.setText(calculadora.calc(exp ));
                expressao.setText(resultado.getText());
                txtParenteses.setText("");
            }else{
                //Snackbar.make(, "Parenteses deve ser precedido de um numero", Snackbar.LENGTH_LONG)
                //snackbar

                Toast.makeText(this, "Fechar os parenteses", 2000).show();
            }
        }else{
            resultado.setText(calculadora.calc(exp));
            expressao.setText(resultado.getText());
            txtParenteses.setText("");

        }
    }

}