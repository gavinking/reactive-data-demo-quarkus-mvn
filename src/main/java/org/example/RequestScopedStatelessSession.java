package org.example;

import jakarta.enterprise.context.RequestScoped;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.mutiny.delegation.MutinyStatelessSessionDelegator;

/**
 * Allows a reactive {@linkplain Mutiny.StatelessSession stateless session}
 * to be associated with the current reactive request scope. This object is
 * a wrapper for the current stateless session.
 */
@RequestScoped
public class RequestScopedStatelessSession extends MutinyStatelessSessionDelegator {

    private Mutiny.StatelessSession session;

    /**
     * Associate the given session with the request scope,
     * or disassociate it if the given session is null.
     */
    public void setSession(Mutiny.StatelessSession session) {
        if (this.session != null && session != null && this.session != session) {
            throw new IllegalStateException("Session already set");
        }
        this.session = session;
    }

    /**
     * Delegate operations of {@link Mutiny.StatelessSession}
     * to the session we've been given.
     */
    @Override
    public Mutiny.StatelessSession delegate() {
        if (session == null) {
            throw new IllegalStateException("no session");
        }
        return session;
    }
}

