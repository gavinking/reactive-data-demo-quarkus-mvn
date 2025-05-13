package org.example;

import jakarta.enterprise.context.RequestScoped;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.mutiny.delegation.MutinyStatelessSessionDelegator;

@RequestScoped
public class RequestScopedStatelessSession extends MutinyStatelessSessionDelegator {

    private Mutiny.StatelessSession session;

    public void setSession(Mutiny.StatelessSession session) {
        System.out.println(session == null ? "Releasing session" : "New session");
        this.session = session;
    }

    @Override
    public Mutiny.StatelessSession delegate() {
        if (session == null) {
            throw new IllegalStateException("no session");
        }
        return session;
    }
}

