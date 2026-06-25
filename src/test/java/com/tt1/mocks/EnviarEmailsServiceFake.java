package com.tt1.mocks;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;

import java.util.ArrayList;
import java.util.List;

public class EnviarEmailsServiceFake implements InterfazEnviarEmails {

    public List<String> emailsEnviados = new ArrayList<>();
    public boolean simularFallo = false;

    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        if (simularFallo) return false;
        emailsEnviados.add(email);
        return true;
    }
}