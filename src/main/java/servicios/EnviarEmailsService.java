package servicios;

import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailsService implements InterfazEnviarEmails {

    private final Logger logger;

    public EnviarEmailsService(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean enviarEmail(Destinatario dest, String email) {
        if (dest == null || email == null || email.isEmpty()) {
            logger.warn("Intento de envío con destinatario o mensaje vacío");
            return false;
        }
        logger.info("Enviando email: {}", email);
        return true;
    }
}
