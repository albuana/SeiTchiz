Fizemos uma gravação de como correr o nosso projeto no eclipse. 
Pode vê-la aqui: https://drive.google.com/file/d/1ZcOiEUUktDzM0I-suIoCDdwJOV0txCwn/view?usp=sharing

----------jar---------

Os ficheiros .jar estão na pasta libs tanto do cliente como do servidor.

Para os correr no terminal tem de estar dentro da pasta libs tanto do client como do server.

Comandos:

java -jar Client.jar 127.0.0.1:45678 ana passana

java -jar Server.jar 45678

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

