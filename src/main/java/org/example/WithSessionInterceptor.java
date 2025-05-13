package org.example;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.hibernate.reactive.mutiny.Mutiny;

@WithSession @Interceptor
public class WithSessionInterceptor {
    @Inject RequestScopedStatelessSession requestScopedStatelessSession;
    @Inject Mutiny.SessionFactory factory;
    @AroundInvoke
    public Object withSession(InvocationContext invocationContext) throws Exception {
        if ( factory.getCurrentStatelessSession() == null
                && invocationContext.getMethod().getReturnType().equals(Uni.class) ) {
            return factory.withStatelessTransaction(session -> {
                requestScopedStatelessSession.setSession(session);
                try {
                    Uni<?> result = (Uni<?>) invocationContext.proceed();
                    return result.eventually(() -> requestScopedStatelessSession.setSession(null));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            return invocationContext.proceed();
        }
    }
    @PostConstruct
    public void postConstruct(InvocationContext invocationContext)
            throws Exception {
        invocationContext.proceed();
    }
}
