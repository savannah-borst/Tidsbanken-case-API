FROM jboss/keycloak:16.1.1

ENV KEYCLOAK_USER ${KEYCLOAK_USER}
ENV KEYCLOAK_PASSWORD ${KEYCLOAK_PASSWORD}
ENV PROXY_ADDRESS_FORWARDING true
ENV PORT 8080

COPY docker-entrypoint.sh /opt/jboss/tools

ENTRYPOINT [ "/opt/jboss/tools/docker-entrypoint.sh" ]

CMD ["-b", "0.0.0.0"]
