package com.parse.starter.util;

import java.util.HashMap;

public class ParseErros {

    private HashMap<Integer, String> erros;

    public ParseErros() {
        this.erros = new HashMap<>();
        this.erros.put(101,"Usuário ou senha inválidos.");
        this.erros.put(125,"Email inválido.");
        this.erros.put(200,"Usuário não preenchido.");
        this.erros.put(201,"Senha não preenchida.");
        this.erros.put(202,"Usuário já existe, escolha outro nome de usuário.");
        this.erros.put(203,"Email já cadastrado, escolha outro.");
        this.erros.put(204,"Email não preenchido.");
        this.erros.put(205,"Email não encontrado.");

    }

    public String getErro(int codErro){
        return this.erros.get(codErro);
    }
}