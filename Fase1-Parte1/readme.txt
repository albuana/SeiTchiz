----------jar---------

Os ficheiros .jar estão na pasta libs tanto do cliente como do servidor.

-----------------------------------------------------------------------------------------------------

Como executar o projeto com os ficheiros de permissões?

----------Server---------

Abrir um terminal na pasta server, escolher uma das opções e correr o comando.


Sem debug

java -cp bin -Djava.security.manager -Djava.security.policy=server.policy server.SeiTchizServer 45678

Com debug

java -cp bin -Djava.security.manager -Djava.security.policy=server.policy -Djava.security.debug=access server.SeiTchizServer 45678

----------Client---------

Abrir um terminal na pasta client, escolher uma das opções e correr o comando.
(O nome de utilizador e password escritos nos comandos são apenas exemplos)


Sem debug

java -cp bin -Djava.security.manager -Djava.security.policy=client.policy client.SeiTchiz 127.0.0.1:45678 ana passana

Com debug

java -cp bin -Djava.security.manager -Djava.security.policy=client.policy -Djava.security.debug=acces client.SeiTchiz 127.0.0.1:45678 ana passana


----------------------

Limitações:

Para fazer post de uma foto tem de a colocar primeiro na pasta UserFiles que se encontra na pasta client.

